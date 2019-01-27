package com.brs.oa.holidayschedule.mapper;


import com.brs.oa.holidayschedule.entity.HolidaySchedule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 放假安排 Mapper 接口
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-12
 */
public interface HolidayScheduleMapper {
    /**
     * 获取指定月份对应的放假，调休安排
     * @param selectedMonth
     * @return
     */
   List<HolidaySchedule> selectListByMonth(@Param("selectedMonth") String selectedMonth);

    /**
     * 获取指定时间范围内的放假，调休安排
     * @param startTime
     * @param endTime
     * @return
     */
   List<HolidaySchedule> selectListRange(@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 查看指定日期放假，调休安排
     * @param selectedDate
     * @return
     */
   HolidaySchedule selectByDay(@Param("selectedDate")String selectedDate);
}