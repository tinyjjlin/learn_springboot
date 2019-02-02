const roleList = {
	'code': 200,
	'msg': '获取数据成功',
	'data': {
		"roleList": [
			{
				"no": 1,
				"name": "普通员工",
			},
			{
				"no": 2,
				"name": "HR",
			},
			{
				"no": 3,
				"name": "经理",
			},
			{
				"no": 4,
				"name": "总经理",
			}
		],
		total: 4
	}
}

const delData = {
	"code": 200,
	"msg": "删除成功",
	"data": {
		no: 1
	}
}

const roleData = {

}


export default {
	getRoleList: config => {
		return roleList
	},
	addRole: config => {
		return roleData
	},
	delRole: config => {
		return delData
	}
}