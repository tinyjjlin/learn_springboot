package com.brs.oadata.salary.controller;

import com.brs.oadata.comm.model.RestfulResult;
import com.brs.oadata.salary.mapper.SalaryMonthlyReportMapper;
import com.brs.oadata.salary.service.ISalaryMonthlyReportService;
import com.brs.oadata.salary.vo.ComplexSalaryVo;
import com.brs.oadata.salary.vo.SimpleSalaryVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-09
 */
@RestController
@RequestMapping("/salary")
public class SalaryMonthlyController {
    private static final Log LOG = LogFactory.getLog(SalaryMonthlyController.class);
    @Autowired(required = false)
    private ISalaryMonthlyReportService salaryMonthlyReportService;

    /**
     * 薪资查询（拥有权限的人才能查看员工的记录，一般为财务组）
     * @param empNo
     * @param startTime
     * @param endTime
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "财务组查看员工薪资记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始月份", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "截止月份", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页显示的行数", dataType = "int", defaultValue = "10")
    })
    @GetMapping("/api/staffs/{staffId}/salaries")
    // @NeedPermissions("salary:list")

    public RestfulResult searchStaffSalaryList(@PathVariable(value = "staffId")Integer empNo, @RequestParam(value = "startTime",required = false) Date startTime, @RequestParam(value = "endTime",required = false) Date endTime, @RequestParam(required = false,defaultValue = "1") Integer page, @RequestParam(required = false,defaultValue = "1") Integer size){
        LOG.info("staffList--------->empNo:"+empNo);
        PageHelper.startPage(page,size);

        PageInfo<ComplexSalaryVo> pageInfo = new PageInfo <>(salaryMonthlyReportService.getSalaryList(empNo, startTime,endTime));

        return new RestfulResult(200,"查看所有员工工资",pageInfo );
    }





    @ApiOperation(value = "我的薪资记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页显示的行数", dataType = "int", defaultValue = "10")
    })
    @GetMapping("/api/personal/salaries")

    public RestfulResult searchPersonalSalaryList(@RequestParam("empNo")Integer empNo, @RequestParam(value = "startTime",required = false) Date  startTime, @RequestParam(value = "endTime",required = false) Date endTime, @RequestParam(required = false,defaultValue = "1") Integer page, @RequestParam(required = false,defaultValue = "10") Integer size){
        PageHelper.startPage(page,size);
        List<SimpleSalaryVo> simpleSalaryVoList= salaryMonthlyReportService.getPersonalSalaryList(empNo, startTime,endTime);
        PageInfo<SimpleSalaryVo> pageInfo = new PageInfo <>(simpleSalaryVoList);
        return new RestfulResult(200,"我的薪资记录",pageInfo);
    }

}
