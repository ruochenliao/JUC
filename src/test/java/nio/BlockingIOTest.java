package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 使用 NIO 完成网络通信的三个核心
 * 1、通道（Channel）负责连接
 *  SelectableChannel
 *      SocketChannel   (TCP)
 *      ServerSocketChannel (TCP)
 *      DatagramChannel (UDP)
 *
 *      pipe.SinkChannel
 *      pipe.SourceChannel
 *
 * 2、缓冲区（Buffer）负责数据存取
 * 3、选择器（Selector）是 SelectableChannel 的多路复用器，用于监控 SelectableChannel IO 的状况
 */
public class BlockingIOTest {

    //客户端
    @Test
    public void client() throws IOException {
        //1、获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        FileChannel inChannel = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);

        //2、分配指定大小的缓冲数据
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3、读取本地文件，并发送到服务端
        while(inChannel.read(buf) != -1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //4、关闭通道
        inChannel.close();
        sChannel.close();
    }

    @Test
    public void server() throws IOException {
        //1、获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("5.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //2、绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //3、获取客户端连接通道
        SocketChannel sChannel = ssChannel.accept();

        //4、分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //4、接收客户端的数据, 并保存到本地
        while(sChannel.read(buffer) != -1){
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        sChannel.close();
        ssChannel.close();
        outChannel.close();
    }
}
