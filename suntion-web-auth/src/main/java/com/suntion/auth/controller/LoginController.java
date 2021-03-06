package com.suntion.auth.controller;

import com.suntion.auth.model.AuthUser;
import com.suntion.auth.service.AuthService;
import com.suntion.common.constants.AuthConstants;
import com.suntion.common.lang.ResponseEntity;
import com.suntion.core.aspect.LogOperation;
import com.suntion.core.jwt.JwtAuthenticationToken;
import com.suntion.core.jwt.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @version V1.0
 * @Description: 登陆跳转
 * @author: suntion@yeah.net
 * @date: 2018年8月3日 下午2:21:581
 */
@RestController
@RequestMapping("auth")
public class LoginController {

    @Autowired
    AuthService authService;


    @Autowired
    StringRedisTemplate redisTemplate;

    @PostMapping("/unauth/login")
    @LogOperation("使用账号密码登陆")
    public Object unauthLogin(String username, String password) {
        Assert.notNull(username, "请输入账户");
        Assert.notNull(password, "请输入密码");
        AuthUser authUser = authService.accountlogin(username, password);
        if (authUser != null) {
            String authToken = JwtTokenUtil.createToken(username, AuthConstants.TTLMILLS, AuthConstants.SECRETKET);
            JwtAuthenticationToken statelessToken = new JwtAuthenticationToken(authToken);
            //UsernamePasswordToken authenticationToken = new UsernamePasswordToken("adamin","admin");
            //TelCheckCodeAuthenticationToken telCheckCodeAuthenticationToken = new TelCheckCodeAuthenticationToken("adamin","admin","1");

            SecurityUtils.getSubject().login(statelessToken);
            return ResponseEntity.success().setResult(authToken);
        }
        return ResponseEntity.failed().setResult("账号密码错误");
    }

    @PostMapping("/unauth/register")
    @LogOperation("使用账号密码登陆")
    public Object unauthRegister(String username, String password) {
        Assert.notNull(username, "请输入账户");
        Assert.notNull(password, "请输入密码");

        Integer integer = authService.accountRegeister(username, password);
        return ResponseEntity.success().setResult(integer);
    }

}
