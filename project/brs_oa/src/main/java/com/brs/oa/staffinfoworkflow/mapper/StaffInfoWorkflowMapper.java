package com.brs.oa.staffinfoworkflow.mapper;

import com.brs.oa.staffinfoworkflow.entity.StaffInfoWorkflow;
import com.brs.oa.workflow.entity.Workflow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 员工重要信息变更工作流 Mapper 接口
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-22
 */
public interface StaffInfoWorkflowMapper  {


    /**
     * 添加新的工作流
     * @param workflow
     * @return
     */
    int insert(@Param("workflow") StaffInfoWorkflow workflow);

    /**
     * 获取申请人的工作流
     * @param empNo
     * @param flowCategory
     * @param selectDate
     * @param approvalStatus
     * @return
     */
    List<StaffInfoWorkflow> selectListByApplicant(@Param("applicant")Integer empNo, @Param("flowCategory")Integer flowCategory, @Param("selectDate")String selectDate, @Param("approvalStatus")Integer approvalStatus);

    /**
     * 获取审核人的工作流
     * @param empNo
     * @param flowCategory
     * @param selectDate
     * @param approvalStatus
     * @return
     */
    List<StaffInfoWorkflow> selectListByApprover(@Param("approver")Integer empNo, @Param("flowCategory")Integer flowCategory, @Param("selectDate")String selectDate,@Param("approvalStatus")Integer approvalStatus);

    /**
     * 审核人更新审核状态
     * @param empNo
     * @param serialNumber
     * @param approvalStatus
     * @return
     */
    int updateApprovalStatus (@Param("approver")Integer empNo,@Param("serialNumber")Integer serialNumber,@Param("approvalStatus")Integer approvalStatus);

    /**
     * 查询审核通过的工作流
     * @return
     */
    StaffInfoWorkflow selectApprovedOne(@Param("approver")Integer empNo,@Param("serialNumber")Integer serialNumber);

}
