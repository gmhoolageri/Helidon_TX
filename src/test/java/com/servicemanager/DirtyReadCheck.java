package com.servicemanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.inject.spi.CDI;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.servicemanager.config.Config;
import com.servicemanager.constants.OperationType;
import com.servicemanager.constants.ServiceRequestState;
import com.servicemanager.constants.ServiceType;
import com.servicemanager.constants.TaskState;
import com.servicemanager.database.DatabaseFacade;
import com.servicemanager.database.DatabaseFacadeImpl;
import com.servicemanager.entities.ServiceRequestDO;
import com.servicemanager.entities.TaskDO;
import com.servicemanager.factories.CreateRequestTaskQueueProducer;
import com.servicemanager.util.Logger;
import com.servicemanager.util.Util;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;

public class DirtyReadCheck {
    private static final int LOOPS = 1;

    private static final String CHANGE_LOG_FILE = "src/main/resources/data/R19/R19.master.xml";

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        databaseFacade = CDI.current().select(DatabaseFacadeImpl.class).get();
        config = CDI.current().select(Config.class).get();

        final ResourceAccessor resourceAccessor = new FileSystemResourceAccessor();
        try {
            DatabaseFactory dbFactory = DatabaseFactory.getInstance();
            Database database = dbFactory.openDatabase(config.getUrl(), config.getUser(), config.getPassword(), null,
                    resourceAccessor);

            Liquibase liquibase;

            liquibase = new Liquibase(CHANGE_LOG_FILE, resourceAccessor, database);

            Contexts contexts = null;

            liquibase.update(contexts);

            System.out.println("Executed liquibase changes");
        } catch (Exception e) {
            System.out.println("Exception in setting up liquibase");
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void startServer() {
        System.setProperty("log-path", "./applogs");
        Main.startServer("src/main/resources/META-INF");
    }

    @Spy
    private DatabaseFacade databaseFacade;

    private Config config;

    @Test
    public void testCreateAndFetch() {

        // PERSIST SERVICE_REQUEST IN APPROVED STATE
        for (int i = 0; i < LOOPS; i++) {
            ServiceRequestDO serviceRequestDO = new ServiceRequestDO();
            serviceRequestDO.setIdentityDomainName("test" + i);
            serviceRequestDO.setCimInstanceId("1234");
            serviceRequestDO.setCimRequestId("2345");
            serviceRequestDO.setOperationType(OperationType.CREATE);
            serviceRequestDO.setServiceInstanceId(UUID.randomUUID().toString());
            serviceRequestDO.setServiceName("OTMGTM");
            serviceRequestDO.setServiceType(ServiceType.PRODUCTION);
            serviceRequestDO.setState(ServiceRequestState.APPROVED);

            databaseFacade.saveServiceRequest(serviceRequestDO);
            System.out.println("Persisted:" + serviceRequestDO);
        }

        CommonObject c = new CommonObject();

        // START CONSUMER AHEAD OF PRODUCER, THE CONSUMER CONSUMES SERVICE REQUESTS IN
        // READY STATE. THIS IS WHERE DIRTY READ HAPPENS
        Thread consumer = new Thread(new ThreadConsumer(c));
        consumer.start();

        // CREATE 'N' PRODUCERS WHICH PROCESS THE SERVICE_REQUEST IN APPROVED STATE AND
        // PRODUCE CHILD ELEMENTS(TASKDO) AND PERSIST THE SERVICE REQUEST IN READY
        // STATE.
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < LOOPS; i++) {
            threads.add(new Thread(new ThreadProducer()));
        }
        for (int i = 0; i < LOOPS; i++) {
            threads.get(i).start();
        }
        for (int i = 0; i < LOOPS; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // WAIT FOR CONSUMER TO JOIN THE MAIN THREAD
        try {
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Thread verifier = new Thread(new ThreadVerifier(c));
//        verifier.start();
//        try {
//            consumer.join();
//            verifier.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    class CommonObject {
        transient boolean alldone = false;
    }

    class ThreadProducer implements Runnable {

        @Override
        public void run() {
            System.setProperty("log-path", "./applogs");

            Optional<List<ServiceRequestDO>> optionalListOfServiceRequestDOs = databaseFacade
                    .findServiceRequestsByServiceRequestState(ServiceRequestState.APPROVED);

            Assert.assertTrue("No data present in the db", Util.isCollectionPresent(optionalListOfServiceRequestDOs));

            List<ServiceRequestDO> listOfAcceptedRequests = optionalListOfServiceRequestDOs.get();
            ServiceRequestDO serviceRequestDO = listOfAcceptedRequests.stream().findFirst().orElse(null);

            System.out.println("Going to process " + ServiceRequestState.APPROVED + " request. ServiceRequest is: "
                    + serviceRequestDO);
            List<String> listOfTasks = new CreateRequestTaskQueueProducer().produceTasks(serviceRequestDO);

            if (!Util.isCollectionEmpty(listOfTasks)) {
                System.out.println("Going to process list of tasks produced");
                Set<TaskDO> orderedListOfTasks = processListOfTasks(listOfTasks, serviceRequestDO);

                System.out.println("Total number of tasks are:" + orderedListOfTasks.size());
                serviceRequestDO.setTask(orderedListOfTasks);
                serviceRequestDO.setState(ServiceRequestState.READY);
                databaseFacade.saveServiceRequest(serviceRequestDO);
                System.out.println("Completed processing service request in " + ServiceRequestState.APPROVED + " state "
                        + serviceRequestDO);
            }

        }

        private Set<TaskDO> processListOfTasks(List<String> listOfTasks, ServiceRequestDO request) {
            Set<TaskDO> orderedListOfTasks = new LinkedHashSet<TaskDO>();
            AtomicInteger ordinal = new AtomicInteger(1);
            listOfTasks.forEach(task ->
                {
                    TaskDO t = new TaskDO();
                    t.setInvocationClass(task);
                    t.setInvocationURI(new String(task).concat("/execute"));
                    t.setState(TaskState.READY);
                    t.setExecutionSequence(new BigDecimal(ordinal.getAndIncrement()));
                    t.setServiceRequest(request);
                    orderedListOfTasks.add(t);
                });
            return orderedListOfTasks;
        }
    }

    class ThreadConsumer implements Runnable {
        CommonObject commonObject;

        public ThreadConsumer(CommonObject c) {
            commonObject = c;
        }

        @Override
        public void run() {
            while (!commonObject.alldone) {
                System.out.println("Going to read service requests in ready state");
                Optional<List<ServiceRequestDO>> optionalListOfReadyRequests = null;
                do {
                    optionalListOfReadyRequests = databaseFacade
                            .findServiceRequestsByServiceRequestState(ServiceRequestState.READY);

                } while (!Util.isCollectionPresent(optionalListOfReadyRequests));
                System.out.println("Service requests in ready state are found");
                List<ServiceRequestDO> listOfAcceptedRequests = optionalListOfReadyRequests.get();

                for (ServiceRequestDO serviceRequestDO : listOfAcceptedRequests) {
                    getNextExecutableTask(serviceRequestDO.getTask());
                    serviceRequestDO.setState(ServiceRequestState.COMPLETED);
                    databaseFacade.saveServiceRequest(serviceRequestDO);
                }

            }
        }

        protected Optional<TaskDO> getNextExecutableTask(Set<TaskDO> tasks) {
            //@formatter:off
            Optional<TaskDO> optionalTask = tasks.stream()
            .sorted((t1, t2) -> t1.getExecutionSequence().compareTo(t2.getExecutionSequence()))
            .filter(task -> task.getState() != TaskState.COMPLETED)
            .filter(task -> task.getState() != TaskState.MANUALLY_SKIPPED)
            .filter(task -> task.getState() != TaskState.AUTO_SKIPPED)
            .findFirst();
            //@formatter:on

            if (optionalTask.isPresent()) {
                TaskDO task = optionalTask.get();
                Logger.debug("GOT TASK:" + task);
                Assert.assertTrue(
                        "READ THE SERVICE REQUEST AND FIRST TASK SEQUENCE IS NOT 1. THIS IS AN EXAMPLE OF DIRTY READ.",
                        task.getExecutionSequence().intValue() == 1);

            }
            return optionalTask;

        }

    }

    class ThreadVerifier implements Runnable {

        CommonObject commonObject;

        public ThreadVerifier(CommonObject c) {
            commonObject = c;
        }

        @Override
        public void run() {
            while (!commonObject.alldone) {
                Optional<List<ServiceRequestDO>> optionalListOfCompletedRequests = null;
                do {
                    optionalListOfCompletedRequests = databaseFacade
                            .findServiceRequestsByServiceRequestState(ServiceRequestState.COMPLETED);

                } while (!Util.isCollectionPresent(optionalListOfCompletedRequests));

                commonObject.alldone = optionalListOfCompletedRequests.get().size() == 100 ? true : false;
            }
        }

    }
}
