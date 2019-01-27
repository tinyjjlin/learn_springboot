/* 职位 api */
import request from '@/utils/request'
// 分页获取职位列表
export function getPositionListByPaging(query) {
  return request({
    url: '/position/pagination',
    method: 'get',
    params: query
  })
}
// 获取职位列表
export function getPositionList(query) {
  return request({
    url: '/position',
    method: 'get',
    params: query
  })
}
// 添加新的职位
export function addPosition(data) {
  return request({
    url: '/position',
    method: 'post',
    data
  })
}
// 删除职位
export function deletePosition(data) {
  return request({
    url: '/position',
    method: 'delete'
  })
}

