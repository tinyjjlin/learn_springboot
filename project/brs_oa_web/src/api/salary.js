import request from '@/utils/request'

export function salaryReport(query) {
  return request({
    url: '/salaries/search',
    method: 'get',
    params: query
  })
}

// 获取个人工资条
export function fetchPersonalSalaryMonthlyReport(query) {
  return request({
    url: '/personal/salaries',
    method: 'get',
    params: query
  })
}
