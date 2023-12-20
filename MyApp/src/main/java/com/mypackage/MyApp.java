package com.mypackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyApp {

    private static final Logger logger = LogManager.getLogger(MyApp.class);

    public static void main(String[] args) {
        logger.trace("This is a TRACE message.");
        logger.debug("This is a DEBUG message.");
        logger.info("This is an INFO message.");
        logger.warn("This is a WARN message.");
        logger.error("This is an ERROR message.");
        logger.fatal("This is a FATAL message.");
    }
}
