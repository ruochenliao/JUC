package com.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的四种方式
 * 1、Runnable 接口
 * 2、new Thread()
 * 3、Callable
 * 4、线程池
 *
 * Callable
 * 相对于 Ruunable 接口的方式，方法可以有返回值，并且可以抛出异常
 *
 * 执行 Callable 方式, 需要 FutureTask 实现支持，用于接收运算结果。FutureTask 是 Future 接口的实现类
 *
 */
public class CallableTest {

    @Test
    public void testCallable(){
        CallableDemo callableDemo = new CallableDemo();
        //1、执行 callable 方式，需要 FutureTask 实现类的支持，用于接收计算结果
        FutureTask<Integer> futureTask = new FutureTask<>(callableDemo);
        new Thread(futureTask).start();
        //2、接收线程运算后的接口
        try {
            Integer result = futureTask.get();
            System.out.println("end " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class CallableDemo implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for(int i = 0; i < 100; i++){
            System.out.println(i);
            sum += i;
        }
        return sum;
    }
}
