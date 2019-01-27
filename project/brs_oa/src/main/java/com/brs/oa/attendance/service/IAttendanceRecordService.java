package com.brs.oa.attendance.service;



import com.brs.oa.attendance.vo.AttendanceRecordVo;
import com.brs.oa.attendance.vo.AttendanceStatisticsVo;


import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 考勤（打卡）记录 服务类
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-18
 */
public interface IAttendanceRecordService{



    /**
     * 查看员工考勤月历
     * @param empNo
     * @param selectedMonth 2019-1
     * @return
     */
    HashMap<String,Object> getRecordMapByMonth(Integer empNo, String selectedMonth);

    /**
     * 月考勤统计
     * @param empNo
     * @param selectedMonth
     * @return
     */
    public AttendanceStatisticsVo getStatisticsRecordByMonth(Integer empNo, String selectedMonth);


    /**
     * 查看指定日期员工的考勤数据
     * @param empNo
     * @param selectedDay
     * @return
     */
    public List<AttendanceRecordVo> getRecordByDay(Integer empNo ,String selectedDay);

    /**
     * 查看指定时间范围内的员工的考勤数据
     * @param empNo
     * @param startTime
     * @param endTime
     * @return
     */
    public List<AttendanceRecordVo> getRecordListRange(Integer empNo,String startTime,String endTime);

}
