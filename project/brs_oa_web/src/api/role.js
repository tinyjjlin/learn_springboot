import request from '@/utils/request'

//  获取角色列表
export function getRoleList(query) {
  return request({
    url: '/role',
    method: 'get',
    params: query
  })
}

//  角色添加权限
export function addRolePrivilage(data) {
  return request({
    url: '/role/bindPermission',
    method: 'post',
    data
  })
}

//  添加角色
export function addRole(data) {
  return request({
    url: '/role',
    method: 'post',
    data
  })
}

//  编辑角色
export function editRole(data) {
  return request({
    url: '/role',
    method: 'patch',
    data
  })
}

// 删除角色
export function delRole(roleId) {
  return request({
    url: '/role/' + roleId,
    method: 'delete'
  })
}

//  根据员工id 获取 角色列表
export function getAdminIdRoleList(adminId) {
  return request({
    url: '/role/empNo?empNo=' + adminId,
    method: 'get'
  })
}
