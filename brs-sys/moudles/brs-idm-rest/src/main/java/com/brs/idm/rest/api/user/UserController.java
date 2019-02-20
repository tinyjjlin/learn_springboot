package com.brs.idm.rest.api.user;

import com.brs.idm.api.IdmIdentityService;
import com.brs.idm.persistence.entity.UserEntity;
import com.brs.idm.service.impl.IdmIdentityServiceImpl;
import com.brs.sys.common.model.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author tiny lin
 * @date 2019/2/20
 */
@RestController
@Api
public class UserController {

    @Autowired
    private IdmIdentityServiceImpl identityService;

    /**
     * add new user
     * @param userEntity
     * @return
     */
    @ApiOperation("add new user")
    @PostMapping("/api/idm/user")
    public RestResult  createNewUser(@RequestBody UserEntity userEntity){
        identityService.saveUser(userEntity);
        return new RestResult(200, "return user info",null);
    }
}
