package nio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * 1、直接缓冲区 VS 非直接缓冲区
 *
 * 非直接缓冲区
 *  通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区
 *  通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 *  能提高效率是因为直接用了物理内存，在物理内存中直接建立缓冲区，减少了物理内存和程序之间的复制
 *  但是内存不易控制
 */
public class BufferDirectAllocateTest {
    @Test
    public void testAllocateDirect(){
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
    }
}
