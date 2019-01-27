package com.brs.oa.workflow.service.impl;


import com.brs.oa.workflow.entity.LeaveType;
import com.brs.oa.workflow.entity.Workflow;
import com.brs.oa.workflow.mapper.WorkflowMapper;
import com.brs.oa.workflow.service.IWorkflowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-14
 */
@Service
public class WorkflowServiceImpl implements IWorkflowService {

    @Autowired(required = false)
    private WorkflowMapper workflowMapper;
    @Override
    public int add(Workflow workflow) {
        return workflowMapper.insert(workflow);
    }

    @Override
    public List <Workflow> getListByApplicantAndMonth(Integer empNo, String selectMonth) {
        return workflowMapper.selectListByApplicantAndMonth(empNo,selectMonth );
    }

    @Override
    public List<Workflow> getListByApplicant(Integer empNo,Integer leaveTypeCode,String selectDate,Integer approvalStatus) {
        return workflowMapper.selectListByApplicant(empNo,leaveTypeCode,selectDate,approvalStatus);
    }

    @Override
    public int updateApplicantMaterials(Integer empNo, Integer serialNumber, String proveImg) {
        return  workflowMapper.updateApplicantMaterials(empNo,serialNumber ,proveImg );
    }

    @Override
    public List <Workflow> getListByApprover(Integer empNo,Integer leaveTypeCode,String selectDate,Integer approvalStatus) {
        return workflowMapper.selectListByApprover(empNo,leaveTypeCode,selectDate,approvalStatus);
    }

    @Override
    public int approval(Integer empNo, Integer serialNumber, Integer approvalStatus) {
        return workflowMapper.updateApprovalStatus(empNo,serialNumber ,approvalStatus );
    }

    @Override
    public List <LeaveType> getLeaveTypeList() {
        return workflowMapper.selectLeaveTypeList();
    }
}

