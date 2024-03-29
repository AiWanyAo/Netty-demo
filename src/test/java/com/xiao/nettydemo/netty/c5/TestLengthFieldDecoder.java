package com.xiao.nettydemo.netty.c5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 预设长度
 */
public class TestLengthFieldDecoder {

    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG),
                // 最大帧长度, 长度值偏移量, 长度字节值, 去除前4个字节的内容
                new LengthFieldBasedFrameDecoder(1024,0,4,1,4)
        );

        // 4 个字节的内容长度, 实际内容
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer,"Hello,World");
        send(buffer,"Hi!");
        channel.writeInbound(buffer);
    }

    private static void send(ByteBuf buffer,String content) {
        byte[] bytes = content.getBytes(); // 实际内容
        int length = bytes.length;  // 实际内容长度
        buffer.writeInt(length);
        buffer.writeByte(1);
        buffer.writeBytes(bytes);
    }

}
