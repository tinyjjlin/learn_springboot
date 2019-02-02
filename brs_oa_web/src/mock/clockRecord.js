import Mock from "mockjs"

export default {
    getPersonalClockRecord:()=>{
        return Mock.mock({
            "code":200,
            "data|3-10":[{
                clockDay:"@datetime()",
                clockInTime:"@datetime()",
                clockOutTime:"@datetime()",
                clockTimes:"@datetime() , @datetime() , @datetime() , @datetime() , @datetime() , @datetime()"
            }],
            "msg":"获取成功!"
        });
    },
    getClockRecord:()=>{
        return Mock.mock({
            "code":200,
            "data|3-10":[{
                clockDay:"@datetime()",
                clockInTime:"@datetime()",
                clockOutTime:"@datetime()",
                clockTimes:"@datetime() , @datetime() , @datetime() , @datetime() , @datetime() , @datetime()"
            }],
            "msg":"获取成功!"
        });
    },
    saveClockRecord:()=>{
        return Mock.mock({
            "code":200,
            "data":"@datetime()",
            "msg":"签到/签退成功!"
        })
    },
    getAttendanceStatistics:()=>{
        return Mock.mock({
            "code":200,
            "data":[{
                late:"@integer(0,10)",
                leave:"@integer(0,10)",
                absenteeism:"@integer(0,5)",
                nosignout:"@integer(0,10)",
                normal:"@integer(5,20)"
            }],
            "msg":"获取成功!"
        })
    },
    getDeptInfo:()=>{
        return Mock.mock({
            "code":200,
            "data":[
                {
                    deptNo:"@integer(1)",
                    deptName:"人事部",
                    staffList:[
                        {empNo:"@integer(1)",name:"@cname()"}
                    ]
                },
                {
                    deptNo:"@integer(1)",
                    deptName:"财务部",
                    staffList:[
                        {empNo:"@integer(1)",name:"@cname()"}
                    ]
                },
                {
                    deptNo:"@integer(1)",
                    deptName:"编辑部",
                    staffList:[
                        {empNo:"@integer(1)",name:"@cname()"}
                    ]
                },
                {
                    deptNo:"@integer(1)",
                    deptName:"IT部",
                    staffList:[
                        {empNo:"@integer(1)",name:"@cname()"}
                    ]
                },
            ],
            "msg":"获取成功!"
        })
    }
}