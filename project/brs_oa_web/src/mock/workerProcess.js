import Mock from "mockjs"

export default {
    // 添加内外勤申请
    saveAttendanceApply:()=>{
        return Mock.mock({
            "code": 200,
            "data": true,
            "msg": '添加成功!'
        })
    },

    // 获取内外勤审批列表
    getWorkflowApprover:()=>{
        return Mock.mock({
            "code": 200,
            "data|3-5": [{
                empNo: '@integer(100001,100100)',
                empName: '@cname()',
                applyType: '@integer(1,10)',
                startTime: '@datetime()',
                endTime: '@datetime()',
                img: '@dataImage()',
                desc: '@cparagraph()',
                approve: '@cname()',
                copyPeople: '@cname()',
                status: '@integer(0,2)'
            }],
            "msg": '获取成功!'
        })
    },

    // 获取个人内外勤申请列表
    getWorkflowApplicant:()=>{
        return Mock.mock({
            "code": 200,
            "data|3-5": [{
                applyType: '@integer(1,10)',
                startTime: '@datetime()',
                endTime: '@datetime()',
                img: '@dataImage()',
                desc: '@cparagraph()',
                approve: '@cname()',
                copyPeople: '@cname()',
                status: '@integer(0,2)'
            }],
            "msg": '获取成功!'
        })
    },

    showWorkflow:()=>{
        return Mock.mock({
            "code":200,
            "data":{
                applicationTime: "@datetime()", // 申请时间
				approvalStatus: "@integet(0,2)", // 审核状态 
				approver: "纪俊林", // 审核人 id
				content: "@string()", 
				startTime: "2019-01-15 00:00:00",  
				endTime: "2019-01-25 00:00:00", // 
				flowType: "@integer(0,7)", // 请假类型  number
				proveImg: "@dataimage()", //  图片
				workName: "请假" ,
            },
            "msg":"获取成功!"
        })
    },
    // 获取请假类型
    getAttendanceApplyTypes:()=>{
        return Mock.mock({
            "code": 200,
            "data": [
                {
                    value: 'qingjia',
                    label: '请假',
                    // children: [
                    //     {value: 'nianjia', label: '年假'},
                    //     {value: 'shijia', label: '事假'},
                    //     {value: 'bingjia', label: '病假'},
                    //     {value: 'tiaoxiu', label: '调休'},
                    //     {value: 'chanjia', label: '产假'},
                    //     {value: 'peichanjia', label: '陪产假'},
                    //     {value: 'hunjia', label: '婚假'},
                    //     {value: 'lijia', label: '例假'},
                    //     {value: 'sangjia', label: '丧假'}
                    // ]
                },
                {
                    value: 'waichu',
                    label: '外出',
                },
                {
                    value: 'jiaban',
                    label: '加班',
                }
            ],
            "msg": "获取成功!"
        })
    },
    
    getApproves:()=>{
        return Mock.mock({
            "code": 200,
            "data": [
                {value: '1', label: '王帅'},
                {value: '2', label: '陈飞'},
                {value: '3', label: '某某某'},
            ],
            "msg": "获取成功!"
        })
    }
}