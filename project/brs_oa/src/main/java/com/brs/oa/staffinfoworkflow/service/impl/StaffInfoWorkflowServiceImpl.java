package com.brs.oa.staffinfoworkflow.service.impl;


import com.brs.oa.staffinfoworkflow.entity.StaffInfoItem;
import com.brs.oa.staffinfoworkflow.entity.StaffInfoWorkflow;
import com.brs.oa.staffinfoworkflow.mapper.StaffInfoItemMapper;
import com.brs.oa.staffinfoworkflow.mapper.StaffInfoWorkflowMapper;
import com.brs.oa.staffinfoworkflow.service.IStaffInfoWorkflowService;

import com.brs.oa.workflow.entity.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 员工重要信息变更工作流 服务实现类
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-22
 */
@Service
public class StaffInfoWorkflowServiceImpl  implements IStaffInfoWorkflowService {
    @Autowired(required = false)
    private StaffInfoItemMapper staffInfoItemMapper;

    @Autowired(required = false)
    private StaffInfoWorkflowMapper staffInfoWorkflowMapper;
    @Override
    public List<StaffInfoItem> getStaffInfoItemList() {
        return staffInfoItemMapper.selectList();
    }

    @Override
    public int insert(StaffInfoWorkflow workflow) {
        return staffInfoWorkflowMapper.insert(workflow);
    }

    @Override
    public List <StaffInfoWorkflow> selectListByApplicant(Integer empNo, Integer flowCategory, String selectDate, Integer approvalStatus) {
        return staffInfoWorkflowMapper.selectListByApplicant(empNo,flowCategory ,selectDate ,approvalStatus );
    }

    @Override
    public StaffInfoWorkflow selectApprovedOne(Integer empNo, Integer serialNumber) {
        return staffInfoWorkflowMapper.selectApprovedOne(empNo,serialNumber  );
    }

    @Override
    public List <StaffInfoWorkflow> selectListByApprover(Integer empNo, Integer flowCategory, String selectDate, Integer approvalStatus) {
        return staffInfoWorkflowMapper.selectListByApprover(empNo,flowCategory ,selectDate ,approvalStatus );
    }

    @Override
    public int updateApprovalStatus(Integer empNo, Integer serialNumber, Integer approvalStatus) {
        return staffInfoWorkflowMapper.updateApprovalStatus(empNo,serialNumber ,approvalStatus );
    }
}
