/* 考勤管理 */
/* eslint-disable indent */
/* eslint-disable no-unused-vars */

const attendanceMng = {
  path: '/attendanceMng',
  component: () => import('@/views/personnelCenter/index'),
  redirect: 'noredirect',
  alwaysShow: true,
  name: 'attendanceMng',
  meta: {
    title: 'attendanceMng',
    icon: 'myAttendance'
  },
  children: [
    {
      path: '/attendanceMng/clockRecord',
      component: () => import('@/views/personnelCenter/attendanceMng/clockRecord'),
      name: 'clockRecord',
      meta: { icon: 'myAttendance', title: 'clockRecord' }
    },
    {
      path: '/attendanceMng/attendanceRecord',
      component: () => import('@/views/personnelCenter/attendanceMng/attendanceRecord'),
      name: 'attendanceRecord',
      meta: { icon: 'attendanceRecord', title: 'attendanceRecord' }
    },
    {
      path: '/attendanceMng/attendanceStatistics',
      component: () => import('@/views/personnelCenter/attendanceMng/attendanceStatistics'),
      name: 'attendanceStatistics',
      meta: { icon: 'attendanceStatistics', title: 'attendanceStatistics' }
    },
    // {
    //   path: '/attendanceMng/importClockRecord',
    //   component: () => import('@/views/personnelCenter/attendanceMng/importClockRecord'),
    //   name: 'importClockRecord',
    //   meta: { title: 'importClockRecord' }
    // },
    {
      path: '/attendanceMng/schedule',
      component: () => import('@/views/personnelCenter/attendanceMng/schedule'),
      name: 'schedule',
      meta: { icon: 'task', title: 'schedule' }
    }
  ]
}

export default attendanceMng
