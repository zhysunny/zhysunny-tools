package com.zhysunny.rest.server;

import java.io.IOException;

/**
 * @author zhysunny
 * @date 2020/8/20 23:36
 */
public interface Server {
    Server create(int port) throws IOException;

    String accept(String request) throws IOException;

    void close() throws IOException;
}
