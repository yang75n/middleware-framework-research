package com.yqw.log;

import java.util.logging.Logger;

public class TestLog {
    private static final Logger logger = Logger.getLogger(TestLog.class.getName());

    public static void main(String[] args) {
        logger.severe("Hello Java logging level="+logger.getLevel());
        logger.config("Hello Java logging");
        logger.info("Hello Java logging");

    }
}
