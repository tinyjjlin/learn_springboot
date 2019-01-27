package com.brs.oa.staff.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author tiny lin
 * @date 2019/1/25
 */
@Data
public class StaffGeneralInfoVo {

    /**
     * 工号
     */
    private Integer empNo;

    /**
     * 1 员工姓名
     */
    private String empName;
    /**
     * 2 性别
     */
    private Integer gender;

    /**
     * 3 出生日期
     */
    private LocalDate birthday;
    /**
     * 4 政治面貌
     */
    private String politicsStatus;

    /**
     * 5 民族
     */
    private String nation;
    /**
     * 6 籍贯
     */
    private String nativePlace;



    /**
     * 7 手机号
     */
    private String mobile;
    /**
     * 8 邮箱
     */
    private String email;





    /**
     * 9 最高学历
     */
    private String highestDegree;


    /**
     * 10 婚姻状况
     */
    private Integer maritalStatus;

    /**
     * 11 现居住地址
     */
    private String currentAddress;

    /**
     * 12 地址
     */
    private String address;

    /**
     * 13 中华人民共和国身份证号
     */
    private String nationIdcard;

    /**
     * 14 社保账号
     */
    private String socialInsuranceAccount;

    /**
     * 15 银行卡名称
     */
    private String bankName;
    /**
     * 16 银行卡号
     */
    private String bankCard;


    /**
     * 17 紧急联系人
     */
    private String emergencyContact;
    /**
     * 18 紧急联系人电话
     */
    private String emergencyContactPhone;
}
