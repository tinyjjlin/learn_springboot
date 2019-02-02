const privilageList = {
	'code': 200,
	'msg': '获取数据成功',
	'data': {
		"permissionList": [
			{
				"no": 1,
				"name": "我的信息",
				"isEnabled": 1
			},
			{
				"no": 2,
				"name": "我的工资条",
				"isEnabled": 1
			},
			{
				"no": 3,
				"name": "打卡记录",
				"isEnabled": 1
			},
			{
				"no": 4,
				"name": "考勤记录",
				"isEnabled": 1
			},
			{
				"no": 5,
				"name": "考勤统计",
				"isEnabled": 1
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

const privilageData = {

}


export default {
	getPrivilageList: config => {
		return privilageList
	},
	addPrivilage: config => {
		return privilageData
	},
	delPrivilage: config => {
		return delData
	}
}