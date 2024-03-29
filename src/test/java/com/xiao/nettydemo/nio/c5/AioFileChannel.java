package com.xiao.nettydemo.nio.c5;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.xiao.nettydemo.nio.ByteBufferUtil.debugAll;

@Slf4j
public class AioFileChannel {

    public static void main(String[] args){
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("data.txt"), StandardOpenOption.READ)) {
            // 参数1 ByteBuffer
            // 参数2 读取的起始位置
            // 参数3 附件
            // 参数4 回调对象
            ByteBuffer buffer = ByteBuffer.allocate(16);
            log.debug("read begin....");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override   // read 成功
                public void completed(Integer result, ByteBuffer attachment) {
                    log.debug("read completed... {}",result);
                    attachment.flip();
                    debugAll(attachment);
                }
                @Override   // read Exc
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
            Thread.sleep(3000);
            log.debug("read end...");
        }catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
