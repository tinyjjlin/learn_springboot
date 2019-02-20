package com.brs.idm.persistence.dao;

import com.brs.idm.api.Privilege;
import com.brs.idm.persistence.entity.PrivilegeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author tiny lin
 * @date 2019/2/20
 */
public interface PrivilegeDao {
    /**
     * add new privilege
     * @param privilege
     */
    @Insert("insert into brs_idm_priv(id_,name_)values(#{privilege.id},#{privilege.name})")
    void insertPrivilege(@Param("privilege") PrivilegeEntity privilege);
}
