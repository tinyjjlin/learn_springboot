/* 员工信息管理*/
import Layout from '@/views/layout/Layout'
const systemMng = {
  path: '/systemMng',
  component: Layout,
  redirect: 'noredirect',
  alwaysShow: true,
  name: 'systemMng',
  meta: {
    roles: ['adminGroup'],
    title: 'systemMng',
    icon: 'setup'
  },
  children: [
    {
      path: '/privilage',
      name: 'privilage',
      component: () => import('@/views/privilage/index'),
      meta: { icon: 'privilage', title: 'privilage', noCache: true }
    },
    {
      path: '/role',
      name: 'role',
      component: () => import('@/views/role/index'),
      meta: { icon: 'role', title: 'role', noCache: true }
    },
    {
      path: '/admin',
      name: 'admin',
      component: () => import('@/views/admin/index'),
      meta: { icon: 'user', title: 'admin', noCache: true }
    },
    {
      path: '/departmentMng',
      component: () => import('@/views/system/departmentMng'),
      name: 'departmentMng',
      meta: {
        icon: 'origanzation',
        title: 'departmentMng',
        noCache: true
      }
    },
    {
      path: '/positionMng',
      component: () => import('@/views/system/positionMng'),
      name: 'positionMng',
      meta: {
        icon: 'component',
        title: 'positionMng',
        noCache: true
      }
    }
    // {
    //   path: '/workNumber',
    //   component: () => import('@/views/system/workNumber'),
    //   name: 'workNumber',
    //   meta: {
    //     icon: 'upload',
    //     title: 'workNumber',
    //     noCache: true
    //   }
    // }
  ]
}
export default systemMng
