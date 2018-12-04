package com.tiny.springbootvalidation.controller;

import com.tiny.springbootspringvalidation.bean.Result;
import com.tiny.springbootspringvalidation.dto.StaffDto;
import com.tiny.springbootspringvalidation.support.DateEditor;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author tiny lin
 * @date 2018/12/2
 */
@RestController
@RequestMapping("/user")
@Api(value = "user controller")
public class UserController {

    @InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }
    @PostMapping("/save")
    public Result  saveUser(@Validated  @RequestBody StaffDto staffDto){
       return new Result(200,"save successful!",staffDto);
    }
}
