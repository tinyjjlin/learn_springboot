package com.brs.oadata.salary.service.impl;



import com.brs.oadata.salary.mapper.SalaryMonthlyReportMapper;
import com.brs.oadata.salary.service.ISalaryMonthlyReportService;
import com.brs.oadata.salary.vo.ComplexSalaryVo;
import com.brs.oadata.salary.vo.SimpleSalaryVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-04
 */
@Service
public class SalaryMonthlyReportServiceImpl implements ISalaryMonthlyReportService {

    @Autowired(required = false)
    private SalaryMonthlyReportMapper salaryMonthlyReportMapper;


    @Override
    public PageInfo <SimpleSalaryVo> getPersonalSalaryListByPage(Integer empNo, Date startTime, Date endTime, Page page) {
        PageHelper.startPage(page);
        List<SimpleSalaryVo> simpleSalaryVoList= getPersonalSalaryList(empNo, startTime,endTime);
        PageInfo<SimpleSalaryVo> pageInfo = new PageInfo <>(simpleSalaryVoList);
        return pageInfo;
    }

    @Override
    public PageInfo <ComplexSalaryVo> getSalaryListByPage(Integer empNo, Date startTime, Date endTime, Page page) {
        PageHelper.startPage(page);
        return new PageInfo <>(getSalaryList(empNo,startTime ,endTime ));
    }

    @Override
    public List<SimpleSalaryVo> getPersonalSalaryList(Integer empNo, Date startTime, Date endTime) {
        return salaryMonthlyReportMapper.queryPersonalSalaryList(empNo,startTime ,endTime );
    }

    @Override
    public List <ComplexSalaryVo> getSalaryList(Integer empNo, Date startTime, Date endTime) {
        return salaryMonthlyReportMapper.querySalaryList(empNo,startTime ,endTime);
    }


//    @Override
//    @QueryFilter(voName = "SimpleSalaryVo")
//    public JSONObject getOwnSalaryInfoFilter(Integer empNo) {
//       // return (JSONObject) JSONObject.toJSON(getOwnSalaryInfo(empNo));
//        return null;
//    }
//
//
//
//    @Override
//    @QueryFilter(voName = "ComplexSalaryVo")
//    public JSONArray getPageListFilter(Page <ComplexSalaryVo> page) {
//       return null;
//    }
}
