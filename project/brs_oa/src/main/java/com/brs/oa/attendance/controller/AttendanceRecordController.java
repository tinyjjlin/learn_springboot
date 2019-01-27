package com.brs.oa.attendance.controller;


import com.alibaba.fastjson.JSONObject;
import com.brs.oa.attendance.service.IAttendanceRecordService;

import com.brs.oa.attendance.vo.AttendanceRecordVo;
import com.brs.oa.attendance.vo.AttendanceStatisticsVo;
import com.brs.oa.clockrecord.vo.ClockRecordVo;
import com.brs.oa.comm.jwt.JwtSupport;
import com.brs.oa.comm.model.RestfulResult;


import com.brs.oa.util.CalendarSupport;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 考勤（打卡）记录 前端控制器
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-18
 */
@RestController
@Api
public class AttendanceRecordController {

    @Autowired
    private IAttendanceRecordService attendanceRecordService;



    @ApiOperation(value = "查询自己的考勤月历")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "selectMonth", value = "指定月份", dataType = "String")
    })
    @GetMapping("/api/personal/attendanceRecord")
    public RestfulResult personalAttendanceRecord(@RequestHeader("Authorization")String token, @RequestParam("selectMonth") String selectMonth){
        Integer empNo = JwtSupport.getEmployeeNo(token);
        System.out.println("empNo...................."+empNo);
        System.out.println("selectMonth...................."+selectMonth);
//        String selectDateStr = null;
//        if(selectedDate != null){
//            selectDateStr = CalendarSupport.dateToStr(selectedDate);
//        }

        HashMap<String,Object> recordMap = attendanceRecordService.getRecordMapByMonth(empNo,selectMonth );

        return new RestfulResult(200,"查询自己的考勤月历操作成功！",  JSONObject.toJSON(recordMap) );
    }

    @ApiOperation("查询自己的考勤统计")
    @GetMapping("/api/personal/attendanceStatistics")
    public RestfulResult personalAttendanceStatistics(@RequestHeader("Authorization")String token, @RequestParam("selectMonth") String selectMonth){
        Integer empNo = JwtSupport.getEmployeeNo(token);
        System.out.println("empNo...................."+empNo);
        System.out.println("selectMonth...................."+selectMonth);

        AttendanceStatisticsVo attendanceStatisticsVo = null;
        if(!TextUtils.isEmpty(selectMonth)){
            attendanceStatisticsVo = attendanceRecordService.getStatisticsRecordByMonth(empNo, selectMonth);
        }


        return new RestfulResult(200,"员工查看考勤统计操作成功！",attendanceStatisticsVo );
    }




    @ApiOperation(value = "人事查看员工考勤记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "empNo", value = "员工工号", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "selectMonth", value = "指定月份", dataType = "string")

    })
    @GetMapping("/api/attendanceRecord")
    public RestfulResult attendanceRecordList(@RequestParam("empNo")Integer empNo, @RequestParam("selectMonth") String selectMonth){

//        String selectDateStr = null;
//        if(selectedDate != null){
//            selectDateStr = CalendarSupport.dateToStr(selectedDate);
//        }
        HashMap<String,Object> recordMap = attendanceRecordService.getRecordMapByMonth(empNo,selectMonth );

        return new RestfulResult(200,"人事查看员工考勤记录操作成功！",recordMap );
    }











    @ApiOperation("人事查询考勤统计")
    @GetMapping("/api/attendanceStatistics")
    public RestfulResult attendanceStatistics(@RequestParam("empNo")Integer empNo, @RequestParam("selectMonth") String selectMonth){

        System.out.println("empNo...................."+empNo);
        System.out.println("selectMonth...................."+selectMonth);
        AttendanceStatisticsVo attendanceStatisticsVo = attendanceRecordService.getStatisticsRecordByMonth(empNo, selectMonth);

        return new RestfulResult(200,"员工查看考勤统计操作成功！",attendanceStatisticsVo );
    }






}
