package com.brs.oa.workflow.controller;


import com.brs.oa.comm.jwt.JwtSupport;
import com.brs.oa.comm.model.RestfulResult;
import com.brs.oa.workflow.entity.LeaveType;
import com.brs.oa.workflow.entity.Workflow;
import com.brs.oa.workflow.service.IWorkflowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-14
 */
@RestController
@Api
public class WorkflowController {


    @Autowired
    private IWorkflowService workflowService;



    @ApiOperation(value = "查看申请人工作流列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "flowType", value = "请假类型", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "selectedDate", value = "选择日期", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页显示的数据行数", dataType = "int", defaultValue = "10")})

    @GetMapping("/api/workflow/applicant")
    public RestfulResult applicantWorkFlowList(@RequestHeader("Authorization") String token,@RequestParam(value = "flowType",required = false)Integer flowType,@RequestParam(value = "selectedDate",required = false)String selectDate,@RequestParam(value = "approvalStatus",required = false) Integer approvalStatus,@RequestParam(value = "page",required = false, defaultValue = "1") Integer page, @RequestParam(value = "limit",required = false, defaultValue = "10") Integer size){
        System.out.println("flowType-->"+flowType);
        System.out.println("selectDate--->"+selectDate);
        if(TextUtils.isEmpty(selectDate)){
            selectDate = null;
        }
        Integer empNo = JwtSupport.getEmployeeNo(token);
        PageHelper.startPage(page,size );
        List<Workflow> workflowList = workflowService.getListByApplicant(empNo,flowType,selectDate,approvalStatus);
        PageInfo pageInfo = new PageInfo(workflowList);
        return new RestfulResult(200,"查看申请人工作流",pageInfo);
    }




    @ApiOperation(value = "查看审批人工作流列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "flowType", value = "请假类型", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "selectedDate", value = "选择日期", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页显示的数据行数", dataType = "int", defaultValue = "10")})
    @GetMapping("/api/workflow/approver")
    public RestfulResult approverWorkFlowList(@RequestHeader("Authorization") String token,@RequestParam(value = "flowType",required = false)Integer leaveTypeCode,@RequestParam(value = "selectedDate",required = false)String selectDate,@RequestParam(value = "approvalStatus",required = false) Integer approvalStatus,@RequestParam(value = "page",required = false, defaultValue = "1") Integer page, @RequestParam(value = "limit",required = false, defaultValue = "10") Integer size){
        if(TextUtils.isEmpty(selectDate)){
            selectDate = null;
        }
        Integer empNo = JwtSupport.getEmployeeNo(token);
        PageHelper.startPage(page,size );
        List<Workflow> workflowList = workflowService.getListByApprover(empNo,leaveTypeCode,selectDate,approvalStatus);
        PageInfo pageInfo = new PageInfo(workflowList);
        return new RestfulResult(200,"查看审批人人工作流",pageInfo );
    }



    /**
     * 审批
     * @return
     */

    @ApiOperation(value = "审批人进行审批")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "serialNumber", value = "工作流", dataType = "int", required = true),
            @ApiImplicitParam(paramType = "query", name = "approvalStatus", value = "审核值", dataType = "int", required = true)
    })
    @PatchMapping("/api/workflow/approval")
    public RestfulResult approval(@RequestHeader("Authorization") String token,@RequestParam("serialNumber")Integer serialNumber,@RequestParam("approvalStatus")Integer approvalStatus){
        Integer empNo = JwtSupport.getEmployeeNo(token);
        int result = workflowService.approval(empNo, serialNumber,approvalStatus );

        return  new RestfulResult(200,"审批人进行审批",result );
    }



    /**
     *申请
     */
    @ApiOperation(value = "员工申请")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "token值", dataType = "String",required = true)
    @PostMapping("/api/workflow/apply")
    public RestfulResult  apply(@RequestHeader("Authorization") String token, @RequestBody Workflow workflow){
        Integer empNo = JwtSupport.getEmployeeNo(token);
        //pre handler
        if(workflow == null){
            return  new RestfulResult(451,"请假申请失败！",null );
        }
        if(TextUtils.isEmpty(workflow.getStartTime()) || TextUtils.isEmpty(workflow.getEndTime()) || TextUtils.isEmpty(workflow.getContent())){
            return  new RestfulResult(451,"请假申请失败！",null );
        }
        //申请人
        workflow.setApplicant(empNo);
        int result = workflowService.add(workflow);
        return  new RestfulResult(200,"申请",result );
    }

    @PatchMapping("/api/workflow/proveImg")
    public RestfulResult  addWorkFlowMaterials(@RequestHeader("Authorization") String token,@RequestParam("serialNumber")Integer serialNumber,@RequestParam("proveImg")String proveImg){
        Integer empNo = JwtSupport.getEmployeeNo(token);
        int result = workflowService.updateApplicantMaterials(empNo, serialNumber, proveImg);

        return  new RestfulResult(200,"添加工作流图片",result );
    }


    @GetMapping("/api/leaveType")
    @ApiOperation("请假类型列表")
    public RestfulResult getLeaveTypeList(){
        List<LeaveType> leaveTypeList = workflowService.getLeaveTypeList();
        return  new RestfulResult(200,"请假类型列表",leaveTypeList );
    }
}
