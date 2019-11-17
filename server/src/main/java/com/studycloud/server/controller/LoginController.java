package com.studycloud.server.controller;

import com.studycloud.server.constant.CookieConstant;
import com.studycloud.server.constant.RedisConstant;
import com.studycloud.server.dataobject.UserInfo;
import com.studycloud.server.enums.ResultEnum;
import com.studycloud.server.enums.RoleEnum;
import com.studycloud.server.result.ResultVo;
import com.studycloud.server.service.UserService;
import com.studycloud.server.util.CookieUtil;
import com.studycloud.server.util.ResultVoUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/buyer")
    public ResultVo buyer(@RequestParam("openid")String openid, HttpServletResponse response){
        //1. openid和数据库数据匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if(userInfo == null){
            return ResultVoUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2. 判断角色
        if(RoleEnum.BUYER.getCode()!=userInfo.getRole()){
            return ResultVoUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3. 设置cookie
        CookieUtil.set(response, CookieConstant.OPENID,openid,CookieConstant.expire);

        return ResultVoUtil.success();
    }

    @GetMapping("/seller")
    public ResultVo seller(@RequestParam String openid,
                           HttpServletRequest request,
                           HttpServletResponse response){

       //判断是否已登录 （访问cookies不断写入）
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie!=null
                && !Strings.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVoUtil.success();
        }
        //1. openid和数据库数据匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if(userInfo == null){
            return ResultVoUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2. 判断角色
        if(RoleEnum.SELLER.getCode()!=userInfo.getRole()){
            return ResultVoUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3. 写入redis设置key=UUID , value=xyz
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set
                (String.format(RedisConstant.TOKEN_TEMPLATE,token),
                        openid,CookieConstant.expire,
                        TimeUnit.SECONDS);

        //4. 设置cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.expire);

        return ResultVoUtil.success();
    }

}
