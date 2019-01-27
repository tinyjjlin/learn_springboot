package com.brs.oa.workflow.mapper;


import com.brs.oa.workflow.entity.LeaveType;
import com.brs.oa.workflow.entity.Workflow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-14
 */
public interface WorkflowMapper {

    /**
     * 获取请假类型
     * @return
     */
    List<LeaveType> selectLeaveTypeList();

    /**
     * 添加新的工作流
     * @param workflow
     * @return
     */
    int insert(@Param("workflow") Workflow workflow);

    /**
     * 获取申请人的工作流
     * @param empNo
     * @param leaveTypeCode
     * @param selectDate
     * @param approvalStatus
     * @return
     */
    List<Workflow> selectListByApplicant(@Param("applicant")Integer empNo,@Param("leaveTypeCode")Integer leaveTypeCode,@Param("selectDate")String selectDate,@Param("approvalStatus")Integer approvalStatus);


    /**
     * 获取审核人的工作流
     * @param empNo
     * @param leaveTypeCode
     * @param selectDate
     * @param approvalStatus
     * @return
     */
    List<Workflow> selectListByApprover(@Param("approver")Integer empNo,@Param("leaveTypeCode")Integer leaveTypeCode,@Param("selectDate")String selectDate,@Param("approvalStatus")Integer approvalStatus);

    /**
     * 审核人更新审核状态
     * @param empNo
     * @param serialNumber
     * @param approvalStatus
     * @return
     */
    int updateApprovalStatus (@Param("approver")Integer empNo,@Param("serialNumber")Integer serialNumber,@Param("approvalStatus")Integer approvalStatus);

    /**
     * 申请人更新申请材料
     * @param empNo
     * @param serialNumber
     * @param proveImg
     * @return
     */
    int updateApplicantMaterials(@Param("applicant")Integer empNo,@Param("serialNumber")Integer serialNumber,@Param("proveImg")String proveImg );

    /**
     * 获取指定月份的请假数据
     * @param empNo
     * @param selectMonth
     * @return
     */
    List<Workflow> selectListByApplicantAndMonth(@Param("applicant")Integer empNo,@Param("selectMonth")String selectMonth);
}
