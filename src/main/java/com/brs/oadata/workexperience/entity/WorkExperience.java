package com.brs.oadata.workexperience.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 工作经历
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WorkExperience implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 起止时间
     */
    private String startEndTime;

    /**
     * 工作单位
     */
    private String companyName;

    /**
     * 职务
     */
    private String position;

    /**
     * 离职原因
     */
    private String reasonToLeave;

    /**
     * 证明人
     */
    private String certifier;

    /**
     * 证明人电话
     */
    private String certifierMobile;

    /**
     * 员工工号
     */
    private Integer staffEmpNo;


}
