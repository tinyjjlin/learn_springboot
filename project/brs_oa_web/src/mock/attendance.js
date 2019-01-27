import Mock from 'mockjs'
export default {
    // 获取考勤统计数据
    getAttendanceStatistics:()=>{
        return Mock.mock({
            "code": 200,
            "data": {
                empNo: "@integer(1)",
                realWorkDay: "@integer(17,23)",
                commonLateCount: "@integer(0,3)",
                seriousLateCount: "@integer(0,3)",
                leaveEarlyCount: "@integer(0,2)",
                absentDay: "@integer(0,1)",
                lackOfCardCount: "@integer(0,5)",
                holidayDayCount: "@integer(0,3)",
                restDayCount: "@integer(4,8)",
                annualLeaveDay: "@integer(0,5)",
                personalLeaveDay: "@integer(0,3)",
                sickLeaveDay: "@integer(0,2)",
                marriageLeaveDay: "@integer(0,3)",
                maternityLeaveDay: "@integer(0,3)",
                funeralLeaveDay: "@integer(0,3)",
                paternityLeaveDay: "@integer(0,3)",
                casualLeaveDay: "@integer(0,3)",
            },
            "msg": "获取成功!"
        })
    },
    getPersonalAttendanceRecord:()=>{
        return Mock.mock({
            "code": 200,
            "data": {
                empNo: "@integer(1)",
                realWorkDay: "@integer(17,23)",
                commonLateCount: "@integer(0,3)",
                seriousLateCount: "@integer(0,3)",
                leaveEarlyCount: "@integer(0,2)",
                absentDay: "@integer(0,1)",
                lackOfCardCount: "@integer(0,5)",
                holidayDayCount: "@integer(0,3)",
                restDayCount: "@integer(4,8)",
                annualLeaveDay: "@integer(0,5)",
                personalLeaveDay: "@integer(0,3)",
                sickLeaveDay: "@integer(0,2)",
                marriageLeaveDay: "@integer(0,3)",
                maternityLeaveDay: "@integer(0,3)",
                funeralLeaveDay: "@integer(0,3)",
                paternityLeaveDay: "@integer(0,3)",
                casualLeaveDay: "@integer(0,3)",
            },
            "msg": "获取成功!"
        })
    },
    // 获取个人考勤记录
    getPersonalAttendanceRecord:()=>{
        return Mock.mock({
            "code":200,
           "data":{
            "2018-12-01":{
                "dayType": 1 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 0, //下班状态  正常 早退 矿工
                "onStatus": 0,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "@cname()",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-02":{
                "dayType": 1 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 0, //下班状态  正常 早退 矿工
                "onStatus": 0,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "@cname()",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-03":{
                "dayType": 0 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 2, //下班状态  正常 早退 矿工
                "onStatus": "@integer(0,2)",  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "@cname()",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-04":{
                "dayType":2 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 0, //下班状态  正常 早退 矿工
                "onStatus": 0,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "国庆",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-05":{
                "dayType": 0 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": "@integer(0,2)", //下班状态  正常 早退 矿工
                "onStatus": 2,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "@cname()",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-06":{
                "dayType": 1 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 0, //下班状态  正常 早退 矿工
                "onStatus": 0,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "@cname()",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-07":{
                "dayType": 0 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 0, //下班状态  正常 早退 矿工
                "onStatus": 0,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "@cname()",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-08":{
                "dayType": 1 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 0, //下班状态  正常 早退 矿工
                "onStatus": 0,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "@cname()",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-09":{
                "dayType": 1 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 0, //下班状态  正常 早退 矿工
                "onStatus": 0,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "春节",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-10":{
                "dayType": 0 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 0, //下班状态  正常 早退 矿工
                "onStatus": 0,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "@cname()",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },
            "2018-12-11":{
                "dayType": 0 ,  //日期类型  0；工作日 1:休息日 2：节假日
                "offStatus": 0, //下班状态  正常 早退 矿工
                "onStatus": 0,  //上班状态  正常 迟到 矿工
                "clockOutTime": "@time()", //下班打卡时间 
                "festival": "@cname()",      //节假日名称
                "empNo": "@integer(1)",          //员工号
                "clockInTime": "@time()"      //上班打卡时间
            },

            
           },
           "msg":"获取成功!"
        })
    },
    
    //  保存排班信息 
    saveSchedule:()=>{
        return Mock.mock({
            "code": 200,
            "data": true,
            "msg": '添加成功!'
        })
    },

    // 查看排班信息
    getSchedule:()=>{
        return Mock.mock({
            "code": 200,
            "data": {
                name: "冬季排班",
                workingHours: "2019-01-14T00:43:03.772Z",
                offHours: "2019-01-14T00:43:03.772Z",
                permitLateTime: 0,
                permitOffLimit: 0,
                permitOnLimit: 0,
                permitForgetClock: 0,
                permitLateOccasion: 0,
            },
            "msg": "获取成功!"
        })
    },


}