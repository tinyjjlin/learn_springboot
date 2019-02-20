package com.brs.idm.api;





import java.util.List;

/**
 * @author tiny lin
 * @date 2019/2/20
 */
public interface IdmIdentityService {
    void saveUser(User user);
    void saveRole(Role role);
    void savePrivilege(Privilege privilege);
    void deleteUser(String userId);
    void deleteRole(String roleId);
    void deletePrivilege(String privilegeId);

    void addUserRoleMapping(String userId,String roleId);
    void deleteUserRoleMapping(String userId,String  roleId);
    void addUserPrivilegeMapping(String userId,String privilegeId);
    void deleteUserPrivilegeMapping(String userId,String privilegeId);
    void addRolePrivilegeMapping(String roleId,String privilegeId);
    void deleteRolePrivilegeMapping(String roleId,String privilegeId);

    List<User> getUserWithRole(String roleId);
    List<User> getUserWithPrivilege(String privilegeId);
    List<Role> getRoleWithPrivilege(String privilegeId);

    User getUser(String userId);
    Role  getRole(String roleId);
    Privilege getPrivilege(String privilegeId);
}
