package com.brs.oa.staffinfoworkflow.service;


import com.brs.oa.staffinfoworkflow.entity.StaffInfoItem;
import com.brs.oa.staffinfoworkflow.entity.StaffInfoWorkflow;
import com.brs.oa.workflow.entity.Workflow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工重要信息变更工作流 服务类
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-22
 */
public interface IStaffInfoWorkflowService {
    /**
     * 获取需要申请修改的信息条目列表
     * @return
     */
    List<StaffInfoItem> getStaffInfoItemList();

    /**
     * 添加新的工作流
     * @param workflow
     * @return
     */
    int insert( StaffInfoWorkflow workflow);

    /**
     * 获取申请人的工作流
     * @param empNo
     * @param flowCategory
     * @param selectDate
     * @param approvalStatus
     * @return
     */
    List<StaffInfoWorkflow> selectListByApplicant(Integer empNo, Integer flowCategory, String selectDate, @Param("approvalStatus")Integer approvalStatus);

    /**
     * 获取审核人的工作流
     * @param empNo
     * @param flowCategory
     * @param selectDate
     * @param approvalStatus
     * @return
     */
    List<StaffInfoWorkflow> selectListByApprover(Integer empNo,Integer flowCategory, String selectDate, Integer approvalStatus);

    /**
     * 审核人更新审核状态
     * @param empNo
     * @param serialNumber
     * @param approvalStatus
     * @return
     */
    int updateApprovalStatus (Integer empNo,Integer serialNumber,Integer approvalStatus);

    /**
     * 查询审核通过的工作流
     * @return
     */
    StaffInfoWorkflow selectApprovedOne(Integer empNo,Integer serialNumber);

}
