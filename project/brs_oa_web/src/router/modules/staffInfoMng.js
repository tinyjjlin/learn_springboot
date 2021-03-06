/* 员工信息管理*/
const staffInfoMng = {
  path: '/staffInfoMng',
  component: () => import('@/views/personnelCenter/index'),
  redirect: 'noredirect',
  alwaysShow: true,
  name: 'staffInfoMng',
  meta: {
    title: 'staffInfoMng',
    icon: 'component'
  },
  children: [
    {
      path: '/empBasic',
      component: () => import('@/views/personnelCenter/staffInfoMng/empBasic'),
      name: 'empBasic',
      meta: {
        icon: 'component',
        title: 'empBasic',
        noCache: true
      }
    },
    {
      path: '/employeeDetails',
      component: () => import('@/views/personnelCenter/employeeDetails'),
      name: 'employeeDetails',
      meta: {
        icon: 'component',
        title: 'checkStaff',
        noCache: true
      }
    },
    {
      path: '/staffInfoApprover',
      component: () => import('@/views/personnelCenter/staffInfoApprover'),
      name: 'staffInfoApprover',
      meta: {
        icon: 'component',
        title: 'staffInfoApprover',
        noCache: true
      }
    }
  ]
}
export default staffInfoMng
