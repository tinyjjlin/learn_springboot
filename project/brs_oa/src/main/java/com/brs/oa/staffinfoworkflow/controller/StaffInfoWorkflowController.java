package com.brs.oa.staffinfoworkflow.controller;


import com.alibaba.fastjson.JSONObject;
import com.brs.oa.comm.jwt.JwtSupport;
import com.brs.oa.comm.model.RestfulResult;
import com.brs.oa.staff.service.IStaffService;
import com.brs.oa.staffinfoworkflow.entity.StaffInfoItem;
import com.brs.oa.staffinfoworkflow.entity.StaffInfoWorkflow;
import com.brs.oa.staffinfoworkflow.service.IStaffInfoWorkflowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.applet.resources.MsgAppletViewer_sv;

import java.util.List;

/**
 * <p>
 * 员工重要信息变更工作流 前端控制器
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-22
 */
@RestController
public class StaffInfoWorkflowController {

    @Autowired
    private IStaffService staffService;
    @Autowired
    private IStaffInfoWorkflowService staffInfoWorkflowService;

    @ApiOperation("员工重要信息条目")
    @GetMapping("/api/staffInfoItem")
    public RestfulResult getStaffInfoItemList(){
        List<StaffInfoItem> staffInfoItemList = staffInfoWorkflowService.getStaffInfoItemList();
        return new RestfulResult(200,"员工重要信息条目",staffInfoItemList );
    }


    @ApiOperation(value = "查看申请人工作流列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "flowCategory", value = "员工信息条目", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "selectedDate", value = "选择日期", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页显示的数据行数", dataType = "int", defaultValue = "10")})

    @GetMapping("/api/staffWorkflow/applicant")
    public RestfulResult applicantWorkFlowList(@RequestHeader("Authorization") String token,@RequestParam(value = "flowCategory",required = false)Integer flowCategory,@RequestParam(value = "selectedDate",required = false)String selectDate,@RequestParam(value = "approvalStatus",required = false) Integer approvalStatus,@RequestParam(value = "page",required = false, defaultValue = "1") Integer page, @RequestParam(value = "limit",required = false, defaultValue = "10") Integer size){
        System.out.println("flowType-->"+flowCategory);
        System.out.println("selectDate--->"+selectDate);
        if(TextUtils.isEmpty(selectDate)){
            selectDate = null;
        }

        Integer empNo = JwtSupport.getEmployeeNo(token);
        PageHelper.startPage(page,size );
        List<StaffInfoWorkflow> workflowList = staffInfoWorkflowService.selectListByApplicant(empNo,flowCategory,selectDate,approvalStatus);
        PageInfo pageInfo = new PageInfo(workflowList);
        return new RestfulResult(200,"查看申请人工作流",pageInfo);
    }




    @ApiOperation(value = "查看审批人工作流列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "flowCategory", value = "员工信息条目", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "selectedDate", value = "选择日期", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页显示的数据行数", dataType = "int", defaultValue = "10")})
    @GetMapping("/api/staffWorkflow/approver")
    public RestfulResult approverWorkFlowList(@RequestHeader("Authorization") String token,@RequestParam(value = "flowCategory",required = false)Integer flowCategory,@RequestParam(value = "selectedDate",required = false)String selectDate,@RequestParam(value = "approvalStatus",required = false) Integer approvalStatus,@RequestParam(value = "page",required = false, defaultValue = "1") Integer page, @RequestParam(value = "limit",required = false, defaultValue = "10") Integer size){
        if(TextUtils.isEmpty(selectDate)){
            selectDate = null;
        }

        Integer empNo = JwtSupport.getEmployeeNo(token);
        PageHelper.startPage(page,size );

       System.out.println("empNo-----approver----->"+empNo);
        System.out.println("selectedDate---------->"+selectDate);
        System.out.println("flowCategory---------->"+flowCategory);
        System.out.println("approvalStatus---------->"+approvalStatus);


        List<StaffInfoWorkflow> workflowList = staffInfoWorkflowService.selectListByApprover(empNo,flowCategory,selectDate,approvalStatus);
        System.out.println("workflowList---------->"+ JSONObject.toJSON(workflowList));
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
    @PatchMapping("/api/staffWorkflow/approval")
    public RestfulResult approval(@RequestHeader("Authorization") String token, @RequestParam("serialNumber")Integer serialNumber, @RequestParam("approvalStatus")Integer approvalStatus){
        Integer empNo = JwtSupport.getEmployeeNo(token);
        int result = staffInfoWorkflowService.updateApprovalStatus(empNo, serialNumber,approvalStatus );
        if(result >0){
            //审核通过更新用户信息
            StaffInfoWorkflow staffInfoWorkflow = staffInfoWorkflowService.selectApprovedOne(empNo,serialNumber);
            //审核通过数据
            if(staffInfoWorkflow != null){
                staffService.updateStaffItems(staffInfoWorkflow);
            }
        }
        int code =200;
        String msg ="";
        if(approvalStatus!= null){
            if(approvalStatus ==1){
                code= 200;
                msg ="审核通过";
            }else if(approvalStatus ==2){
                code =4003;
                msg="审核不通过！";
            }

        }
        return  new RestfulResult(code,msg,result );
    }


    /**
     *申请
     */
    @ApiOperation(value = "员工申请")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "token值", dataType = "String",required = true)
    @PostMapping("/api/staffWorkflow/apply")
    public RestfulResult  apply(@RequestHeader("Authorization") String token, @RequestBody StaffInfoWorkflow workflow){
        Integer empNo = JwtSupport.getEmployeeNo(token);
        //申请人
        workflow.setApplicant(empNo);
        int result = staffInfoWorkflowService.insert(workflow);
        return  new RestfulResult(200,"申请",result );
    }

}