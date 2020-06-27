package nio;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 一、通道 Channel
 * 通道 channel 类似传统的流
 * 但是 channel 不能直接访问数据，channel 只能和 Buffer 进行交互
 *
 * 二、通道实现类
 * 1、channel 不会像传统 IO 那样，每个连接都占用一个 IO，通道直通内存，JAVA NIO 中负责缓冲区中数据传输
 * 2、通道主要的实现类
 *  FileChannel（本地）
 *  SocketChannel（TCP）
 *  ServerSocketChannel（TCP）
 *  DatagramChannel(UDP)
 *
 * 三、获取通道
 * 1、Java 通过 getChannel();
 * 2、在 1.7 中 NIO2 针对各个通道提供了静态方法 open()
 * 3、在 1.7 中 NIO2 的 Files 工具类的 newByteChannel()
 *
 */
public class ChannelTest {

    /**
     * 利用通道完成文件的复制
     */
    @Test
    public void testChannel() {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fileInputStream = new FileInputStream("1.jpg");
            fileOutputStream = new FileOutputStream("2.jpg");

            //获取通道
            inChannel = fileInputStream.getChannel();
            outChannel = fileOutputStream.getChannel();

            //分配指定大小缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //读数据
            while (inChannel.read(buffer) != -1) {
                //切换成读取数据模式
                buffer.flip();
                //写数据
                outChannel.write(buffer);
                //清空缓冲区
                buffer.clear();
            }
        }catch (Exception e){

        }finally {
            if(inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 直接使用换成弄过去，完成文件复制
     * 使用内存映射文件的方式
     */
    @Test
    public void testDirectBuffer() throws IOException{
        FileChannel inChannel = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("3.png"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        //映射到物理内存中
        MappedByteBuffer inMapBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMapBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行读写操作
        byte[] byteBuffer = new byte[1024];
        inMapBuf.get(byteBuffer);
        outMapBuffer.put(byteBuffer);

        inChannel.close();
        outChannel.close();
    }

}
