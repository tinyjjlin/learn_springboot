/* 人事中心 */
/* eslint-disable indent */
/* eslint-disable eol-last */
import Layout from '@/views/layout/Layout'
import attendanceMng from './attendanceMng'
import staffInfoMng from './staffInfoMng'
// import salaryMng from './salaryMng'
const personnelCenter = {
  path: '/personnelCenter',
  component: Layout,
  redirect: 'noredirect',
  alwaysShow: true,
  name: 'personnelCenter',
  meta: {
    roles: ['personnelGroup'],
    icon: 'peoples',
    title: 'personnelCenter'
  },
  children: [
    staffInfoMng,
    attendanceMng
    // salaryMng,
  ]
}
export default personnelCenter
