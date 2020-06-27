package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * PIPE 是两个线程之间单向连接
 *
 * PIPE 有一个 source 通道和一个 sink 通道。
 * 数据会被写到 sink 通道，从source 通道读取
 */
public class PipeTest {
    @Test
    public void testPipe() throws IOException {
        //1、获取管道
        Pipe pipe = Pipe.open();

        //2、将缓冲区数据写入管道
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sinkChannel = pipe.sink();
        buffer.put("通过管道发送".getBytes());
        buffer.flip();
        sinkChannel.write(buffer);

        //3、读取管道中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer receiverBufer = ByteBuffer.allocate(1024);
        int length = sourceChannel.read(receiverBufer);
        System.out.println(new String(receiverBufer.array(), 0, length));
    }
}
