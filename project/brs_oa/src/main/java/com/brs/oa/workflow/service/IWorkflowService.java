package com.brs.oa.workflow.service;


import com.brs.oa.workflow.entity.LeaveType;
import com.brs.oa.workflow.entity.Workflow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-14
 */
public interface IWorkflowService  {


    /**
     * 新增工作流
     * @param workflow
     * @return
     */
    int add( Workflow workflow);

    /**
     * 获取申请人工作流
     * @param empNo
     * @param leaveTypeCode
     * @param selectDate
     * @param approvalStatus
     * @return
     */
    List<Workflow> getListByApplicant(Integer empNo,Integer leaveTypeCode,String selectDate,Integer approvalStatus);

    /**
     * 获取审批人工作流
     * @param empNo
     * @param leaveTypeCode
     * @param selectDate
     * @param approvalStatus
     * @return
     */
    List<Workflow> getListByApprover(Integer empNo,Integer leaveTypeCode,String selectDate,Integer approvalStatus);

    /**
     * 获取请假类型
     * @return
     */
    List<LeaveType> getLeaveTypeList();

    /**
     * 按月获取请假工作流
     * @param empNo
     * @param selectMonth
     * @return
     */
    List<Workflow> getListByApplicantAndMonth(Integer empNo,String selectMonth);

    /**
     * 审核人进行审核
     * @param empNo
     * @param serialNumber
     * @param approvalStatus
     * @return
     */
    int approval (Integer empNo,Integer serialNumber,Integer approvalStatus);
    /**
     * 申请人更新申请材料
     * @param empNo
     * @param serialNumber
     * @param proveImg
     * @return
     */
    int updateApplicantMaterials(Integer empNo,Integer serialNumber,String proveImg );
}
