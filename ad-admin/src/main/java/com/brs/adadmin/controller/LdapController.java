package com.brs.adadmin.controller;

import com.brs.adadmin.jwt.JwtSupport;
import com.brs.adadmin.model.RestfulResult;
import com.brs.adadmin.service.LdapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

/**
 * @author tiny lin
 * @date 2018/12/20
 */

@RestController
public class LdapController {
    static final Logger LOG = LoggerFactory.getLogger(LdapController.class);
    @Autowired
    private LdapService ldapService;
    @PostMapping("api/user/login")
    public RestfulResult login(@RequestParam("username")String username,@RequestParam("password")String password){
        Boolean result =ldapService.ldapLoginVerify(username,password );
        RestfulResult restfulResult = new RestfulResult();
        if(result){
            // generate token
            String token = JwtSupport.genereateToken(username);
            Map<String,String> tokenMap = new HashMap <>(1);
            tokenMap.put("token",token );
            LOG.info("token..................."+token);
            restfulResult.setCode(200);
            restfulResult.setMsg("login successful!");
            restfulResult.setData(tokenMap);
        }else{
            restfulResult.setCode(400);
            restfulResult.setMsg("login failed!");
        }
        return restfulResult;
    }

}
