package com.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join 框架
 * 在必要情况下，将一个大任务拆解成若干个小任务，再将小任务进行 join 汇总
 *
 * 任务窃取：从其他线程的末尾去偷线程，因此更能利用 CPU 资源
 */
public class ForkJoinTest {
    @Test
    public void testForkJoin(){
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new Calculator(0, 1000000000L);
        long sum = pool.invoke(task);
        System.out.println(sum);
    }
}

class Calculator extends RecursiveTask<Long>{

    private long start;
    private long end;

    //临界值
    private static final long THREAD_HOLD = 1000L;

    public Calculator(long start, long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        long length = end - start;
        if(length <= THREAD_HOLD){
            long sum = 0L;
            for(long  i = start; i <= end; i++){
                sum += i;
            }
            return sum;
        }else{
            long middle = (start + end)/2;
            Calculator left = new Calculator(length, middle);
            left.fork();
            Calculator right = new Calculator(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}
