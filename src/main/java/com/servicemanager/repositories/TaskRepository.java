package com.servicemanager.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.servicemanager.constants.TaskState;
import com.servicemanager.entities.TaskDO;

public class TaskRepository extends JpaRepository<TaskDO, BigDecimal> {

    public TaskRepository(EntityManager em) {
        super(TaskDO.class, em);
    }

    public static final String findTasksByState_Query = "SELECT r FROM com.servicemanager.entities.TaskDO r WHERE r.state = ?1";
    public static final String findTasksByState_Name = "findTasksByState";

    public Optional<List<TaskDO>> findTasksByState(TaskState state) {
        List<TaskDO> taskDOList = executeNamedQuery(findTasksByState_Name, state);
        return getListFromList(taskDOList, state);
    }

    public static final String findTasksByStateAndServiceRequestId_Query = "SELECT r FROM com.servicemanager.entities.TaskDO r WHERE r.state = ?1 and r.serviceRequest.id = ?2";
    public static final String findTasksByStateAndServiceRequestId_Name = "findTasksByStateAndServiceRequestID";

    public Optional<List<TaskDO>> findByTaskStateAndServiceRequestId(TaskState state, BigDecimal serviceRequestId) {
        List<TaskDO> taskDOList = executeNamedQuery(findTasksByStateAndServiceRequestId_Name, state, serviceRequestId);
        return getListFromList(taskDOList, state, serviceRequestId);
    }

    public TaskDO saveTask(TaskDO taskDO) {
        return persistEntity(taskDO, taskDO.getId());
    }
}
