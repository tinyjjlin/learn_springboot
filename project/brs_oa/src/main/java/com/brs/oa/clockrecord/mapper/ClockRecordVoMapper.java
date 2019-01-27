package com.brs.oa.clockrecord.mapper;

import com.brs.oa.clockrecord.entity.ClockRecord;
import com.brs.oa.clockrecord.vo.ClockRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author tiny lin
 * @date 2019/1/9
 */
public interface ClockRecordVoMapper {

    /**
     * 添加员工打卡记录
     * @param clockRecord
     * @return
     */
    int insert(@Param("clockRecord") ClockRecord clockRecord);
    /**
     * 获取员工的打卡记录(指定时间范围)
     * @param empNo
     * @param startTime
     * @param endTime
     * @return
     */
    List<ClockRecordVo> selectRecordListRange(@Param("empNo")Integer empNo, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查看员工打卡记录（指定日期）
     * @param empNo
     * @param selectedDate
     * @return
     */
    ClockRecordVo selectRecordByDay(@Param("empNo")Integer empNo,@Param("selectedDate") String selectedDate);

    /**
     * 查看员工的打卡记录列表（指定日期）
     * @param empNo
     * @param selectedDate
     * @return
     */
    List<ClockRecordVo> selectRecordList(@Param("empNo")Integer empNo,@Param("selectedDate") String selectedDate);

    /**
     * 查看员工打卡记录（指定月份）
     * @param empNo
     * @param selectedMonth
     * @return
     */
    List<ClockRecordVo> selectRecordListByMonth(@Param("empNo")Integer empNo, @Param("selectedMonth") String selectedMonth);
}
