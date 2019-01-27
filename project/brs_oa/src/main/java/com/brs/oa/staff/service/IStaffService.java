package com.brs.oa.staff.service;
import com.brs.oa.staff.entity.EducationRecord;
import com.brs.oa.staff.entity.TrainRecord;
import com.brs.oa.staff.entity.WorkRecord;
import com.brs.oa.staff.vo.*;
import com.brs.oa.staffinfoworkflow.entity.StaffInfoWorkflow;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-04
 */
public interface IStaffService {


    /**
     * 通过审核的更改用户信息更新
     * @param workflowList
     * @return
     */
    int updateStaffItems(StaffInfoWorkflow workflowList);
    /**
     * 获取用户最近一次登录的ip地址
     * @param empNo
     * @return
     */
    String selectLoginAddr(Integer empNo);
    /**
     * 添加或更新登录用户信息（员工号，登录ip地址）
     * @param empNo
     * @param loginAddr
     * @return
     */
    int insertLoginInfo(Integer empNo, String loginAddr);
    /**
     * 更新员工系统登录状态
     * @param empNo
     * @param loginStatus
     * @return
     */
    int updateLoginStatus(Integer empNo,Integer loginStatus);
    /**
     * 获取用户登陆状态 1代表已经登陆，0代表未登录
     * @param empNo
     * @return
     */
    int getLoginStatus(Integer empNo);
    /**
     * 获取审核人信息
     * @param roleName
     * @return
     */
    List<SimpleStaffVo> getApproverList(String roleName);

    /**
     * 更新员工头像
     * @param empNo
     * @param imageUrl
     * @return
     */
    int updateStaffHeaderImg(Integer empNo,String imageUrl);

    /**
     * 为员工绑定角色
     * @param empNo
     * @param roleNoList
     * @return
     */
    int bindRole(Integer empNo, List<Integer> roleNoList);
    /**
     * 查看公司所有员工
     * @return
     */
     List<SimpleStaffVo> getStaffBaseInfoAll();

    /**
     * 查看指定部门员工
     * @param deptNo
     * @return
     */
     List<SimpleStaffVo> getStaffBaseInfoByDeptNo(Integer deptNo);

    /**
     * 根据员工号获取员工基本信息
     * @return
     */
    SimpleStaffVo getStaffBaseInfo(Integer empNo);

    /**
     * 根据员工姓名获取员工基本信息
     * @return
     */
    SimpleStaffVo getStaffBaseInfo(String empName);

    /**
     * 根据员工工号获取员工详细信息
     * @param empNo
     * @return
     */
    StaffDetailInfo getStaffDetailInfo(Integer empNo);

    /**
     * 根据员工姓名获取员工详细信息
     * @param empName
     * @return
     */
    StaffDetailInfo getStaffDetailInfo(String empName);

    /**
     * 根据部门号+员工姓名 获取员工详细信息
     * @param deptNo
     * @param empName
     * @return
     */
    StaffDetailInfo getStaffDetailInfo(Integer deptNo,String empName);

    /**
     * 编辑（更新）员工基本信息
     * @return
     */
    int editBaseInfoByEmpNo( StaffBaseInfoVo baseInfoVo);

    /**
     * 编辑（更新）员工重要信息
     * @param importantInfoVo
     * @return
     */
    int editImportantInfoByEmpNo(StaffImportantInfoVo importantInfoVo);

    /**
     * 插入或更新教育经历（记录）
     * @param educationRecord
     * @return
     */
    int insertOrUpdateEduRecord( EducationRecord educationRecord);
    /**
     * 插入教育经历（记录）
     * @return
     */
    int insertEduRecord(EducationRecord educationRecord);

    /**
     * 更新教育经历（记录）
     * @return
     */
    int updateEduRecord( EducationRecord educationRecord);

    /**
     * 根据id删除指定教育经历
     * @param eduRecordId
     * @return
     */
    int deleteEduRecordById(Integer eduRecordId);


    /**
     * 插入工作记录
     * @param workRecord
     * @return
     */
    int insertWorkRecord( WorkRecord workRecord);

    /**
     * 更新工作记录
     * @param workRecord
     * @return
     */
    int updateWorkRecord( WorkRecord workRecord);

    /**
     * 删除工作记录
     * @param workRecordId
     * @return
     */
    int deleteWorkRecord(Integer workRecordId);

    /**
     * 插入培训记录
     * @param trainRecord
     * @return
     */
    int insertTrainRecord( TrainRecord trainRecord);

    /**
     * 更新培训记录
     * @param trainRecord
     * @return
     */
    int updateTrainRecord(TrainRecord trainRecord);

    /**
     * 删除培训记录
     * @param trainRecordId
     * @return
     */
    int deleteTrainRecord(Integer trainRecordId);

    /**
     * 创建新的员工，管理员初始化信息
     */
    int createNewStaff( StaffInitialInfoVo staffInitialInfoVo);

    /**
     * 更新员工关键信息
     * @param staffGeneralInfoVo
     * @return
     */
    int updateStaffGeneralInfo(StaffGeneralInfoVo staffGeneralInfoVo);


    /**
     * 查看管理员创建的员工初始信息
     * @param empNo
     * @return
     */
    List<StaffInitialInfoVo> selectStaffInitialInfoList(Integer empNo);
    /**
     * 查看员工关键信息
     * @param empNo
     * @return
     */
    List<StaffGeneralInfoVo> selectStaffGeneralInfoList(Integer empNo);

    int updateNewStaffInfo(StaffInitialInfoVo staffInitialInfoVo);

}
