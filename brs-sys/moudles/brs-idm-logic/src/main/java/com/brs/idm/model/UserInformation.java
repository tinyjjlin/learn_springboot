package com.brs.idm.model;

import com.brs.idm.persistence.entity.RoleEntity;
import com.brs.idm.persistence.entity.UserEntity;
import lombok.Data;

import java.util.List;


/**
 * @author tiny lin
 * @date 2019/2/20
 */
@Data
public class UserInformation {
    protected UserEntity user;
    private List<RoleEntity> roles;
    private List<String>privileges;
}
