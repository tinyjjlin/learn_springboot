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

// 修改审核状态
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