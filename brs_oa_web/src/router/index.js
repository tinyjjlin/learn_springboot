/* eslint-disable indent */
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/* Router Modules */
import myAttendance from './modules/myAttendance'
import personnelCenter from './modules/personnelCenter'
import systemMng from './modules/systemMng'
import workProcess from './modules/workProcess'
/** note: Submenu only appear when children.length>=1
 *  detail see  https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 **/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  /* 重定向 */
  // {
  //   path: '/redirect',
  //   component: Layout,
  //   hidden: true,
  //   children: [
  //     {
  //       path: '/redirect/:path*',
  //       component: () => import('@/views/redirect/index')
  //     },
  //     {
  //       path: 'dashboard',
  //       component: () => import('@/views/dashboard/index'),
  //       name: 'Dashboard',
  //       meta: { title: 'dashboard', icon: 'dashboard', noCache: true }
  //     }
  //   ]
  // },
  /** 登录 */
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  /** 权限认证 */
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/authredirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/errorPage/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/errorPage/401'),
    hidden: true
  },
  /** 主界面 */
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: 'dashboard', icon: 'dashboard', noCache: true }
      }
    ]
  },
  /** 个人中心 */
  {
    path: '/personalCenter',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'personalCenter',
    meta: {
      icon: 'user',
      title: 'personalCenter'
    },
    children: [
      {
        path: '/personalCenter/myinfo',
        component: () => import('@/views/personalCenter/myInfo2'),
        name: 'myInfo',
        meta: { icon: 'userinfo', title: 'myInfo', noCache: true }
      },
      {
        path: '/personalCenter/infoChanges',
        component: () => import('@/views/personalCenter/infoChanges'),
        name: 'infoChanges',
        meta: {
          icon: 'apply',
          title: 'infoChanges',
          noCache: true
        }
      },
      {
        path: '/personalCenter/mySalarySheet',
        component: () => import('@/views/personalCenter/mySalarySheet'),
        name: 'mySalarySheet',
        meta: { icon: 'money1', title: 'mySalarySheet', noCache: true }
      },
      myAttendance,
      {
        path: '/personalCenter/searchstaff',
        component: () => import('@/views/personalCenter/searchstaff'),
        name: 'searchstaff',
        meta: {
          icon: 'watch',
          title: 'checkStaff',
          noCache: true
        }
      }
      // {
      //   path: '/personalCenter/birthday',
      //   component: () => import('@/views/system/birthday'),
      //   name: 'birthday',
      //   meta: {
      //     icon: 'component',
      //     title: 'birthday',
      //     noCache: true
      //   }
      // }
    ]
  }
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

/** 根据角色权限动态加载路由 */
export const asyncRouterMap = [
  personnelCenter,
  workProcess,
  systemMng,
   /* 组织管理 */
   {
    path: '/organizationMng',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    meta: {
      icon: 'tree',
      title: 'organizationalStructure'
    },
    children: [
      {
        path: '/organizationMng/index',
        component: () => import('@/views/organizationMng/index'),
        name: 'organizationMng',
        meta: {
          icon: 'origanzation',
          title: 'organizationMng',
          noCache: true
        }
      }
    ]
  }
]
