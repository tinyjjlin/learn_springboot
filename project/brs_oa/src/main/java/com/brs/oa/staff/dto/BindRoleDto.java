package com.brs.oa.staff.dto;

import lombok.Data;

import java.util.List;

/**
 * @author tiny lin
 * @date 2019/1/15
 */
@Data
public class BindRoleDto {
    private Integer empNo;
    private List<Integer> roleNoArr;
}
