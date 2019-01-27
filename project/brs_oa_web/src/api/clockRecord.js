import request from '@/utils/request'

// 获取个人打卡
export function getPersonalClockRecord(query){
    return request({
        url: '/personal/clockRecord',
        method: 'get',
        params:query
    })
}
// 保存打卡
export function saveClockRecord(data){
    return request({
        url: '/clockRecord',
        method: 'post',
        data
    })
}
// 获取某个员工打卡信息
export function getClockRecord(query){
    console.log(query)
    return request({
        url: '/clockRecord',
        method: 'get',
        params:query
    })
}

// 获取所有部门和员工信息
export function getDeptInfo(){
    return request({
        url: '/department/more',
        methods: 'get',
    })
}