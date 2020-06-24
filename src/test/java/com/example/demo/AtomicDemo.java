package com.example.demo;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class AtomicDemo implements Runnable{

    private volatile static Integer counter = 0;

    @Override
    public void run() {
        counter++;
        System.out.println(Thread.currentThread().getName() + " counter :" + counter);
    }
}
