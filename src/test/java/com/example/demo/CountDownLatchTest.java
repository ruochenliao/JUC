package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 一个同步辅助类，在完成一组操作，等待其他线程，确保所有活动执行完了再执行下一个动作
 * 闭锁
 */
public class CountDownLatchTest {
    @Test
    public void testCountDownLatch(){
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        LatchDemo latchDemo = new LatchDemo(countDownLatch);
        long start = System.currentTimeMillis();
        for(int i = 0; i < 10; i++){
            new Thread(latchDemo).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("process time is " + (end - start) + "ms");
    }

    public class LatchDemo implements Runnable{
        private CountDownLatch countDownLatch;

        LatchDemo(CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            }finally {
                countDownLatch.countDown();
            }
        }
    }
}
