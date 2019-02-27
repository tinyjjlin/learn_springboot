package com.brs.oa.workflow.service;

import org.flowable.idm.api.Group;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tiny lin
 * @date 2019/2/12
 */
@Service
public class UserService {
    @Autowired
    private IdmIdentityService idmIdentityService;

    /**
     * 创建新的用户
     * @param user
     */
   public  void saveUser(User user ){
       idmIdentityService.saveUser(user);
    }


    /**
     * 创建新的组
     * @param group
     */
    public void saveGroup(Group group){
        idmIdentityService.saveGroup(group);
    }

    /**
     * 创建权限信息
     * @param privName
     */
    public void createPrivilege(String privName){
        idmIdentityService.createPrivilege(privName);
    }

    /**
     * 关联用户和组
     * @param userId
     * @param groupId
     */
    public void createMembership(String userId,String groupId){
        idmIdentityService.createMembership(userId,groupId );
    }

    /**
     * 用户关联权限
     * @param userId
     * @param privilegeId
     */
    public void addUserPrivilegeMapping(String userId,String privilegeId){
         idmIdentityService.addUserPrivilegeMapping(userId,privilegeId );
    }

    /**
     * 组管理权限
     * @param groupId
     * @param privilegeId
     */
    public void addGroupPrivilegeMapping(String groupId,String privilegeId){
        idmIdentityService.addGroupPrivilegeMapping(groupId,privilegeId );
    }

}
