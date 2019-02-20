package com.brs.idm.persistence.dao;

import com.brs.idm.persistence.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;


/**
 * @author tiny lin
 * @date 2019/2/20
 */
public interface UserDao {
    /**
     * add new user
     * @param user
     */
    @Insert("insert into brs_idm_user (id_,first_,last_,display_name_,pwd_,email_,picture_id_)"+
            "values(#{user.id},#{user.firstName},#{user.lastName},#{user.displayName},#{user.pwd},#{user.email},#{user.pictureId})")
     void insertUser(@Param("user") UserEntity user);
}
