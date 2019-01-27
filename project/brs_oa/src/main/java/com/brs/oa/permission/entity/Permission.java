package com.brs.oa.permission.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer permissionNo;

    private String permissionName;

    private String permissionCode;

    private Integer isEnabled;

    /**
     * 表创建时间
     */
    private Date gmtCreate;

    /**
     * 表修改时间
     */
    private Date gmtModified;


}
