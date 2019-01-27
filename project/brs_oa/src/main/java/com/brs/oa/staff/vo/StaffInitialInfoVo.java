package com.brs.oa.staff.vo;

import lombok.Data;

/**
 * @author tiny lin
 * @date 2019/1/25
 */
@Data
public class StaffInitialInfoVo {

    /**
     * 员工号
     */
    private Integer empNo;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 部门号
     */
    private Integer deptNo;
    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 职位
     */
    private Integer positionNo;
    /**
     * 职位名称
     */
    private String positionName;
}
