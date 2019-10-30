package com.yqw.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Log4j2在初始化时会自动查找配置，然后配置自己。支持各种方式、各种类型的配置，检查过程如下：
 *
 * 系统属性log4j.configurationFile指定的文件
 * classpath下log4j2-test.properties
 * classpath下log4j2-test.yaml或log4j2-test.json或log4j2-test.xml
 * classpath下log4j2.properties
 * classpath下log4j2.yaml或log4j2.json或log4j2.xml
 * 默认配置被使用
 */
public class TestLog {

    private static final Logger logger = LogManager.getLogger(TestLog.class);

    public static void main(String[] args) {
        logger.error("Hello log4j2");
    }
}
