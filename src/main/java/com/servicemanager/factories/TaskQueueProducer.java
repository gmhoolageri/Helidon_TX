package com.servicemanager.factories;

import java.util.ArrayList;
import java.util.List;

import com.servicemanager.entities.ServiceRequestDO;

public interface TaskQueueProducer<T> {

    public List<T> produceTasks(ServiceRequestDO request);

    public default List<T> getCommonTasks(ServiceRequestDO request) {
        List<T> listOfCommonTasks = new ArrayList<T>();
        listOfCommonTasks.add(null);
        return null;
    }
}
