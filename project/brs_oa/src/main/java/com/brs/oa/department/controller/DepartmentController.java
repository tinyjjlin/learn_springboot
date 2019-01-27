package com.brs.oa.department.controller;

import com.brs.oa.comm.annotation.Authentication;

import com.brs.oa.comm.model.RestfulResult;
import com.brs.oa.department.entity.Department;
import com.brs.oa.department.service.IDepartmentService;
import com.brs.oa.department.vo.DepartmentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-05
 */
@RestController

@Api(value = "部门管理")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    private final static Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);


    /**
     * 分页查看公司所有部门信息列表
     * @return
     */
    @ApiOperation(value = "分页查看公司所有部门信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页显示的数据行数", dataType = "int", defaultValue = "10")})
    @ApiResponse(code = 200,message = "分页查看部门信息",response = List.class)
    @GetMapping("/api/department/pagination")

    public RestfulResult getDeptListByPage(@RequestParam(value = "page",required = false, defaultValue = "1") Integer page, @RequestParam(value = "limit",required = false, defaultValue = "10") Integer size){
        PageHelper.startPage(page,size );
        List<Department> departmentList= departmentService.getDeptList();
        PageInfo pageInfo = new PageInfo(departmentList);

        return new RestfulResult(200,"分页查看公司所有部门信息操作成功！",pageInfo );
    }
    /**
     * 查看公司所有部门信息列表
     * @return
     */
    @ApiOperation(value = "查看公司所有部门信息")
    @ApiResponse(code = 200,message = "查看部门信息",response = List.class)
    @GetMapping("/api/department")

    public RestfulResult getDeptList(){
        List<Department> departmentList= departmentService.getDeptList();

        //TODO
        //转换为树形结构
        return new RestfulResult(200,"获取部门列表",departmentList );
    }

    /**
     * 查看部门和员工信息
     * @return
     */
    @ApiOperation(value = "查看部门和员工信息")
    @ApiResponse(code = 200,message = "查看部门信息",response = List.class)
    @GetMapping("/api/department/more")
    @Authentication
    public RestfulResult getDeptAndStaffList(){
        List<DepartmentVo> departmentVoList= departmentService.getDepartmentVoList();
        return new RestfulResult(200,"查看部门和员工信息",departmentVoList );
    }



    /**
     * 添加信息的部门
     * @param department
     * @return
     */
    @ApiOperation(value = "添加新的部门")
    @PostMapping("/api/department")

    public RestfulResult add(@RequestBody Department department) {
        int primarykey  =  departmentService.addAndReturnPrimaryKey(department);
        return new RestfulResult(200, "添加新的部门操作成功！",primarykey) ;
    }


    /**
     * 删除一个部门
     * @param deptNo
     * @return
     */
    @ApiOperation(value = "管理员删除一个部门")
    @DeleteMapping("/api/department/{deptNo}")

    public RestfulResult delete(@PathVariable("deptNo")Integer deptNo){
       int result = departmentService.delete(deptNo);
        return new RestfulResult(200,"删除部门操作成功！",result );
    }



    /**
     * 修改部门信息
     * @param department
     * @return
     */
    @ApiOperation(value = "管理员修改部门信息")
    @PatchMapping("/api/department")
    public RestfulResult edit(@RequestBody Department department){
         int result = departmentService.edit(department);
        return new RestfulResult(200,"编辑部门操作成功！" ,result);
    }
}
