package com.lambdaschool.school.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

/**
 * Created by satyendra on 12/7/16.
 */
public class LogWriter {

    private static final Logger logger = LoggerFactory.getLogger(LogWriter.class);

    public static void write(Class clazz, LogLevel logLevel, String message) {
        switch (logLevel) {
            case TRACE:
                logger.trace(message);
                break;
            case DEBUG:
                logger.debug(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            default:
                logger.warn("No suitable log level found");
                break;
        }
    }

}