package com.brs.oa.login.controller;

import com.alibaba.fastjson.JSONObject;
import com.brs.oa.comm.annotation.Authentication;
import com.brs.oa.comm.jwt.JwtSupport;
import com.brs.oa.login.dto.LoginDto;
import com.brs.oa.comm.model.RestfulResult;
import com.brs.oa.comm.model.TokenModel;
import com.brs.oa.login.dto.UserInfoDto;
import com.brs.oa.login.service.ILdapService;
import com.brs.oa.login.service.ITokenService;
import com.brs.oa.role.service.IRoleService;
import com.brs.oa.staff.service.IStaffService;
import com.brs.oa.staff.vo.SimpleStaffVo;
import com.brs.oa.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tiny lin
 * @date 2018/11/29
 */
@RestController
@Api("用户登录接口")
public class LoginController {
    static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ILdapService ldapService;
    @Autowired
    private ITokenService tokenService;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IRoleService  roleService;





    @PostMapping("/api/login")
    @ApiOperation(value = "用户登录",notes = "用户名，密码就是登录计算机账号密码")
    public RestfulResult login(HttpServletRequest request,@RequestBody LoginDto loginDto){
        try {
         //   ldapService.authentication(loginDto);
            LOG.info("user login=============>"+loginDto.getUsername()+","+loginDto.getPassword());
            Integer empNo = CommonUtil.getEmpNo(loginDto.getUsername());
            String loginAddr = request.getRemoteAddr();
            System.out.println("loginAdd---->"+loginAddr );
            System.out.println("loginHost--->"+loginAddr );
            TokenModel tokenModel = tokenService.generateToken(loginDto.getUsername(),empNo);
            staffService.insertLoginInfo(empNo, loginAddr);
            return new RestfulResult(200, "登录成功!", tokenModel);
        } catch (Exception e) {
            return new RestfulResult(4001, e.getMessage(),null);
        }
    }


    @GetMapping("/api/logout")
    @ApiOperation(value = "退出系统")
    @Authentication
    public RestfulResult logout(@RequestHeader("Authorization")String token){
        String loginName = JwtSupport.getLoginName(token);
        tokenService.deleteToken(loginName);
        return new RestfulResult(200, "退出操作成功!");
    }

    @PostMapping("/api/verifyLoginPwd")
    @ApiOperation(value = "登录验证")
    public RestfulResult loginVerify(@RequestHeader("Authorization")String token,@RequestBody JSONObject jsonObject){
         String loginName = JwtSupport.getLoginName(token);
         System.out.println("loginName==============================>"+loginName);
        System.out.println("password==============================>"+jsonObject.get("password").toString());
         LoginDto loginDto = new LoginDto();
         loginDto.setUsername(loginName);
         loginDto.setPassword(jsonObject.get("password").toString());
        try {
            ldapService.authentication( loginDto);

        } catch (Exception e) {
            return new RestfulResult(401, e.getMessage(),"");
        }
        return new RestfulResult(200, "登录成功!", "");
    }

    @GetMapping("/api/login/userInfo")
    @ApiOperation(value = "获取登录用户基本信息")
    public RestfulResult userInfo(@RequestHeader("Authorization")String token){
          Integer empNo = JwtSupport.getEmployeeNo(token);
        SimpleStaffVo simpleSalaryVo = staffService.getStaffBaseInfo(empNo);
        List<String> roleList = roleService.getRoleNamesByEmpNo(empNo);
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setAvatar(simpleSalaryVo.getPicture());
        userInfoDto.setName(simpleSalaryVo.getName());
        userInfoDto.setEmpNo(simpleSalaryVo.getEmpNo());
        userInfoDto.setRoles(roleList);
        userInfoDto.setIntroduction("贝偌偲员工");
        return new RestfulResult(200, "退出操作成功!",userInfoDto);
    }


    @PostMapping("/api/loginValidation")
    @ApiOperation(value = "用户登录",notes = "用户名，密码就是登录计算机账号密码")
    public RestfulResult loginValidation(HttpServletRequest request,@RequestBody LoginDto loginDto){
        String loginHost =request.getRemoteHost();
        System.out.println("login host--------------------------->"+loginHost);

        if(loginDto == null || TextUtils.isEmpty(loginDto.getUsername())){
            return new RestfulResult(250, "该账号没有登录！",null);
        }
        if(!tokenService.loginValidation(loginDto.getUsername())){
            return new RestfulResult(250, "该账号没有登录", null);
        }else{
            Integer empNo = CommonUtil.getEmpNo(loginDto.getUsername());
            String loginAddr = staffService.selectLoginAddr(empNo);
            return new RestfulResult(451, "该账号"+loginDto.getUsername()+"已经在"+loginAddr+"登录,是否继续登录？", null);
        }
    }

}