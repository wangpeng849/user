package com.studycloud.server.dataobject;

import lombok.Data;

@Data
public class UserInfo {
    private String id;
    private String username;
    private String password;
    private String openid;
    private Integer role;
}
