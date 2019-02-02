/* eslint-disable space-before-blocks */
import request from '@/utils/request'
// 分页获取员工列表
export function fetchList(query) {
  return request({
    url: '/staffs',
    method: 'get',
    params: query
  })
}
// 分页获取员工初始化信息列表
export function getStaffInitialInfoList(query){
  return request({
    url: '/staff/initialInfo',
    method: 'get',
    params: query
  })
}
// 创建新的员工
export function createNewStaff(data) {
  return request({
    url: '/staff/initialInfo',
    method: 'post',
    data
  })
}

//  修改 员工信息
export function editNewStaff(data) {
  return request({
    url: '/staff/initialInfo',
    method: 'patch',
    data
  })
}

//  更新员工关键信息
export function addGeneralInfo(data) {
  return request({
    url: '/staff/generalInfo',
    method: 'patch',
    data
  })
}

//  获取员工重要信息条目
export function getStaffInfoItem() {
  return request({
    url: '/staffInfoItem',
    method: 'get'
  })
}

//  员工申请
export function apply(data) {
  return request({
    url: '/staffWorkflow/apply',
    method: 'post',
    data
  })
}

//  根据员工id 添加角色
export function addRoleAdminId(data){
  return request({
    url: '/staffs/bindRole',
    method: 'post',
    data
  })
}

// 获取审批人
export function getPersonnelApprover(){
  return request({
    url: '/personnelApprover',
    method: 'get'
  })
}

//  删除员工
// export function delAdmin(adminId){
//   return request({
//     url: '/staffs?empNo=' + adminId,
//     method: 'delete'
//   })
// }

//  添加员工
// export function addAdmin(data){
//   return request({
//     url: '/staffs',
//     method: 'post',
//     data
//   })
// }

// 获取部门
export function getDepartmentList() {
  return request({
    url: '/department',
    method: 'get'
  })
}

//  根据部门获取员工
export function getDepartmentMan(query) {
  return request({
    url: '/staff/limit',
    method: 'get',
    params: query
  })
}

// 获取登录用户的个人详细信息
export function getPersonalDetailInfo() {
  return request({
    url: '/staffs/personal/info',
    method: 'get'
  })
}

// 获取指定员工号的个人详细信息
export function getStaffIdinfo(staffId){
  return request({
    url: '/staffs/' + staffId + '/info',
    method: 'get'
  })
}

//  添加头像
export function addAvatar(data){
  return request({
    url: '/uploadImage',
    method: 'post',
    data
  })
}

// 更新用户基本信息
export function updatePersonalBaseInfo(data) {
  return request({
    url: 'staffs/personal/baseInfo',
    method: 'patch',
    data
  })
}
// 更新用户重要信息

export function updatePersonalImportantInfo(data) {
  return request({
    url: 'staffs/personal/importantInfo',
    method: 'patch',
    data
  })
}

// 新增教育经历
// eslint-disable-next-line space-before-blocks
export function addEduRecord(data){
  return request({
    url: 'staffs/personal/eduInfo',
    method: 'post',
    data
  })
}

// 更新教育经历
// eslint-disable-next-line space-before-blocks
export function updateEduRecord(data){
  return request({
    url: 'staffs/personal/eduInfo',
    method: 'patch',
    data
  })
}
// 删除教育经历
// eslint-disable-next-line space-before-blocks
export function deleteEduRecord(data){
  return request({
    url: 'staffs/personal/eduInfo/' + data,
    method: 'delete'
  })
}

const workRecordUrl = 'staffs/personal/workRecord'
export function addWorkRecord(data){
  return request({
    url: workRecordUrl,
    method: 'post',
    data
  })
}
export function updateWorkRecord(data){
  return request({
    url: workRecordUrl,
    method: 'patch',
    data
  })
}
export function deleteWorkRecord(data){
  return request({
    url: workRecordUrl + '/' + data,
    method: 'delete'
  })
}

const trainRecordUrl = 'staffs/personal/trainRecord'
export function addTrainRecord(data){
  return request({
    url: trainRecordUrl,
    method: 'post',
    data
  })
}
export function updateTrainRecord(data){
  return request({
    url: trainRecordUrl,
    method: 'patch',
    data
  })
}
export function deleteTrainRecord(data) {
  return request({
    url: trainRecordUrl + '/' + data,
    method: 'delete'
  })
}

// 插入或更新教育经历（记录）
export function insertOrUpdateEduRecord(data) {
  return request({
    url: 'staffs/personal/eduInfo',
    method: 'patch',
    data
  })
}

//  查看申请人工作流
export function applyManWorkflow(query) {
  return request({
    url: '/staffWorkflow/applicant',
    method: 'get',
    params: query
  })
}
