package com.brs.oa.clockrecord.controller;


import com.brs.oa.clockrecord.entity.ClockRecord;
import com.brs.oa.clockrecord.service.IClockRecordService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 打卡记录 前端控制器
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-09
 */
@RestController
@Api("打卡记录模块")
public class ClockRecordController {
    @Autowired
    private IClockRecordService clockRecordService;



    /**
     * 个人保存打卡记录
     */

    @ApiOperation(value = "保存打卡记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "token值", dataType = "String",required = true)
    })
    @PostMapping("/api/clockRecord")
    public RestfulResult save(@RequestHeader("Authorization") String token, @RequestBody ClockRecord clockRecord){
        Integer empNo = JwtSupport.getEmployeeNo(token);
        clockRecord.setEmpNo(empNo);
        int result = clockRecordService.save(clockRecord);
        return new RestfulResult(200,"签到成功！",result );
    }





    /**
     * 个人查看打卡记录
     * @param token
     * @param selectedDate
     * @return
     */
    @ApiOperation(value = "查看个人打卡记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "token值", dataType = "String",required = true),
            @ApiImplicitParam(paramType = "query", name = "selectedDate", value = "指定日期", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页显示的数据行数", dataType = "int", defaultValue = "10")
    })
    @GetMapping("/api/personal/clockRecord")
    public RestfulResult personalClockRecordList(@RequestHeader("Authorization") String token, @RequestParam(value = "selectedDate",required = false) Date selectedDate,@RequestParam(value = "page",required = false, defaultValue = "1") Integer page, @RequestParam(value = "limit",required = false, defaultValue = "10") Integer size){
        Integer empNo = JwtSupport.getEmployeeNo(token);
        System.out.println("selectedDate----------================>"+selectedDate+",empNo--->"+empNo);

        String selectDateStr = null;
        if(selectedDate != null){
            selectDateStr = CalendarSupport.dateToStr(selectedDate);
        }
        System.out.println("selectedDate-----str-----================>"+selectDateStr);
        PageHelper.startPage(page,size );
        List<ClockRecordVo> clockRecordVoList = clockRecordService.selectClockRecordList(empNo,selectDateStr);
        PageInfo pageInfo = new PageInfo(clockRecordVoList);
        return  new RestfulResult(200,"查看指定日期的打卡记录",pageInfo);
    }


    /**
     * 人事查看打开记录
     * @param empNo
     * @param selectedDate
     * @param page
     * @param size
     * @return
     */

    @ApiOperation(value = "查看员工打卡记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "token值", dataType = "String",required = true),
            @ApiImplicitParam(paramType = "query", name = "empNo", value = "员工号", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "selectedDate", value = "指定日期", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页显示的数据行数", dataType = "int", defaultValue = "10")
    })
    @GetMapping("/api/clockRecord")
    public RestfulResult clockRecordList(@RequestParam(value = "empNo",required = false)Integer empNo, @RequestParam(value = "selectedDate",required =false) Date selectedDate,@RequestParam(value = "page",required = false, defaultValue = "1") Integer page, @RequestParam(value = "limit",required = false, defaultValue = "10") Integer size){
        String selectDateStr = null;
        if(selectedDate != null){
            selectDateStr = CalendarSupport.dateToStr(selectedDate);
        }
        PageHelper.startPage(page,size );
        List<ClockRecordVo> clockRecordVoList = clockRecordService.selectClockRecordList(empNo, selectDateStr);
        PageInfo pageInfo = new PageInfo(clockRecordVoList);
        return  new RestfulResult(200,"查看打卡记录",pageInfo);
    }



    @ApiOperation(value = "查看员工指定时间范围打卡记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", value = "token值", dataType = "String",required = true),
            @ApiImplicitParam(paramType = "query", name = "empNo", value = "员工号", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始日期", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束日期", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页显示的数据行数", dataType = "int", defaultValue = "10")
    })
    @GetMapping("/api/clockRecord/range")
    public RestfulResult clockRecordListRange(@RequestParam(value = "empNo",required = false)Integer empNo, @RequestParam(value = "startTime",required =false) Date startTime, @RequestParam(value = "endTime",required =false) Date endTime,@RequestParam(value = "page",required = false, defaultValue = "1") Integer page, @RequestParam(value = "limit",required = false, defaultValue = "10") Integer size){
        String startTimeStr = null;
        String endTimeStr = null;
        if(startTime != null){
            startTimeStr = CalendarSupport.dateToStr(startTime);
        }
        if(endTime != null){
            endTimeStr = CalendarSupport.dateToStr(endTime);
        }
        PageHelper.startPage(page,size );
        List<ClockRecordVo> clockRecordVoList = clockRecordService.selectClockRecordListRange(empNo, startTimeStr,endTimeStr);
        PageInfo pageInfo = new PageInfo(clockRecordVoList);
        return  new RestfulResult(200,"查看员工指定时间范围打卡记录",pageInfo);
    }




}
