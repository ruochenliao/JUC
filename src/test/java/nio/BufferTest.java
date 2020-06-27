package nio;

import org.junit.jupiter.api.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 一、缓冲区的底层就是数组，用于存储不同类型的数据
 *  根据数据类型不同，提供了对应类型的缓冲区
 *      ByteBuffer
 *      CharBuffer
 *      IntBuffer ...
 * 上述缓冲区的管理方式一致，通过 allocate() 获取缓冲区
 *
 * 二、缓冲区存取数据的两个核心方法
 * put() : 存入数据到缓冲区
 * get() : 获取缓冲区中的数据
 * flip(): 读写模式切换
 *
 * 三、缓冲区的核心属性
 * capacity: 容量，表示缓冲区最大存储数据的容量，一旦申明不能改变。
 * limit: 上线，表示缓冲区中可以操作数据的大小。limit 后面的数据是不能进行读写的
 * position: 位置，表示缓冲区中正在操作的数据的位置
 *
 * mark: 标记，表示记录当前 position 的位置，可以通过 reset() 恢复 mark 的位置
 *
 * postion <= limit <= capacity
 *
 *
 */
public class BufferTest {
    /**
     * Buffer 常用操作
     */
    @Test
    public void testBuffer(){
        String str = "abcde";

        ByteBuffer buf = ByteBuffer.allocate(1024);
        printLocation(buf);

        //写数据模式
        buf.put(str.getBytes());
        printLocation(buf);

        //切换读模式
        buf.flip();
        printLocation(buf);

        //利用 get() 读数据
        byte[] readResult = new byte[buf.limit()];
        buf.get(readResult);
        System.out.println(new String(readResult));
        printLocation(buf);

        //重复读数据
        buf.rewind();
        printLocation(buf);

        //清空缓存区, 但是缓冲中的数据还在，但是数据处于被遗忘状态
        buf.clear();
        printLocation(buf);

        System.out.println((char) buf.get());
    }

    @Test
    public void testBufferMark(){
        String str = "abcde";

        ByteBuffer buf = ByteBuffer.allocate(1024);

        //写数据模式
        buf.put(str.getBytes());

        //切换读模式
        buf.flip();


        //利用 get() 读数据
        byte[] readResult = new byte[buf.limit()];
        buf.get(readResult, 0, 2);

        //标记 mark
        buf.mark();
        printLocation(buf);
        buf.get(readResult, 0, 2);


        //reset() 恢复到 mark 的位置
        buf.reset();
        printLocation(buf);
    }

    private void printLocation(Buffer buf){
        System.out.println("----------------------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

    }
}
