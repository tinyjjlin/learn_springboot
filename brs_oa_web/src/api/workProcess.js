import request from '@/utils/request'
// 添加内外勤申请
export function saveAttendanceApply(data){
    return request({
        url: '/workflow/apply',
        method: 'post',
        data
    })
}
// 获取内外勤审批列表 
export function getWorkflowApprover(query){
    return request({
        url: '/workflow/approver',
        method: 'get',
        params:query
    })
}

// 获取个人内外勤申请列表
export function getWorkflowApplicant(query){
    return request({
        url: '/workflow/applicant',
        method: 'get',
        params:query
    })
}

// 修改请假审核状态
export function updateWorkflowApproval(query){
    return request({
        url:"/workflow/approval",
        method:"PATCH",
        params:query
    })
}

// 获取某条内外勤申请记录
export function showWorkflow(serialNumber){
    return request({
        url: "/workflow/"+serialNumber+"/info",
        method: "get",
    })
}


// 获取请假类型
export function getAttendanceApplyTypes(){
    return request({
        url: '/leaveType',
        method: 'get'
    })
}

// 获取审批人
export function getApproves(){
    return request({
        url: '/approver',
        method: 'get',
    })
}

// 获取个人补卡申请列表
export function getCardWorkflowApplicant(query){
    return request({
        url: '/cardWorkflow/applicant',
        method: 'get',
        params: query
    })
}
// 添加补卡申请
export function saveCardWorkflowApply(data){
    return request({
        url: '/cardWorkflow/apply',
        method: 'post',
        data
    })
}
// 更新补卡申请状态
export function updateCardWorkflowApproval(query){
    return request({
        url:"/cardWorkflow/approval",
        method:"PATCH",
        params:query
    })
}
// 获取补卡审批列表
export function getCardWorkflowApprover(query){
    return request({
        url: '/cardWorkflow/approver',
        method: 'get',
        params:query
    })

}


export function addWorkflowImage(query){
    return request({
        url: '/workflow/proveImg',
        method: 'patch',
        params:query
    })
}

export function getUploadUrl(){
    return process.env.BASE_API+"/uploadWorkflowImg";
}