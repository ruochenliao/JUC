package com.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于解决多线程安全的方式
 * synchronized: 隐视锁
 * 1、同步代码块
 * 2、同步方法
 *
 * DK 1.5 以后使用显示锁
 * 3、同步锁 LockTest
 */
public class LockTest {

    @Test
    public void testThreadSecurity(){
        System.out.println("hello world");
        Ticket ticket = new Ticket();
        new Thread(ticket, "窗口1").start();
        new Thread(ticket, "窗口2").start();
        new Thread(ticket, "窗口3").start();
    }
}

class Ticket implements Runnable{

    private Integer tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while(tick > 0){
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "余票数是:" + tick--);
            }finally {
                lock.unlock();
            }
        }
    }
}
