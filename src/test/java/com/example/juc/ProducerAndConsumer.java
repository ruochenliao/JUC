package com.example.juc;

import org.junit.jupiter.api.Test;

/**
 * 生产者消费者
 *
 * 可能产生的问题？
 * 虽然明知道货物已经满，但是生产者还在一直生产
 * 虽然明知道没有货物了，但是消费者还在不停地消费
 *
 * 需要等待唤醒机制, 当缺货的时候 wait, 当有货的时候通知; 当满了的时候 wait, 当没满的时候通知
 * wait 和 notifyAll
 * 但是这种方法容易出现 Object wait 无限等待
 *
 *
 */
public class ProducerAndConsumer {
    @Test
    public void testProducerAndConsumer(){
        Clerk clerk = new Clerk();
        producer producer = new producer(clerk);
        Consumer consumer = new Consumer(clerk);
        new Thread(producer, "生产者 A").start();
        new Thread(consumer, "消费者 B").start();
    }
}

class Clerk{

    private int product = 0;

    public synchronized void produce(){
        //买货物
        while(product >= 10){   //为了避免虚假唤醒问题，wait 应该总是使用在循环中
            System.out.println("商品已满");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product++;
        System.out.println(Thread.currentThread().getName() + ":" + product);
        this.notifyAll();
    }
    //卖货
    public synchronized void sell() {
        while(product <= 0){    //为了避免虚假唤醒问题，wait 应该总是使用在循环中
            System.out.println("缺货!");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        product--;
        System.out.println(Thread.currentThread().getName() + ":" + product);
        this.notifyAll();
    }
}

//生产者
class producer implements Runnable{

    private Clerk clerk;

    public producer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0; i < 20; i++){
            clerk.produce();
        }
    }
}

//消费者
class Consumer implements Runnable{

    private Clerk clerk;

    public Consumer(Clerk clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for(int i = 0 ; i < 20; i++){
            clerk.sell();
        }
    }
}
