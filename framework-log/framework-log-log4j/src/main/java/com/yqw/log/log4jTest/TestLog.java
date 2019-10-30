package com.yqw.log.log4jTest;

import org.apache.log4j.Logger;

public class TestLog {

    private static final Logger logger = Logger.getLogger(TestLog.class);

    public static void main(String[] args) {
        //  BasicConfigurator.configure ()： 自动快速地使用缺省Log4j环境
        //  PropertyConfigurator.configure (String configFilename) ：读取使用Java的特性文件编写的配置文件
        //  DOMConfigurator.configure ( String filename ) ：读取XML形式的配置文件
        logger.info("Hello,log4j");
    }
}
