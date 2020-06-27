package com.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 Reentrantlock 来进行同步
 * 然后用 Condition 来控制线程通信
 * Condition 中有 wait, notify, notifyAll
 */
public class LockConditionTest {

    @Test
    public void testLockCondition(){
        Manager manager = new Manager();
        producer producer = new producer(manager);
        Consumer consumer = new Consumer(manager);
        new Thread(producer, "生产者 A").start();
        new Thread(consumer, "消费者 B").start();
    }
}

class Manager extends Clerk{
    private Integer product = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void produce(){

        //买货物
        lock.lock();
        try {
            while (product >= 10) {   //为了避免虚假唤醒问题，wait 应该总是使用在循环中
                System.out.println("商品已满");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            product++;
            System.out.println(Thread.currentThread().getName() + ":" + product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    //卖货
    public void sell() {
        lock.lock();
        try {
            while (product <= 0) {    //为了避免虚假唤醒问题，wait 应该总是使用在循环中
                System.out.println("缺货!");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            product--;
            System.out.println(Thread.currentThread().getName() + ":" + product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}