import request from '@/utils/request'


// 获取个人考勤统计数据
export function getPersonalAttendanceStatistics(query){
    return request({
        url: '/personal/attendanceStatistics',
        method: 'get',
        params:query
    })
}
// 获取指定员工号考勤统计数据
export function getAttendanceStatistics(query){
    return request({
        url: '/attendanceStatistics',
        method: 'get',
        params:query
    })
}




// 获取个人考勤记录
export function getPersonalAttendanceRecord(query){
    return request({
        url: '/personal/attendanceRecord',
        method: 'get',
        params:query
    })
}

// 获取指定员工号考勤统计数据
export function getAttendanceRecord(query){
    return request({
        url: '/attendanceRecord',
        method: 'get',
        params:query
    })
}

// 保存排班信息
export function saveSchedule(data){
    return request({
        url: '/schedule',
        method: 'post',
        data
    })
}

// 查看排班信息
export function getSchedule(){
    return request({
        url: 'schedule',
        method: 'get'
    })
}
