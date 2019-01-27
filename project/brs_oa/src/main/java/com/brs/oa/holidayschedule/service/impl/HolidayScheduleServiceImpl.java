package com.brs.oa.holidayschedule.service.impl;

import com.brs.oa.holidayschedule.entity.HolidaySchedule;
import com.brs.oa.holidayschedule.mapper.HolidayScheduleMapper;
import com.brs.oa.holidayschedule.service.IHolidayScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 放假安排 服务实现类
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-12
 */
@Service
public class HolidayScheduleServiceImpl  implements IHolidayScheduleService {

    @Autowired(required = false)
    private HolidayScheduleMapper holidayScheduleMapper;
    @Override
    public List<HolidaySchedule> selectListByMonth(String selectMonth) {
        return holidayScheduleMapper.selectListByMonth(selectMonth);
    }

    @Override
    public HolidaySchedule selectByDay(String selectedDate) {
        return holidayScheduleMapper.selectByDay(selectedDate);
    }

    @Override
    public List <HolidaySchedule> selectListRange(String startTime, String endTime) {
        return holidayScheduleMapper.selectListRange(startTime,endTime );
    }
}
