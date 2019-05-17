package com.yqw.test.activemq;

/**
 * 生产者开始生产消息
 */
public class TestProducer {
    public static void main(String[] args) {
        Producer producter = new Producer();
        producter.init();

        TestProducer testMq = new TestProducer();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Thread 1
        new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 2
       // new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 3
       // new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 4
        //new Thread(testMq.new ProductorMq(producter)).start();
        //Thread 5
       // new Thread(testMq.new ProductorMq(producter)).start();
    }

    private class ProductorMq implements Runnable {
        Producer producter;

        public ProductorMq(Producer producter) {
            this.producter = producter;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("sendMessage...");
                    producter.sendMessage("yqw-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}