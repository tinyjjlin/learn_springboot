package com.brs.oa.department.service;


import com.brs.oa.department.entity.Department;
import com.brs.oa.department.vo.DepartmentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-05
 */
public interface IDepartmentService  {

    /**
     * 添加并返回主键
     * @param department
     * @return
     */
    public Integer addAndReturnPrimaryKey( Department department);

    public Integer  add( Department department);

    public Integer  edit(Department department);

    public Integer delete(Integer deptNo);

    public boolean deleteMulti(Integer deptNo);

    public List<Department> getDeptList();

    public List<DepartmentVo> getDepartmentVoList();



}
