package com.brs.oa.department.entity;


import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-05
 */
@Data
public class Department implements Serializable {
    private Integer deptNo;
    private String deptName;
    private Integer parentNo;

}
