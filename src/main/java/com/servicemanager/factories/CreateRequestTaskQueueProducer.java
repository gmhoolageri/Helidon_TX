package com.servicemanager.factories;

import java.util.ArrayList;
import java.util.List;

import com.servicemanager.entities.ServiceRequestDO;
import com.servicemanager.tasks.TaskActivateAppGatewayInIdcs;
import com.servicemanager.tasks.TaskAllocateDatabase;
import com.servicemanager.tasks.TaskAttachBlockVolumesToVM;
import com.servicemanager.tasks.TaskCreateAppGatewayInIdcs;
import com.servicemanager.tasks.TaskCreateBackendSet;
import com.servicemanager.tasks.TaskCreateBlockVolumes;
import com.servicemanager.tasks.TaskCreateCloudGateMappingsInApp;
import com.servicemanager.tasks.TaskCreateEnterpriseAppInIdcs;
import com.servicemanager.tasks.TaskCreateHostInAppGateway;
import com.servicemanager.tasks.TaskCreateListenerForLoadBalancer;
import com.servicemanager.tasks.TaskCreateListenerHostname;
import com.servicemanager.tasks.TaskCreateRuleSet;
import com.servicemanager.tasks.TaskCreateVM;
import com.servicemanager.tasks.TaskCreateVMInitializationAnsiblePlaybookNew;
import com.servicemanager.tasks.TaskGenerateDNSNameForLoadBalancerIfNotPresent;
import com.servicemanager.tasks.TaskGenerateNecessaryFoldersForProvisioning;
import com.servicemanager.tasks.TaskGenerateRehydrationAnsiblePlaybook;
import com.servicemanager.tasks.TaskGenerateRehydrationMonitoringAnsiblePlaybook;
import com.servicemanager.tasks.TaskGenerateRehydrationWrapperScript;
import com.servicemanager.tasks.TaskGenerateServiceJsonAndWallet;
import com.servicemanager.tasks.TaskGetEnterpriseAppDetails;
import com.servicemanager.tasks.TaskGetHostNameOfTheVM;
import com.servicemanager.tasks.TaskGetICAPResource;
import com.servicemanager.tasks.TaskGetOrCreateLoadBalancer;
import com.servicemanager.tasks.TaskGetSMTPResource;
import com.servicemanager.tasks.TaskGetServiceInstanceGUID;
import com.servicemanager.tasks.TaskInitializeVM;
import com.servicemanager.tasks.TaskInvokeRehydrationAnsible;
import com.servicemanager.tasks.TaskPersistCustomerInDBForCIM;
import com.servicemanager.tasks.TaskPersistIdentityDomainInDB;
import com.servicemanager.tasks.TaskPersistServiceAdminInDBForCIM;
import com.servicemanager.tasks.TaskPersistServiceInDBForCIM;
import com.servicemanager.tasks.TaskRegisterWithFleetManager;
import com.servicemanager.tasks.TaskUpdateAnsibleHostsFile;
import com.servicemanager.tasks.TaskUpdateEnvironmentInDB;
import com.servicemanager.tasks.TaskUpdateLoadBalancerSSLCertificate;
import com.servicemanager.tasks.TaskUpdateServiceInDB;
import com.servicemanager.tasks.TaskValidateLoadBalancerDNSNameAndPublishARecord;
import com.servicemanager.tasks.TaskValidateServiceDNSNameAndPublishCNAME;
import com.servicemanager.tasks.TaskWaitForRehydrationCompletion;
import com.servicemanager.util.Logger;

public class CreateRequestTaskQueueProducer implements TaskQueueProducer<String> {

    @Override
    public List<String> produceTasks(ServiceRequestDO request) {
        Logger.debug("Going to return tasks for OCI");
        List<String> listOfTasks = new ArrayList<String>();

        listOfTasks.add(TaskPersistIdentityDomainInDB.class.getName());

        listOfTasks.add(TaskPersistCustomerInDBForCIM.class.getName());
        // listOfTasks.add(TaskPersistCustomerInDB.class.getName());

        listOfTasks.add(TaskPersistServiceAdminInDBForCIM.class.getName());
//        // listOfTasks.add(TaskPersistServiceAdminInDB.class.getName());
//
        listOfTasks.add(TaskPersistServiceInDBForCIM.class.getName());
//        // listOfTasks.add(TaskPersistServiceInDB.class.getName());

        listOfTasks.add(TaskCreateVM.class.getName());

        listOfTasks.add(TaskGenerateNecessaryFoldersForProvisioning.class.getName());

        listOfTasks.add(TaskGetICAPResource.class.getName());

        listOfTasks.add(TaskGetSMTPResource.class.getName());

        listOfTasks.add(TaskAllocateDatabase.class.getName());

        listOfTasks.add(TaskCreateBlockVolumes.class.getName());

        listOfTasks.add(TaskAttachBlockVolumesToVM.class.getName());

        listOfTasks.add(TaskUpdateAnsibleHostsFile.class.getName());

        listOfTasks.add(TaskCreateVMInitializationAnsiblePlaybookNew.class.getName());

        listOfTasks.add(TaskInitializeVM.class.getName());

        listOfTasks.add(TaskGetHostNameOfTheVM.class.getName());

        //@formatter:off

        // BEGIN:IDCS related tasks
        listOfTasks.add(TaskCreateEnterpriseAppInIdcs.class.getName());
        listOfTasks.add(TaskGetServiceInstanceGUID.class.getName());
        listOfTasks.add(TaskGetEnterpriseAppDetails.class.getName());
        listOfTasks.add(TaskCreateAppGatewayInIdcs.class.getName());
        listOfTasks.add(TaskActivateAppGatewayInIdcs.class.getName());
        listOfTasks.add(TaskCreateHostInAppGateway.class.getName());
        listOfTasks.add(TaskCreateCloudGateMappingsInApp.class.getName());        
        // END:IDCS related tasks
        
        // @formatter:on

        listOfTasks.add(TaskGenerateServiceJsonAndWallet.class.getName());
        listOfTasks.add(TaskGenerateRehydrationWrapperScript.class.getName());
        listOfTasks.add(TaskGenerateRehydrationAnsiblePlaybook.class.getName());
        listOfTasks.add(TaskInvokeRehydrationAnsible.class.getName());

        listOfTasks.add(TaskGenerateRehydrationMonitoringAnsiblePlaybook.class.getName());
        // listOfTasks.add(TaskInvokeRehydrationScript.class.getName());

        listOfTasks.add(TaskWaitForRehydrationCompletion.class.getName());

        listOfTasks.add(TaskGetOrCreateLoadBalancer.class.getName());

        listOfTasks.add(TaskUpdateLoadBalancerSSLCertificate.class.getName());

        listOfTasks.add(TaskCreateListenerHostname.class.getName());

        listOfTasks.add(TaskCreateBackendSet.class.getName());

        listOfTasks.add(TaskCreateRuleSet.class.getName());

        listOfTasks.add(TaskCreateListenerForLoadBalancer.class.getName());

        listOfTasks.add(TaskGenerateDNSNameForLoadBalancerIfNotPresent.class.getName());

        listOfTasks.add(TaskValidateLoadBalancerDNSNameAndPublishARecord.class.getName());

        // Publishing DNS record for a URL should be done after protecting the app
        listOfTasks.add(TaskValidateServiceDNSNameAndPublishCNAME.class.getName());

        listOfTasks.add(TaskUpdateEnvironmentInDB.class.getName());

        listOfTasks.add(TaskRegisterWithFleetManager.class.getName());

        listOfTasks.add(TaskUpdateServiceInDB.class.getName());

        listOfTasks.forEach(task -> Logger.debug("Task:" + task));

        return listOfTasks;
    }
}
