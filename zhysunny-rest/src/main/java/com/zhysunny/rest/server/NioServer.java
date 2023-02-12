package com.zhysunny.rest.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * nio 服务端
 * @author 章云
 * @date 2019/11/13 20:36
 */
public class NioServer implements Server {

    private int port;
    private ServerSocketChannel channel;

    @Override
    public Server create(int port) throws IOException {
        this.port = port;
        this.channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(this.port));
        return this;
    }

    @Override
    public String accept(String request) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        Charset charset = Charset.forName("UTF-8");
        SocketChannel socketChannel = null;
        // 接收请求，阻塞的
        socketChannel = channel.accept();
        buf.flip();
        int read = socketChannel.read(buf);
        String result = null;
        while (read != -1) {
            buf.flip();
            CharBuffer charBuffer = charset.decode(buf);
            result = charBuffer.toString();
            buf.clear();
            read = socketChannel.read(buf);
        }
        socketChannel.close();
        return result;
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new NioServer();
        server.create(9999);
    }
}
