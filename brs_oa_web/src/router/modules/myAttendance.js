/* eslint-disable indent */
/* eslint-disable no-unused-vars */
const myAttendance = {
  path: '/myAttendance',
  component: () => import('@/views/personalCenter/myAttendance'),
  redirect: 'noredirect',
  name: 'myAttendance',
  meta: {
    title: 'myAttendance',
    icon: 'myAttendance'
  },
  children: [
    {
      path: '/myAttendance/clockRecord',
      component: () => import('@/views/personalCenter/myAttendance/clockRecord'),
      name: 'clockRecord',
      meta: { icon: 'myAttendance', title: 'clockRecord' }
    },
    {
      path: '/myAttendance/attendanceRecord',
      component: () => import('@/views/personalCenter/myAttendance/attendanceRecord'),
      name: 'attendanceRecord',
      meta: { icon: 'attendanceRecord', title: 'attendanceRecord' }
    },
    {
      path: '/myAttendance/attendanceStatistics',
      component: () => import('@/views/personalCenter/myAttendance/attendanceStatistics'),
      name: 'attendanceStatistics',
      meta: { icon: 'attendanceStatistics', title: 'attendanceStatistics' }
    }
  ]
 }

export default myAttendance
