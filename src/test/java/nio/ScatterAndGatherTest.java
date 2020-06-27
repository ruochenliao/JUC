package nio;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

/**
 * 分散（scatter）与聚集（Gather）
 * 分散读取（Scattering Reads）:将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering writes）:将多个缓冲区的数据聚集到通道中
 *
 */
public class ScatterAndGatherTest {

    //分散
    @Test
    public void testScatter() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        //获取通道
        FileChannel channel  = randomAccessFile.getChannel();
        //分配缓冲区大小
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //分散读取
        ByteBuffer[] buffers = {buf1, buf2};
        channel.read(buffers);

        for(ByteBuffer byteBuffer:buffers){
            byteBuffer.flip();
        }

        System.out.println(new String(buffers[0].array(), 0, buffers[0].limit()));
        System.out.println(new String(buffers[1].array(), 0, buffers[0].limit()));

        //聚集写入
        RandomAccessFile randomAccessFile1 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel1 = randomAccessFile1.getChannel();
        channel1.write(buffers);
    }
}
