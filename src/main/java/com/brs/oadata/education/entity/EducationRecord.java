package com.brs.oadata.education.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 教育及培训记录(从高中开始)
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EducationRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 起止时间
     */
    private String startEndTime;

    /**
     * 专业内容
     */
    private String major;

    /**
     * 学历证书
     */
    private String diplomas;
    /**
     * 员工工号
     */

    private Integer staffEmpNo;


}
