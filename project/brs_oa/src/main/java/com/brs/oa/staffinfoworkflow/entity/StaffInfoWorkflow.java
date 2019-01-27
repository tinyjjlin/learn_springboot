package com.brs.oa.staffinfoworkflow.entity;


import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 员工重要信息变更工作流
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StaffInfoWorkflow implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer serialNumber;

    private String workName;

    private Integer applicant;
    private String applicantName;

    private Integer approver;
    private String approverName;

    private String applyTime;

    private Integer approvalStatus;

    private Integer flowCategory;

    private String flowCategoryDescription;

    private String firstItem;

    private String firstItemOldValue;

    private String firstItemNewValue;

    private String secondItem;

    private String secondItemOldValue;

    private String secondItemNewValue;


}
