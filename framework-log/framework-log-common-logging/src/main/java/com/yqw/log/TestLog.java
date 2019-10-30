package com.yqw.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestLog {

    private  static  final Log log = LogFactory.getLog(TestLog.class);

    public static void main(String[] args) {
        log.info("Hello ,common-logging");
    }
}
