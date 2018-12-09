package com.brs.oadata.salary.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SalaryMonthly implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工工号
     */
    private Integer empNo;

    /**
     * 日期
     */
    private LocalDate settlementDay;

    /**
     * 基本工资
     */
    private BigDecimal basicSalary;

    /**
     * 浮动工资
     */
    private BigDecimal bonusSalary;

    /**
     * 提成
     */
    private BigDecimal commission;

    /**
     * 事假扣除
     */
    private BigDecimal deduction;

    /**
     * 个人税前工资
     */
    private BigDecimal payment;

    /**
     * 个人缴纳社保
     */
    private BigDecimal socialInsuranceOfStaff;

    /**
     * 个人所得税
     */
    private BigDecimal taxOfStaff;

    /**
     * 个人税后工资（到手）
     */
    private BigDecimal paymentInHand;

    /**
     * 公司缴纳社保
     */
    private BigDecimal socialInsuranceOfCompany;

    /**
     * 公司支出合计
     */
    private BigDecimal expenditure;

    /**
     * 补充说明
     */
    private String description;

    /**
     * 岗位津贴
     */
    private BigDecimal jobAllowance;

    /**
     * 交通补贴
     */
    private BigDecimal travelAllowance;

    /**
     * 餐费补贴
     */
    private BigDecimal mealAllowance;

    /**
     * 表创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 表修改时间
     */
    private LocalDateTime gmtModified;


}
