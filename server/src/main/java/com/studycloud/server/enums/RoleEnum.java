package com.studycloud.server.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum RoleEnum {
    BUYER(1,"买家"),
    SELLER(2,"卖家");

    private Integer code;
    private String message;

     RoleEnum(Integer code,String message){
        this.code  = code;
        this.message = message;
    }
}
