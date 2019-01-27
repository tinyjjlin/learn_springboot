package com.brs.oa.staff.service.impl;


import com.brs.oa.staff.entity.EducationRecord;
import com.brs.oa.staff.entity.TrainRecord;
import com.brs.oa.staff.entity.WorkRecord;
import com.brs.oa.staff.mapper.StaffMapper;
import com.brs.oa.staff.service.IStaffService;
import com.brs.oa.staff.vo.*;
import com.brs.oa.staffinfoworkflow.entity.StaffInfoWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-04
 */
@Service

public class StaffServiceImpl implements IStaffService {

    @Autowired(required = false)
    private StaffMapper staffMapper;

    @Override
    public int createNewStaff(StaffInitialInfoVo staffInitialInfoVo) {
        return staffMapper.insertNewStaff(staffInitialInfoVo);
    }

    @Override
    public int updateStaffGeneralInfo(StaffGeneralInfoVo staffGeneralInfoVo) {
        return staffMapper.updateStaffGeneralInfo(staffGeneralInfoVo);
    }

    @Override
    public List <StaffInitialInfoVo> selectStaffInitialInfoList(Integer empNo) {
        return staffMapper.selectStaffInitialInfoList(empNo);
    }

    @Override
    public int updateNewStaffInfo(StaffInitialInfoVo staffInitialInfoVo) {
        return staffMapper.updateNewStaffInfo(staffInitialInfoVo);
    }

    @Override
    public List <StaffGeneralInfoVo> selectStaffGeneralInfoList(Integer empNo) {
        return staffMapper.selectStaffGeneralInfoList(empNo);
    }

    @Override
    public int updateStaffItems(StaffInfoWorkflow workflowList) {
        return staffMapper.updateStaffItems(workflowList);
    }

    @Override
    public String selectLoginAddr(Integer empNo) {
        return staffMapper.selectLoginAddr(empNo);
    }

    @Override
    public int insertLoginInfo(Integer empNo, String loginAddr) {
        return staffMapper.insertLoginInfo(empNo,loginAddr );
    }

    @Override
    public int updateLoginStatus(Integer empNo, Integer loginStatus) {
        return staffMapper.updateLoginStatus(empNo,loginStatus );
    }

    @Override
    public int getLoginStatus(Integer empNo) {
        return staffMapper.selectLoginStatus(empNo);
    }

    @Override
    public int updateStaffHeaderImg(Integer empNo, String imageUrl) {
        return staffMapper.updateStaffHeaderImg(empNo,imageUrl );
    }

    @Override
    public int bindRole(Integer empNo, List <Integer> roleNoList) {
        return staffMapper.bindRole(empNo, roleNoList);
    }

    @Override
    public List <SimpleStaffVo> getApproverList(String roleName) {
        return staffMapper.selectApprover(roleName);
    }

    @Override
    public List <SimpleStaffVo> getStaffBaseInfoAll() {
        return staffMapper.queryStaffAll();
    }



    @Override
    public List <SimpleStaffVo> getStaffBaseInfoByDeptNo(Integer deptNo) {
        return staffMapper.queryStaffByDeptNo(deptNo);
    }

    @Override
    public SimpleStaffVo getStaffBaseInfo(Integer empNo) {
        return staffMapper.queryStaffByEmpNo(empNo);
    }

    @Override
    public SimpleStaffVo getStaffBaseInfo(String empName) {
        return staffMapper.queryStaffByEmpName(empName);
    }

    @Override
    public StaffDetailInfo getStaffDetailInfo(Integer empNo) {
        return staffMapper.queryStaffDetailByEmpNo(empNo);
    }

    @Override
    public StaffDetailInfo getStaffDetailInfo(String empName) {
        return staffMapper.queryStaffDetailByEmpName(empName);
    }

    @Override
    public StaffDetailInfo getStaffDetailInfo(Integer deptNo, String empName) {
     return staffMapper.queryStaffDetailByDeptNoAndEmpName(deptNo,empName );
    }

    @Override
    public int editBaseInfoByEmpNo(StaffBaseInfoVo baseInfoVo) {
        return staffMapper.updateBaseInfoByEmpNo(baseInfoVo);
    }

    @Override
    public int insertEduRecord(EducationRecord educationRecord) {
        return staffMapper.insertEduRecord(educationRecord);
    }

    @Override
    public int updateEduRecord(EducationRecord educationRecord) {
        return  staffMapper.updateEduRecord(educationRecord);
    }

    @Override
    public int insertWorkRecord(WorkRecord workRecord) {
        return staffMapper.insertWorkRecord(workRecord);
    }

    @Override
    public int updateWorkRecord(WorkRecord workRecord) {
        return staffMapper.updateWorkRecord(workRecord);
    }

    @Override
    public int deleteWorkRecord(Integer workRecordId) {
        return staffMapper.deleteWorkRecord(workRecordId);
    }

    @Override
    public int insertTrainRecord(TrainRecord trainRecord) {
        return staffMapper.insertTrainRecord(trainRecord);
    }

    @Override
    public int updateTrainRecord(TrainRecord trainRecord) {
        return staffMapper.updateTrainRecord(trainRecord);
    }

    @Override
    public int deleteTrainRecord(Integer trainRecordId) {
        return staffMapper.deleteTrainRecord(trainRecordId);
    }

    @Override
    public int deleteEduRecordById(Integer eduRecordId) {
        return staffMapper.deleteEduRecordById(eduRecordId);
    }

    @Override
    public int insertOrUpdateEduRecord(EducationRecord educationRecord) {
        return staffMapper.insertOrUpdateEduRecord(educationRecord);
    }

    @Override
    public int editImportantInfoByEmpNo(StaffImportantInfoVo importantInfoVo) {
        return staffMapper.updateImportantInfoByEmpNo(importantInfoVo);
    }
}
