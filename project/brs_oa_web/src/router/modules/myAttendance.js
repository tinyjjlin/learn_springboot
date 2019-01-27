/* eslint-disable indent */
/* eslint-disable no-unused-vars */
const myAttendance = {
  path: '/myAttendance',
  component: () => import('@/views/personalCenter/myAttendance'),
  redirect: 'noredirect',
  name: 'myAttendance',
  meta: {
    title: 'myAttendance',
    icon: 'component'
  },
  children: [
    {
      path: '/myAttendance/clockRecord',
      component: () => import('@/views/personalCenter/myAttendance/clockRecord'),
      name: 'clockRecord',
      meta: { title: 'clockRecord' }
    },
    {
      path: '/myAttendance/attendanceRecord',
      component: () => import('@/views/personalCenter/myAttendance/attendanceRecord'),
      name: 'attendanceRecord',
      meta: { title: 'attendanceRecord' }
    },
    {
      path: '/myAttendance/attendanceStatistics',
      component: () => import('@/views/personalCenter/myAttendance/attendanceStatistics'),
      name: 'attendanceStatistics',
      meta: { title: 'attendanceStatistics' }
    }
  ]
 }

export default myAttendance
