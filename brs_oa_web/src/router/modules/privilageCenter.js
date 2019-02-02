// 权限管理
import Layout from '@/views/layout/Layout'

const privilageCenter = {
  path: '/privilageCenter',
  component: Layout,
  redirect: 'noredirect',
  alwaysShow: true,
  name: 'privilageCenter',
  meta: {
    roles: ['adminGroup'],
    icon: 'component',
    title: 'privilageCenter'
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
      meta: { icon: 'component', title: 'role', noCache: true }
    },
    {
      path: '/admin',
      name: 'admin',
      component: () => import('@/views/admin/index'),
      meta: { icon: 'user', title: 'admin', noCache: true }
    }
  ]
}

export default privilageCenter
