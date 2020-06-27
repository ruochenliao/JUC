package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.Instant;
import java.util.Iterator;

/**
 * UDP 的 NIO
 */
public class NonBlockingIOUDPTest {
    @Test
    public void send() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((Instant.now().toString() + " : hello world").getBytes());
        buffer.flip();
        datagramChannel.send(buffer, new InetSocketAddress("127.0.0.1", 9898));
        buffer.clear();

        datagramChannel.close();
    }

    @Test
    public void receive() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(9898));

        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);

        while(selector.select() > 0){
            //7、选择器中所有注册的"选择健（已就绪的监听事件）"
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                //8、判断具体什么事件准备就绪
                if(key.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    datagramChannel.receive(buffer);
                    buffer.flip();
                    System.out.println(new String(buffer.array(), 0, buffer.limit()));
                    buffer.clear();
                }
            }
            //取消选择键
            iterator.remove();
        }
    }
}
