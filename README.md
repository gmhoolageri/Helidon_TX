Here is the sample code - https://github.com/gmhoolageri/Helidon_TX.git

This is a cutdown version of our project.  The junit class is the one which reproduces the scenario that I explained earlier - https://github.com/gmhoolageri/Helidon_TX/blob/master/src/test/java/com/servicemanager/DirtyReadCheck.java . This was a simple and dirty junit class that I could come up with.

The junit test case basically persisting the ServiceRequestDO entity to database with APPROVED state.  The PRODUCER is a thread that will read the ServiceRequestDO in APPROVED state from DB and will add the child elements called TaskDOs to ServiceRequestDO and persist it to DB with the state changed to READY.  While saving the ServiceRequestDO to DB the state of the ServiceRequestDO gets changed to READY and then the child elements are saved to database. It is to be noted here that child elements are annotated with CascadeType.ALL . Since everything happening as part of one transaction, the data must not be available to other threads till transaction completes. But, the other thread called CONSUMER able to read the ServiceRequestDO in READY state and fails as the first TASKDO does not have the execution sequence as 1.

The easiest way to execute this test case is 
add the db details in microprofile-config.properties 
do - mvn clean package -Dtest=DirtyReadCheck

Since the Assertion is failing in threads, the error is not propagated to test method and build will still succeed  but in the STDOUT following message would be seen - READ THE SERVICE REQUEST AND FIRST TASK SEQUENCE IS NOT 1. THIS IS AN EXAMPLE OF DIRTY READ.
