package com.zhysunny.rest.client;

import com.zhysunny.common.json.JsonUtils;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author zhysunny
 * @date 2020/8/23 21:38
 */
public class HttpClientUtils {

    public static RestResponse get(String url) throws Exception {
        HttpGet get = new HttpGet(url);
        try {
            return client(get);
        } catch (Exception e) {
            throw e;
        }
    }

    public static <T> T get(String url, Class<T> clz) throws Exception {
        try {
            return JsonUtils.parse(get(url).getResponse(), clz);
        } catch (Exception e) {
            throw e;
        }
    }

    public static RestResponse post(String url, Object request) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(JsonUtils.toJson(request), StandardCharsets.UTF_8));
        try {
            return client(post);
        } catch (Exception e) {
            throw e;
        }
    }

    public static <T> T post(String url, Object request, Class<T> clz) throws Exception {
        try {
            return JsonUtils.parse(post(url, request).getResponse(), clz);
        } catch (Exception e) {
            throw e;
        }
    }

    public static RestResponse patch(String url, Object request) throws Exception {
        HttpPatch patch = new HttpPatch(url);
        patch.setEntity(new StringEntity(JsonUtils.toJson(request), StandardCharsets.UTF_8));
        try {
            return client(patch);
        } catch (Exception e) {
            throw e;
        }
    }

    public static <T> T patch(String url, Object request, Class<T> clz) throws Exception {
        try {
            return JsonUtils.parse(patch(url, request).getResponse(), clz);
        } catch (Exception e) {
            throw e;
        }
    }

    public static RestResponse put(String url, Object request) throws Exception {
        HttpPut put = new HttpPut(url);
        put.setEntity(new StringEntity(JsonUtils.toJson(request), StandardCharsets.UTF_8));
        try {
            return client(put);
        } catch (Exception e) {
            throw e;
        }
    }

    public static <T> T put(String url, Object request, Class<T> clz) throws Exception {
        try {
            return JsonUtils.parse(put(url, request).getResponse(), clz);
        } catch (Exception e) {
            throw e;
        }
    }

    public static RestResponse delete(String url) throws Exception {
        HttpDelete delete = new HttpDelete(url);
        try {
            return client(delete);
        } catch (Exception e) {
            throw e;
        }
    }

    public static <T> T delete(String url, Class<T> clz) throws Exception {
        try {
            return JsonUtils.parse(delete(url).getResponse(), clz);
        } catch (Exception e) {
            throw e;
        }
    }

    private static RestResponse client(HttpRequestBase httpRequest) throws IOException {
        BufferedReader reader = null;
        httpRequest.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpRequest.setHeader("Accept", "application/json;charset=UTF-8");
        try (CloseableHttpClient client = HttpClientBuilder.create().build();
             CloseableHttpResponse response = client.execute(httpRequest)) {
            int statusCode = response.getStatusLine().getStatusCode();
            StringBuilder content = new StringBuilder();
            if (response.getEntity() != null) {
                reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            }
            return new RestResponse(statusCode, content.toString());
        } catch (Exception e) {
            throw e;
        } finally {
            httpRequest.releaseConnection();
            if (reader != null) {
                reader.close();
            }
        }
    }

}
