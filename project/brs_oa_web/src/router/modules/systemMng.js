/* 员工信息管理*/
import Layout from '@/views/layout/Layout'
const systemMng = {
  path: '/systemMng',
  component: Layout,
  redirect: 'noredirect',
  alwaysShow: true,
  name: 'systemMng',
  meta: {
    title: 'systemMng',
    icon: 'component'
  },
  children: [
    {
      path: '/privilage',
      name: 'privilage',
      component: () => import('@/views/privilage/index'),
      meta: { icon: 'component', title: 'privilage', noCache: true }
    },
    {
      path: '/role',
      name: 'role',
      component: () => import('@/views/role/index'),
      meta: { icon: 'component', title: 'role', noCache: true }
    },
    {
      path: '/admin',
      name: 'admin',
      component: () => import('@/views/admin/index'),
      meta: { icon: 'component', title: 'admin', noCache: true }
    },
    {
      path: '/departmentMng',
      component: () => import('@/views/system/departmentMng'),
      name: 'departmentMng',
      meta: {
        icon: 'component',
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
  ]
}
export default systemMng
