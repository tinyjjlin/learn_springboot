// 工作流程
import Layout from '@/views/layout/Layout'
const workProcess = {
  path: '/workProcess',
  component: Layout,
  redirect: 'noredirect',
  alwaysShow: true,
  name: 'workProcess',
  meta: {
    title: 'workProcess',
    icon: 'component'
  },
  children: [
    {
      path: '/workProcess/workflowApplicant',
      component: () => import('@/views/workProcess/workflowApplicant'),
      name: 'workflowApplicant',
      meta: { title: 'workflowApplicant' }
    },
    {
      path: '/workProcess/workflowApprover',
      component: () => import('@/views/workProcess/workflowApprover'),
      name: 'workflowApprover',
      meta: { roles: ['adminGroup'], title: 'workflowApprover' }
    },
    {
      path: '/workProcess/patchCard/patchCardApplicant',
      component: () => import('@/views/workProcess/patchCard/patchCardApplicant'),
      name: 'patchCardApplicant',
      meta: { title: 'patchCardApplicant' }
    },
    {
      path: '/workProcess/patchCard/patchCardApprover',
      component: () => import('@/views/workProcess/patchCard/patchCardApprover'),
      name: 'patchCardApprover',
      meta: { title: 'patchCardApprover' }
    }
  ]
}

export default workProcess
