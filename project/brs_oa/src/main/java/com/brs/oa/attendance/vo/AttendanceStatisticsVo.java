package com.brs.oa.attendance.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tiny lin
 * @date 2018/12/18
 */
@Data
public class AttendanceStatisticsVo {

    /**
     * 员工号
     */
    private int empNo;


    /**
     * 实际工作天数（出勤天数）
     */
    private double realWorkDay;

    /**
     * 出勤日期列表
     */
    private List<String> attendanceDateList;

    /**
     * 一般迟到（五分钟以内的迟到）
     */
    private int commonLateCount;

    /**
     * 严重迟到(30分钟以内的迟到)
     */
    private int seriousLateCount;

    /**
     * 早退次数（15分钟以内）
     */
    private int leaveEarlyCount;

    /**
     * 忘记刷卡次数(缺卡)
     */
    private int lackOfCardCount;

    /**
     * 旷工天数
     */
    private double absentDay;

    /**
     *   节假日
     */

    private int holidayDayCount;
    /**
     * 休息日天数
     */
    private int restDayCount;


    /**
     * 年假
     */
    private double annualLeaveDay;

    /**
     * 事假
     */
    private double personalLeaveDay;
    /**
     * 病假
     */
    private double sickLeaveDay;

    /**
     * 婚假
     */
    private double marriageLeaveDay;

    /**
     * 产假
     */
    private double maternityLeaveDay;

    /**
     * 丧假
     */
    private double funeralLeaveDay;

    /**
     * 陪产假
     */
    private double paternityLeaveDay;

    /**
     * 例假
     */
    private double casualLeaveDay;

    /**
     * 调休
     */
    private double daysOffDay;


}
