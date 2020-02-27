package com.servicemanager.config;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;

import com.servicemanager.util.Logger;

public class CustomJPALogger extends AbstractSessionLog implements SessionLog {
    @Override
    public void log(SessionLogEntry entry) {
        int level = entry.getLevel();
        String message = entry.getMessage();
        if (entry.getParameters() != null) {
            message += " [";
            int index = 0;
            for (Object object : entry.getParameters()) {
                message += (index++ > 0 ? "," : "") + object;
            }
            message += "]";
        }
        switch (level) {
        case SessionLog.SEVERE:
            Logger.error(message);
            break;
        case SessionLog.WARNING:
            Logger.warn(message);
            break;
        case SessionLog.INFO:
            Logger.info(message);
            break;
        case SessionLog.CONFIG:
            Logger.trace(message);
            break;
        default:
            Logger.debug(message);
            break;
        }
    }
}
