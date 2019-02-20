package com.brs.idm.persistence.entity;

import com.brs.idm.api.User;
import lombok.Data;

/**
 * @author tiny lin
 * @date 2019/2/20
 */
@Data
public class UserEntity implements User {
    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String pwd;
    private String email;
    private String pictureId;
}

