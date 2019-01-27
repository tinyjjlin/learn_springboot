package com.brs.oa.attendance.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author tiny lin
 * @date 2018/12/18
 */
@Data
public class AttendanceRecordVo {


    /**
     * 员工工号
     */
    private Integer empNo;

    /**
     * 员工姓名
     */
    private String empName;

    /**
     * 签到时间
     */
    private String clockInTime;

    /**
     * 签退时间
     */
    private String clockOutTime;

    /**
     * 上班状态，0正常，1迟到A 2迟到B，3缺卡，4旷工
     */
    private int onStatusCode=-1;
    private String onStatus;

    /**
     * 下班状态，0正常，1早退，3缺卡，4旷工
     */
    private int offStatusCode=-1;
    private String offStatus;

    /**
     * 日期类型：0代表工作日，1代表休息日，2代表节假日
     */
    private int dayType;

    /**
     * 节假日
     */
    private String festival;

    /**
     * 请假类型
     */
    private int leaveTypeCode =-1;


}
