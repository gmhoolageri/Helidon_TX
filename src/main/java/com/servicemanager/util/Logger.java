package com.servicemanager.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;

public final class Logger {

    private final static Map<String, LoggerImpl> allLoggers = new ConcurrentHashMap<>();

    private final static ThreadLocal<String> requestContext = new ThreadLocal<String>() {
    };

    public static String getRequestContext() {
        if (requestContext.get() == null) {
            return "default";
        }
        return requestContext.get();
    }

    public static void setRequestContext(String rcontext) {
        requestContext.set(rcontext);
    }

    private static String getCallerClassName() {
        String classnameFQN = Thread.currentThread().getStackTrace()[3].getClassName();
        return classnameFQN.substring(classnameFQN.lastIndexOf(".") + 1);
    }

    public static LoggerImpl getLogger(String name) {
        LoggerImpl loggerImpl = null;
        if (allLoggers.containsKey(name)) {
            loggerImpl = allLoggers.get(name);
        } else {
            loggerImpl = new LoggerImpl(name);
            allLoggers.put(name, loggerImpl);
        }

        return loggerImpl;
    }

    private final static class LoggerImpl {
        private final org.apache.logging.log4j.Logger log4jLogger;

        LoggerImpl(String name) {
            log4jLogger = LogManager.getLogger(name);
        }

        public void debug(Object message) {
            updateThreadContext();
            log4jLogger.debug(message);
        }

        private void updateThreadContext() {
            if (getRequestContext() != null) {
                ThreadContext.put("serviceInstanceId", getRequestContext());
            } else {
                ThreadContext.put("serviceInstanceId", "default");
            }
        }

        public void debug(Object message, Throwable e) {
            updateThreadContext();
            log4jLogger.debug(message, e);
        }

        public void error(Object message, Throwable e) {
            updateThreadContext();
            log4jLogger.error(message, e);
        }

        public void info(Object message, Throwable e) {
            updateThreadContext();
            log4jLogger.info(message, e);
        }

        public void error(Object message) {
            updateThreadContext();
            log4jLogger.error(message);
        }

        public void warn(Object message) {
            updateThreadContext();
            log4jLogger.warn(message);
        }

        public void info(Object message) {
            updateThreadContext();
            log4jLogger.info(message);
        }

        public void trace(Object message) {
            updateThreadContext();
            log4jLogger.trace(message);
        }

        public void warn(Object obj, Exception e) {
            updateThreadContext();
            log4jLogger.warn(obj, e);
        }

    }

    public static void debug(Object message) {
        getLogger(getCallerClassName()).debug(message);
    }

    public static void debug(Object message, Throwable e) {
        getLogger(getCallerClassName()).debug(message, e);

    }

    public static void error(Object message, Throwable e) {
        getLogger(getCallerClassName()).error(message, e);
    }

    public static void info(Object message, Throwable e) {
        getLogger(getCallerClassName()).info(message, e);
    }

    public static void error(Object message) {
        getLogger(getCallerClassName()).error(message);
    }

    public static void warn(Object message) {
        getLogger(getCallerClassName()).warn(message);
    }

    public static void info(Object message) {
        getLogger(getCallerClassName()).info(message);

    }

    public static void trace(Object message) {
        getLogger(getCallerClassName()).trace(message);
    }

    public static void warn(Object obj, Exception e) {
        getLogger(getCallerClassName()).warn(obj, e);
    }
}
