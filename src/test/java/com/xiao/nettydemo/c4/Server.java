package com.xiao.nettydemo.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.xiao.nettydemo.ByteBufferUtil.debugRead;

@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        // 使用 NIO 来理解阻塞模式 , 单线程
        // 0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 1. 创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // 2. 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        // 3. 链接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            // 4. accept , 建立与客户端的连接 , SocketChannel 用来与客户端的通信
            log.debug("connecting...");
            SocketChannel sc = ssc.accept(); // 阻塞方法, 线程停止运行
            log.debug("connected... {}",sc);
            channels.add(sc);

            for (SocketChannel channel : channels) {
                // 5. 接受客户端发送的数据
                log.debug("before read... {}",channel);
                channel.read(buffer);   // 阻塞方法, 线程停止运行
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read... {}",channel);
            }
        }

    }

}
