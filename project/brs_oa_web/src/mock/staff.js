import { param2Obj } from '@/utils'

const userInfo = {
  "code": 200,
  "msg": "查看员工个人信息操作成功！",
  "data": {
    "picture": "https://mypics.zhaopin.cn//attachment/2018/11/6/603f07d8-cc13-49ca-a612-cc83a9a7d0e1.jpg",
    "name": "纪俊林",
    "gender": 0,
    "nation": "汉",
    "politicsStatus": "群众",
    "empNo": 100057,
    "position": "java软件工程师",
    "deptNo": 101101,
    "deptName": "IT部门",
    "hireDate": "2018-10-26",
    "mobile": "15195988793",
    "email": "tinyjjlin@163.com",
    "hobby": null,
    "maritalStatus": 1,
    "highestDegree": "大专",
    "birthday": "1989-09-17",
    "currentAddress": "南京市玄武区孝陵卫街道钟鼎山庄",
    "address": "江苏省泗阳县",
    "nationIdcard": "321323XXXX",
    "socialInsuranceAccount": "1444444444444",
    "emergencyContact": "bbbbb",
    "emergencyContactPhone": "15555",
    "educationRecordList": [
      {
        "educationRecordId": 6,
        "schoolName": "南京农业大学",
        "educationStartEndTime": "2019-01-07,2019-02-12",
        "major": "大专",
        "diplomas": "硕士",
        "staffEmpNo": null,
        "isNationalUnified": 1
      }
    ],
    "workExperienceList": [
      {
        "workExperienceId": 1,
        "workStartEndTime": "2019-01-22,2019-02-18",
        "companyName": "南京",
        "oldPosition": "Java",
        "reasonToLeave": "考研",
        "certifier": "人",
        "certifierMobile": "34232ddd",
        "staffEmpNo": null
      }
    ],
    "trainRecordList": [
      {
        "trainRecordId": 4,
        "staffEmpNo": null,
        "trainStartEndTime": "2019-01-29,2019-02-05",
        "trainAgency": "dfdf",
        "trainCourse": "fdfd"
      }
    ]
  }
}


const adminList = {
	'code': 200,
	'msg': '获取数据成功',
	'data': {
		"adminList": [
			{
				"no": 1,
				"name": "张三",
			},
			{
				"no": 2,
				"name": "李四",
			},
			{
				"no": 3,
				"name": "王五"
			},
			{
				"no": 4,
				"name": "黑蛋"
			},
			{
				"no": 5,
				"name": "王八"
			},
		],
		total: 5
	}
}

const delData = {
	"code": 200,
	"msg": "删除成功",
	"data": {
		no: 1
	}
}

const adminData = {

}

export default {
  getUserInfo: (config) => {
    return userInfo;
  },
  getAdminList: config => {
		return adminList
	},
	addAdmin: config => {
		return adminData
	},
	delAdmin: config => {
		return delData
	}
}
