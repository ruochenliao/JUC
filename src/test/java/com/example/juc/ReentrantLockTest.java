package com.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、ReentrantLock 是可重入锁
 *  使用 AQS 中的 state 来计数
 *
 * 2、ReentrantLock 核心是 AQS
 *  AQS 核心数据结构：双向链表 + state(锁状态)
 *  state 的修改是用的 CAS 乐观锁的方法来修改的
 *
 * 3、ReentrantLock 分配公平锁和非公平锁
 *  非公平锁（默认）：非公平锁加锁时不考虑排队等待问题，直接尝试获取锁，所以存在后申请却先获得锁的情况
 *  公平锁：公平锁就是通过同步队列来实现多个线程按照申请锁的顺序来获取锁
 *
 */
public class ReentrantLockTest {
    @Test
    public void testReentrantLock(){
        Lock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }
}
