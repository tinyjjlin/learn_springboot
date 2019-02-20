package com.brs.idm.persistence.entity;

import com.brs.idm.api.Privilege;
import lombok.Data;

/**
 * @author tiny lin
 * @date 2019/2/20
 */
@Data
public class PrivilegeEntity implements Privilege {
    private String id;
    private String name;
}
