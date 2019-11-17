package com.studycloud.server.service;

import com.studycloud.server.dataobject.UserInfo;

public interface UserService {
    UserInfo findByOpenid(String openid);
}
