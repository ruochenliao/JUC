package com.example.demo;

import lombok.Data;

/**
 * test volatile
 */
@Data
class ThreadDemo implements Runnable{
    private Boolean flag = false;

    private volatile Boolean flagWithVolatile = false;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //写数据
        flag = true;
        flagWithVolatile = true;
        System.out.println("flag is " + flag);
    }
}
