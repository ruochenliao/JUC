package com.example.demo;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VolatileTest {

    /**
     * 线程与线程之间内存不可见
     */
    @Test
    public void testWithoutVolatile(){
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();
        while(true){
            if(threadDemo.getFlag()){
                //将永远不会执行下面这一步，因为主线程感知不到子线程对内存的操作
                System.out.println("主线程读取到的flag =" + threadDemo.getFlag());
                break;
            }
        }
    }
    /**
     * 使用 synchronized 悲观锁
     */
    @Test
    public void testWithSynchronized(){
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();
        while(true){
            synchronized (threadDemo) {
                if (threadDemo.getFlag()) {
                    //使用 synchronized 使得可以打印出这个数据 主线程读取到的flag =true
                    //但是 synchronized 悲观锁的性能很低
                    System.out.println("主线程读取到的flag =" + threadDemo.getFlag());
                    break;
                }
            }
        }
    }

    /**
     * volatile 保证多线程对修饰的主存变量的可见性
     * 相对于 synchronized 是一种轻量级的同步策略
     *
     * 注意
     * 1、volatile 不具有互斥性
     * 2、volatile 不能保证变量的原子性
     */
    @Test
    public void testWithVolatile(){
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();
        while(true){
            if(threadDemo.getFlagWithVolatile()){
                //主线程读取到的 flagWithVolatile =true
                System.out.println("主线程读取到的 flagWithVolatile =" + threadDemo.getFlagWithVolatile());
                break;
            }
        }
    }

    /**
     * volatile 不能保证变量的原子性
     *
     * 解决方式：原子变量 java.util.concurrent.atomic 下所有的变量都具有原子特性
     * 比如 java.util.concurrent.atomic.AtomicInteger
     * 1、它们都使用 volatile 保证内存可见性
     * 2、他们都用了 CAS 算法保证数据原子性
     *    CAS 是硬件对于并发操作共享数据的支持
     *    CAS 包含三个操作值
     *      内存值（第二次读取的内存值） V
     *      预估值（第一次读取的内存值） A
     *      更新值 B
     *    当且仅当 V == A 时，才赋值 V = B， 否则再次尝试，它不阻塞
     */
    @Test
    public void testVolatileNoAtomic(){
        AtomicDemo atomicDemo = new AtomicDemo();
        for(int i = 0; i < 10; i++){
            //循环起 10 个线程，每个线程都对 volatile 值进行 + 1 操作
            Thread thread = new Thread(atomicDemo);
            thread.setName(String.valueOf(i));
            thread.start();
        }
    }

    /**
     * 使用 atomicInteger，它包含了 volatile 和 atomic 功能
     *
     */
    @Test
    public void testVolatileWithAtomic(){
        AtomicIntegerDemo atomicDemo = new AtomicIntegerDemo();
        for(int i = 0; i < 10; i++){
            //循环起 10 个线程，每个线程都对 atomicInteger 值进行 + 1 操作
            Thread thread = new Thread(atomicDemo);
            thread.setName(String.valueOf(i));
            thread.start();
        }
    }

}
