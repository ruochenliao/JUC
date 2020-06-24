package com.example.demo;

import lombok.Data;

@Data
public class AtomicDemo implements Runnable{

    private volatile static Integer counter = 0;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter++;
        System.out.println(Thread.currentThread().getName() + ":" + counter);
    }
}
