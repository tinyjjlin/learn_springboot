package com.brs.oa.role.dto;

import lombok.Data;

import java.util.List;

/**
 * @author tiny lin
 * @date 2019/1/15
 */
@Data
public class BindPermissionDto {
    private Integer roleNo;
    private List<Integer>permissionNoArr;
}
