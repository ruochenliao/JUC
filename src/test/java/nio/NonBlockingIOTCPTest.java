package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
public class NonBlockingIOTCPTest {

    //客户端
    @Test
    public void client() throws IOException {
        //1、获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2、设置为非阻塞
        sChannel.configureBlocking(false);

        FileChannel inChannel = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);

        //3、分配指定大小的缓冲数据
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4、发送数据给服务端
        buf.put(Instant.now().toString().getBytes());
        buf.flip();
        sChannel.write(buf);
        buf.clear();

        //5、关闭通道
        sChannel.close();
    }

    @Test
    public void server() throws IOException {
        //1、获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        //2、切换成飞阻塞模式
        ssChannel.configureBlocking(false);

        //3、绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //4、获取选择器
        Selector selector = Selector.open();

        //5、将通道注册到选择器上, 并且指定监听接收事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6、轮询式获取选择器上已经"准备就绪"的时间
        while(selector.select() > 0){
            //7、选择器中所有注册的"选择健（已就绪的监听事件）"
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                //8、判断具体什么事件准备就绪
                if(key.isAcceptable()){
                    //9、若准备就绪，就获取客户端连接
                    SocketChannel sChannel = ssChannel.accept();
                    //10、切换非阻塞模式
                    sChannel.configureBlocking(false);
                    //11、将该通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    //12、获取当前选择器上"读就绪"通道
                    SocketChannel sChannel = (SocketChannel) key.channel();
                    //13、读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int length = 0;
                    while((length = sChannel.read(buffer)) != -1){
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, length));
                        buffer.clear();
                    }
                }
                //取消选择键
                iterator.remove();
            }
        }
    }
}
