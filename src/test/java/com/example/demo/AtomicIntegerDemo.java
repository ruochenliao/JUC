package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo implements Runnable{

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void run() {
        atomicInteger.getAndIncrement();
        System.out.println(Thread.currentThread().getName() + " atomicInteger :" + atomicInteger);
    }
}
