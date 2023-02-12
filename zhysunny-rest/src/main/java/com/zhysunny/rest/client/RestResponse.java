package com.zhysunny.rest.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhysunny
 * @date 2020/8/20 23:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse {
    private Integer code;
    private String response;
}
