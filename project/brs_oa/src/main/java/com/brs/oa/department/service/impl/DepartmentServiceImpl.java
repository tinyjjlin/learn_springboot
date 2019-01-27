package com.brs.oa.department.service.impl;


import com.brs.oa.department.entity.Department;
import com.brs.oa.department.mapper.DepartmentMapper;
import com.brs.oa.department.service.IDepartmentService;

import com.brs.oa.department.vo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-05
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired(required = false)
    private DepartmentMapper departmentMapper;
    @Override
    public Integer add(Department department) {
        return departmentMapper.insert(department);
    }

    @Override
    public Integer addAndReturnPrimaryKey(Department department) {
        departmentMapper.insert(department);
        return departmentMapper.selectPrimaryKey(department.getDeptName());
    }

    @Override
    public Integer edit(Department department) {
        return departmentMapper.update(department);
    }

    @Override
    public boolean deleteMulti(Integer deptNo) {
        //递归删除
        List<Department> departmentList = getDeptList();
        if(departmentList!= null && departmentList.size()>0){
            for (Department department : departmentList) {
                 if(department.getParentNo().equals(deptNo) ){

                 }
            }
        }
       return true;
    }

    @Override
    public Integer delete(Integer deptNo) {
        return departmentMapper.delete(deptNo);
    }

    @Override
    public List<Department> getDeptList() {
        return departmentMapper.selectList();
    }

    @Override
    public List <DepartmentVo> getDepartmentVoList() {
        return departmentMapper.selectDepartmentVoList();
    }
}
