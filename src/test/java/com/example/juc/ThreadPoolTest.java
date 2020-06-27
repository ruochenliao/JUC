package com.example.juc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池，提供了一个线程队列，队列保持了等待状态的线程, 减少了线程关闭打开的开销
 *
 * 线程池工具类
 * Executors
 *  ExcutorService newFixedThreadPool();    创建固定大小的线程池
 *  ExcutorService newCachedThreadPool();   创建大小不固定的线程池
 *  ExcutorService newSingleThreadPool();   创建单个线程的线程池
 *  ExcutorService newScheduledThreadPool();创建可以定时调度的线程池
 */
public class ThreadPoolTest {
    /**
     * 使用线程池
     */
    @Test
    public void testThreadPool(){
        //1、创建线程池
        ExecutorService executorService  = Executors.newFixedThreadPool(5);

        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();

        //2、为线程池分配任务
        executorService.submit(threadPoolDemo);

        //3、关闭线程池
        executorService.shutdown();
    }

    /**
     * 线程池和 callable 一起使用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testThreadPoolAndCallable() throws ExecutionException, InterruptedException {
        //1、创建线程池
        ExecutorService executorService  = Executors.newFixedThreadPool(5);

        Future<Integer> result = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for(int i = 0; i < 100; i++){
                    sum += i;
                }
                return sum;
            }
        });
        System.out.println("result: "+ result.get());
        executorService.shutdown();
    }

    @Test
    public void testThreadPoolAndBatchCallable() throws ExecutionException, InterruptedException {
        //1、创建线程池
        ExecutorService executorService  = Executors.newFixedThreadPool(5);

        List<Future<Integer>> resultList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Future<Integer> result = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int sum = 0;
                    for(int i = 0; i < 100; i++){
                        sum += i;
                    }
                    return sum;
                }
            });
            resultList.add(result);
        }

        for(Future<Integer> result :resultList){
            System.out.println(result.get());
        }
        executorService.shutdown();
    }
}

class ThreadPoolDemo implements Runnable{

    @Override
    public void run() {
        for(int i = 0; i < 100; i++){
            System.out.println(Thread.currentThread().getName() + ":" +i);
        }
    }
}
