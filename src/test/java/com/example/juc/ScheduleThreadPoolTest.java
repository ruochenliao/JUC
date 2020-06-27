package com.example.juc;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class ScheduleThreadPoolTest {
    @Test
    public void testSchedulePool() throws ExecutionException, InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        //每隔 2 s 打印一个随机数
        for(int i = 0; i < 5; i++){
            Future<Integer> result = executorService.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int num = new Random().nextInt(10);
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    return num;
                }
            }, 2, TimeUnit.SECONDS);
            System.out.println(result.get());
        }
        executorService.shutdown();


    }
}
