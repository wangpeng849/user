package com.studycloud.server.service.impl;

import com.netflix.discovery.converters.Auto;
import com.studycloud.server.dataobject.UserInfo;
import com.studycloud.server.mapper.UserMapper;
import com.studycloud.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userMapper.findByOpenid(openid );
    }
}
