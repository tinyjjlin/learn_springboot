package com.brs.oa.workflow.web;

import com.brs.oa.workflow.model.RestResult;
import com.brs.oa.workflow.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tiny lin
 * @date 2019/2/14
 */
@RestController
@Api
public class UserController {
    @Autowired
   private UserService userService;


    @ApiOperation(value = "创建新的用户")
    @PostMapping("/api/user")
    RestResult createUser(@RequestBody User user){
         userService.saveUser(user);
        return new RestResult(200,"create user success!");
    }

}
