package com.studycloud.server.result;

import lombok.Data;

@Data
public class ResultVo<T> {
    private Integer code;
    private String msg;
    /**
     * 数据
     */
    private T data;
}
