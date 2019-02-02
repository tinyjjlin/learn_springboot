import request from '@/utils/request'

//  获取权限列表
export function getPrivilageList(query) {
	return request({
		url:'/permission',
		method: 'get',
		params: query
	})
}

//  添加新的权限
export function addPrivilage(data) {
	return request({
		url: '/permission',
		method: 'post',
		data
	})
}

//  编辑权限
export function editPrivilage(data) {
	return request({
		url:'/permission',
		method: 'patch',
		data
	})
}

//  删除权限
export function delPrivilage(index) {
	return request({
		url: '/permission/'+index,
		method: 'delete'
	})
}

//  根据角色ID取出对应的权限信息
export function getRolePrivilageInfo(roleId) {
	return request({
		url:'/permission/roleNo?roleNo='+roleId,
		method: 'get'
	})
}