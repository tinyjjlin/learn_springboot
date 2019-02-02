import Mock from 'mockjs'
import loginAPI from './login'
import attendanceAPI from './attendance'
import clockRecordAPI from './clockRecord'
import staffAPI from './staff'
import privilageAPI from './privilage'
import roleAPI from './role'

// 修复在使用 MockJS 情况下，设置 withCredentials = true，且未被拦截的跨域请求丢失 Cookies 的问题
// https://github.com/nuysoft/Mock/issues/300
Mock.XHR.prototype.proxy_send = Mock.XHR.prototype.send
Mock.XHR.prototype.send = function() {
  if (this.custom.xhr) {
    this.custom.xhr.withCredentials = this.withCredentials || false
  }
  this.proxy_send(...arguments)
}

// Mock.setup({
//   timeout: '350-600'
// })

// 登录相关
Mock.mock(/\/login/, 'post', loginAPI.loginByUsername)
Mock.mock(/\/logout/, 'post', loginAPI.logout)
Mock.mock(/\/login\/userInfo\.*/, 'get', loginAPI.getUserInfo)


// 签到相关

// 保存打卡
Mock.mock(/\/clockRecord/,'post',clockRecordAPI.saveClockRecord);
// 获取个人打卡
Mock.mock(/\/personal\/clockRecord/,'get',clockRecordAPI.getPersonalClockRecord)
// 获取某个员工打卡信息
Mock.mock(/\/clockRecord/,'get',clockRecordAPI.getClockRecord)
// 获取个人考勤统计
Mock.mock(/\/personal\/attendanceStatistics/,'get',attendanceAPI.getPersonalAttendanceStatistics)
// 获取指定员工号考勤统计
Mock.mock(/\/attendanceStatistics/,'get',attendanceAPI.getAttendanceStatistics)
// 获取个人考勤记录
Mock.mock(/\/personal\/attendanceRecord/,'get',attendanceAPI.getPersonalAttendanceRecord)

// 获取请假类型
Mock.mock(/\leaveType/, 'get',attendanceAPI.getAttendanceApplyTypes)
// 添加内外勤申请
Mock.mock(/\attendanceApply/,'post',attendanceAPI.saveAttendanceApply)
// 获取内外勤审批列表
Mock.mock(/\workflow\/approver/, 'get',attendanceAPI.getWorkflowApprover)
// 获取个人内外勤申请列表
Mock.mock(/\workflow\/applicant/, 'get',attendanceAPI.getWorkflowApplicant)
// 获取审批人
Mock.mock(/\approver$/, 'get',attendanceAPI.getApproves)



// 人事

// 获取所有部门和员工信息 
Mock.mock(/\/department\/more/,'get',clockRecordAPI.getDeptInfo)
// 保存排版信息
Mock.mock(/\/schedule/,'post',attendanceAPI.saveSchedule)

// 个人中心
// 我的信息
Mock.mock(/\/staffs\/personal\/info/, 'get', staffAPI.getUserInfo)
// 用户
Mock.mock(/\/user/, 'get', staffAPI.getAdminList)
Mock.mock(/\/user/, 'post', staffAPI.addAdmin)
Mock.mock(/\/user/, 'delete', staffAPI.delAdmin)
//  权限
Mock.mock(/\/permission/, 'get', privilageAPI.getPrivilageList)
Mock.mock(/\/permission/, 'post', privilageAPI.addPrivilage)
Mock.mock(/\/permission/, 'delete', privilageAPI.delPrivilage)

// 角色
Mock.mock(/\/role/, 'get', roleAPI.getRoleList)
Mock.mock(/\/role/, 'post', roleAPI.addRole)
Mock.mock(/\/role/, 'delete', roleAPI.delRole)


export default Mock
