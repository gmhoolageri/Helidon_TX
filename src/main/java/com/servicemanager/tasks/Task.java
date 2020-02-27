package com.servicemanager.tasks;

import java.util.concurrent.Callable;

import com.servicemanager.valueobjects.ResponsePayloadVO;

public abstract class Task implements Callable<Object>, Runnable {

    @Override
    public Object call() throws Exception {
        return callTask();
    }

    protected abstract boolean canThisTaskBeSkipped();

    protected abstract ResponsePayloadVO callTask() throws Exception;

    @Override
    public void run() {
        rollback();
    }

    protected abstract void rollback();

}
