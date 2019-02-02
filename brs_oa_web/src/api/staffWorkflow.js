import request from '@/utils/request'

// 获取内外勤审批列表
export function getStaffWorkflowApprover(query) {
  return request({
    url: '/staffWorkflow/approver',
    method: 'get',
    params: query
  })
}

// 修改审核状态
export function updateStaffWorkflowApproval(query) {
  return request({
    url: '/staffWorkflow/approval',
    method: 'PATCH',
    params: query
  })
}

// 获取员工重要信息选项
export function getStaffInfoFlowTypes() {
  return request({
    url: '/staffInfoItem',
    method: 'get'
  })
}
