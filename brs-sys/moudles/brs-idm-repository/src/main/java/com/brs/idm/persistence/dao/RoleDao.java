package com.brs.idm.persistence.dao;

import com.brs.idm.persistence.entity.RoleEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author tiny lin
 * @date 2019/2/20
 */
public interface RoleDao {
    /**
     * add new role
     * @param roleEntity
     */
    @Insert("insert into brs_idm_role(id_,name_,type_)values(#{role.id},#{role.name},#{role.type})")
    void insertRole(@Param("role")RoleEntity roleEntity);
}
