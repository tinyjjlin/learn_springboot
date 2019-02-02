/* 工资管理 */
/* eslint-disable indent */
/* eslint-disable no-unused-vars */

const salaryMng = {
  path: '/salaryMng',
  component: () => import('@/views/personnelCenter/index'),
  redirect: 'noredirect',
  alwaysShow: true,
  name: 'salaryMng',
  meta: {
    title: 'salaryMng',
    icon: 'origanzation'
  },
  children: [
    {
      path: '/salary/report',
      component: () => import('@/views/personnelCenter/salaryMng/salaryReport'),
      name: 'salaryReport',
      meta: { title: 'salaryReport' }
    }
  ]
}

export default salaryMng
