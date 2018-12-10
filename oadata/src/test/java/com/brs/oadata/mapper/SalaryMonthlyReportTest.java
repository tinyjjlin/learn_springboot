package com.brs.oadata.mapper;


import com.brs.oadata.salary.mapper.SalaryMonthlyReportMapper;
import com.brs.oadata.salary.service.impl.SalaryMonthlyReportServiceImpl;
import com.brs.oadata.salary.vo.SimpleSalaryVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author tiny lin
 * @date 2018/12/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalaryMonthlyReportTest {

    @Autowired(required = false)
    private SalaryMonthlyReportMapper salaryMonthlyMapper;

    @Autowired
    private SalaryMonthlyReportServiceImpl salaryMonthlyReportService;
    @Test
    public void test(){
        List<SimpleSalaryVo> list = salaryMonthlyMapper.queryPersonalSalaryList(100057,null,null);
        for (SimpleSalaryVo salaryMonthly : list) {
            System.out.println(salaryMonthly.getSettlementDay());
        }
    }

    @Test
    public void test2(){
      PageInfo pageInfo =  salaryMonthlyReportService.getPersonalSalaryListByPage(100057,null ,null ,new Page(1,10));
        System.out.println("pageinfo--->"+pageInfo.getPageNum());


    }
}
