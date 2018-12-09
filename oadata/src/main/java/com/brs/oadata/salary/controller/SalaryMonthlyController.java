package com.brs.oadata.salary.controller;

import com.brs.oadata.salary.domain.SalaryMonthly;
import com.brs.oadata.salary.mapper.SalaryMonthlyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
//   // @Autowired
//  //  private ISalaryMonthlyService salaryMonthlyService;
//
    @Autowired
    private SalaryMonthlyMapper salaryMonthlyMapper;
//    @GetMapping("/salaries")
//    public String  searchSalaries(){
//        salaryMonthlyService.list();
//        return "test";
//    }
    @GetMapping("/salaries/2")
    public PageInfo  searchSalaries2(){
        PageHelper.startPage(1,10);
       List<SalaryMonthly> salaryMonthlies =salaryMonthlyMapper.list();
        PageInfo<SalaryMonthly> pageInfo = new PageInfo<>(salaryMonthlies);
        return pageInfo;
    }

}
