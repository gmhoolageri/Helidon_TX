package com.servicemanager.tasks;

import com.servicemanager.valueobjects.ResponsePayloadVO;

public class TaskGetHostNameOfTheVM extends Task {

    @Override
    protected boolean canThisTaskBeSkipped() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected ResponsePayloadVO callTask() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void rollback() {
        // TODO Auto-generated method stub

    }

}
