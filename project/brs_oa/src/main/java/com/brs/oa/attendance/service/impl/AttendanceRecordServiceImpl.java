package com.brs.oa.attendance.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.brs.oa.attendance.service.IAttendanceRecordService;
import com.brs.oa.attendance.vo.AttendanceRecordVo;

import com.brs.oa.attendance.vo.AttendanceStatisticsVo;
import com.brs.oa.clockrecord.service.IClockRecordService;
import com.brs.oa.clockrecord.vo.ClockRecordVo;
import com.brs.oa.holidayschedule.entity.HolidaySchedule;
import com.brs.oa.holidayschedule.service.IHolidayScheduleService;
import com.brs.oa.schedule.entity.Schedule;
import com.brs.oa.schedule.service.IScheduleService;
import com.brs.oa.staff.service.IStaffService;
import com.brs.oa.staff.vo.SimpleStaffVo;

import com.brs.oa.util.CalendarSupport;
import com.brs.oa.workflow.entity.Workflow;
import com.brs.oa.workflow.service.IWorkflowService;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;


/**
 * <p>
 * 考勤（打卡）记录 服务实现类
 * </p>
 *
 * @author tiny lin
 * @since 2018-12-18
 */
@Service
public class AttendanceRecordServiceImpl  implements IAttendanceRecordService {


    @Autowired
    private IClockRecordService clockRecordService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IHolidayScheduleService holidayScheduleService;
    @Autowired
    private IWorkflowService workflowService;

    @Autowired
    private IStaffService staffService;


    @Override
    public List <AttendanceRecordVo> getRecordByDay(Integer empNo, String selectedDay) {
        return null;
    }

    @Override
    public List <AttendanceRecordVo> getRecordListRange(Integer empNo, String startTime, String endTime) {
        return null;
    }

    @Override
    public AttendanceStatisticsVo getStatisticsRecordByMonth(Integer empNo, String selectedMonth){
        AttendanceStatisticsVo  attendanceStatisticsVo = new AttendanceStatisticsVo();
        attendanceStatisticsVo.setEmpNo(empNo);

        //出勤天数
        Double workDayCount=0.0;
        //节假日天数
        int holidayDayCount =0;
        //休息日天数
        int restDayCount =0;
        // 迟到A,迟到B
        int lateANumber= 0;
        int lateBNumber =0;
        //早退
        int leaveEarlyNumber =0;
        //缺卡
        int lackOfCardNumber=0;

        HashMap<String, Object> attendaceRecordMap = getRecordMapByMonth(empNo,selectedMonth );
        if(attendaceRecordMap== null){
            return attendanceStatisticsVo;
        }
        for(Map.Entry<String, Object> entry: attendaceRecordMap.entrySet()) {
            String dayStr = entry.getKey();
            AttendanceRecordVo record = (AttendanceRecordVo) entry.getValue();
             System.out.println("开始统计================================》"+dayStr+"record---------->"+record);

            //工作日
            if(record.getDayType() == 0){
                //有上班打卡记录
                if(record.getOnStatusCode()==0 || record.getOnStatusCode()==1 || record.getOnStatusCode() ==2 ){
                   workDayCount = workDayCount +3;
                }
                //有下班打卡记录
                if(record.getOffStatusCode()==0 || record.getOnStatusCode()==1){
                    workDayCount = workDayCount+4.5;
                }
                //迟到类型A
                if(record.getOnStatusCode() ==1){
                    lateANumber = lateANumber +1;
                }
                //迟到类型B
                if(record.getOnStatusCode()==2){
                    lateBNumber = lateBNumber+1;
                }
                //早退
                if(record.getOffStatusCode()==1){
                    leaveEarlyNumber = leaveEarlyNumber +1;
                }
                //下班缺卡
                if(record.getOffStatusCode()==3){
                    lackOfCardNumber = lackOfCardNumber +1;
                }
                //上班缺卡
                if(record.getOnStatusCode()==3){
                    lackOfCardNumber = lackOfCardNumber +1;
                }
            }
            //休息日
            if(record.getDayType()==1){
               restDayCount = restDayCount +1;
            }
            //节假日
            if(record.getDayType() == 2){
                holidayDayCount = holidayDayCount+1;
            }

        }

        attendanceStatisticsVo.setRealWorkDay(workDayCount);
        attendanceStatisticsVo.setCommonLateCount(lateANumber);
        attendanceStatisticsVo.setSeriousLateCount(lateBNumber);
        attendanceStatisticsVo.setLeaveEarlyCount(leaveEarlyNumber);
        attendanceStatisticsVo.setLackOfCardCount(lackOfCardNumber);
        attendanceStatisticsVo.setHolidayDayCount(holidayDayCount);
        attendanceStatisticsVo.setRestDayCount(restDayCount);

        //一个月请假统计
        String selectMonthWrapper = selectedMonth+"-01";
        List<Workflow> workflowList = workflowService.getListByApplicantAndMonth(empNo,selectMonthWrapper );

        //1
        double annualLeaveDay = 0;
        //2
        double personalLeaveDay = 0;
        //3
        double sickLeaveDay = 0;
        //7
        double marriageLeaveDay  = 0;
        //5
        double maternityLeaveDay  = 0;
        //9
        double funeralLeaveDay  = 0;
        //6
        double paternityLeaveDay  = 0;
        //8
        double casualLeaveDay  = 0;
        //4
        double daysOffDay  = 0;
        if(workflowList != null && workflowList.size()>0){
            for(int i=0;i<workflowList.size();i++){
                Workflow workflow = workflowList.get(i);
                switch ( workflow.getFlowType()){
                    case 1:
                       annualLeaveDay = annualLeaveDay + workflow.getDuration();
                        break;
                    case 2:
                        personalLeaveDay = paternityLeaveDay +workflow.getDuration();
                        break;
                    case 3:
                        sickLeaveDay = sickLeaveDay + workflow.getDuration();
                        break;
                    case 4:
                        daysOffDay = daysOffDay + workflow.getDuration();
                        break;
                    case 5:
                        maternityLeaveDay = maternityLeaveDay + workflow.getDuration();
                        break;
                        case 6:
                            paternityLeaveDay = paternityLeaveDay + workflow.getDuration();
                        break;
                        case 7:
                            marriageLeaveDay = maternityLeaveDay + workflow.getDuration();
                        break;
                    case 9:
                        funeralLeaveDay = funeralLeaveDay +workflow.getDuration();
                        break;
                    default:
                         casualLeaveDay = casualLeaveDay +workflow.getDuration();
                        break;
                }
            }
        }

        attendanceStatisticsVo.setFuneralLeaveDay(funeralLeaveDay);
        attendanceStatisticsVo.setMarriageLeaveDay(marriageLeaveDay);
        attendanceStatisticsVo.setMaternityLeaveDay(maternityLeaveDay);
        attendanceStatisticsVo.setPaternityLeaveDay(paternityLeaveDay);
        attendanceStatisticsVo.setPersonalLeaveDay(personalLeaveDay);
        attendanceStatisticsVo.setSickLeaveDay(sickLeaveDay);
        attendanceStatisticsVo.setAnnualLeaveDay(annualLeaveDay);
        attendanceStatisticsVo.setCasualLeaveDay(casualLeaveDay);
        attendanceStatisticsVo.setDaysOffDay(daysOffDay);


            return attendanceStatisticsVo;
    }

    @Override
    public HashMap <String, Object> getRecordMapByMonth(Integer empNo, String selectedMonth) {
        int[] dateArr = CalendarSupport.getYearAndMonth(selectedMonth);
        int year = dateArr[0];
        int month = dateArr[1];
        //step1： 选择的月份是否为过去月份
        Boolean isAfter = CalendarSupport.isAfterNow(year,month);
        if(isAfter){
            return null;
        }
        HashMap<String,Object> attendanceInitData = getAttendanceInitDataByMonth(empNo,selectedMonth);

        //工作日，考勤数据才有效，添加考勤数据
        System.out.println("### read clock record:.............param..........."+empNo+",selectMonth"+selectedMonth);
        String selectMonthWrapper = selectedMonth+"-01";
        List <ClockRecordVo> clockRecordVoList = clockRecordService.selectClockRecordListByMonth(empNo, selectMonthWrapper);
      //  System.out.println("### get clock record===========>"+JSONObject.toJSON(clockRecordVoList));

        // 获取员工姓名
        SimpleStaffVo staffBaseInfoVo = staffService.getStaffBaseInfo(empNo);
        //遍历
        for(Map.Entry<String, Object> entry: attendanceInitData.entrySet()) {
            String dayStr = entry.getKey();
            AttendanceRecordVo record = (AttendanceRecordVo)entry.getValue();
            //添加员工姓名
            record.setEmpName(staffBaseInfoVo.getName());
            //工作日
            if(record.getDayType() == 0){
               boolean isClockRecord = false;
               //判断是否有这一是否有打卡记录
               if(clockRecordVoList!= null && clockRecordVoList.size()>0){
                for (int j = 0; j < clockRecordVoList.size(); j++) {
                    ClockRecordVo clockRecordVo = clockRecordVoList.get(j);
                    String clockDayStr = clockRecordVo.getClockDay();
                    /*
                    判断工作日是否有打卡记录，只要有一条打卡记录，条件都成立。第一条打卡记录（有效的：5：00到下班时间18：00），就是上班时间，以后的打卡记录都是下班时间（有效的：上班时间8：30到下班打卡有效时间24：00）
                     */
                    if (dayStr.equalsIgnoreCase(clockDayStr)) {
                        System.out.println("###工作日："+dayStr+".........has clock record==============>"+dayStr);
                        isClockRecord = true;
                        //打卡记录处理
                        handleClockRecord(record,clockRecordVo);
                    }
                }
              }
                //这天没有一条打卡记录
                if(!isClockRecord){
                    record.setOnStatusCode(3);
                    record.setOnStatus("缺卡");
                    record.setOffStatusCode(3);
                    record.setOffStatus("缺卡");
                }
            }
        }

        System.out.println(JSONObject.toJSON(attendanceInitData));
        return attendanceInitData;
    }


     class AttendanceParams{
         long onWorkTimeL;
         long permitLateTimeL;
         long permitOnLimitL;
         long offWorkTimeL;
         long permitOffLimitMinuteL;

     }

    /**
     * 获取考勤参数
     * @return
     */
     public AttendanceParams getAttendanceParamsInstance(){
         Schedule schedule = scheduleService.selectOne();
         AttendanceParams attendanceParams = new AttendanceParams();
         //上班时间
         String onWorkTime = schedule.getWorkingHours();
         long onWorkTimeL = CalendarSupport.getTimeValue(onWorkTime);
         //允许迟到时间
         int  permitLateMinute  = schedule.getPermitLateTime();
         long permitLateTimeL = CalendarSupport.getAfterTime(onWorkTimeL, permitLateMinute);
         // 30 超过该范围算旷工
         int permitOnLimit = schedule.getPermitOnLimit();
         long permitOnLimitL = CalendarSupport.getAfterTime(onWorkTimeL,permitOnLimit );

         //下班时间
         String offWorkTime = schedule.getOffHours();
         long offWorkTimeL = CalendarSupport.getTimeValue(offWorkTime);
         //超过该范围算旷工 15
         int permitOffLimitMinute = schedule.getPermitOffLimit();
         long permitOffLimitMinuteL = CalendarSupport.getBeforeTime(offWorkTimeL, permitOffLimitMinute);

        attendanceParams.onWorkTimeL = onWorkTimeL;
        attendanceParams.permitLateTimeL = permitLateTimeL;
        attendanceParams.permitOnLimitL = permitOnLimitL;
        attendanceParams.offWorkTimeL = offWorkTimeL;
        attendanceParams.permitOffLimitMinuteL = permitOffLimitMinuteL;
//         System.out.println("schedue template==========>onWorkTime{"+onWorkTime+"},permitLateMinute{"+permitLateMinute+"},permitOnLimit{"+permitOnLimit+"},offWorkTime{"+offWorkTime+"},permitOffLimitMinute{"+permitOffLimitMinute+"}");
        return attendanceParams;
     }



    /**
     * 考勤记录处理：
     */
     public void handleClockRecord( AttendanceRecordVo record, ClockRecordVo clockRecordVo ){
         //考勤参数对象（打卡记录与考勤参数进行计算，获取有效的打卡记录，以及考勤状态）
         AttendanceParams attendanceParams = getAttendanceParamsInstance();
         /*
            1 情况一：打卡记录只有一条：1这打卡记录有效
          */
        String clockRecords = clockRecordVo.getClockTimes();
         System.out.println("##   clockRecords==>"+ clockRecords);

          // 打开记录过滤，获取有效的打卡记录
         List<String> clockTimeList = clockRecordFilter(clockRecords, "05:00:00", "23:59:59");
         System.out.println("##clockRecord........time filter.........."+JSONObject.toJSON(clockTimeList));

         //情况一：所有打卡记录都在上班时间之前，则只有上班打卡时间，下班打卡时间为空；
         //情况二：所有打卡记录都在下班时间之后，则只有下班打卡时间，上班打卡时间为空；
         //情况三：有一条记录则为上班打卡时间；下班打卡时间为空；有两条及以上打卡数据，则最后的打卡时间为下班时间。
         long startClockTimeL =  CalendarSupport.getTimeValue(clockTimeList.get(0));
         long endClockTimeL =  CalendarSupport.getTimeValue(clockTimeList.get(clockTimeList.size()-1));

          if(endClockTimeL < attendanceParams.onWorkTimeL){
              //只有上班打卡数据，无下班打卡数据
              record.setClockInTime(clockTimeList.get(0));
          }else if(startClockTimeL > attendanceParams.offWorkTimeL){
              //只有下班打卡数据，无上班打卡数据
              record.setClockOutTime(clockTimeList.get(clockTimeList.size()-1));
          }else if(clockTimeList.size()== 1){
              //只有上班打卡数据，无下班打卡数据
              record.setClockInTime(clockTimeList.get(0));
          }else{
              record.setClockInTime(clockTimeList.get(0));
              record.setClockOutTime(clockTimeList.get(clockTimeList.size()-1));
          }


         // 上班打卡时间
         String clockInTime = record.getClockInTime();
         //下班打卡时间
         String clockOutTime = record.getClockOutTime();
         System.out.println("clcokInTime==>"+clockInTime+",========clockOutTime===>"+clockOutTime);
         //处理签到
         if(!TextUtils.isEmpty(clockInTime)){
             long clockInTimeL = CalendarSupport.getTimeValue(clockInTime);
             //上班时间比较
             if(CalendarSupport.timeCompare(clockInTimeL, attendanceParams.onWorkTimeL)<=0){
                 record.setOnStatusCode(0);
                 record.setOnStatus("正常");
             }else if(CalendarSupport.timeCompare(clockInTimeL, attendanceParams.permitLateTimeL)<=0){
                 record.setOnStatusCode(1);
                 record.setOnStatus("迟到A");
             }else if(CalendarSupport.timeCompare(clockInTimeL, attendanceParams.permitOnLimitL)<=0){
                 record.setOnStatusCode(2);
                 record.setOnStatus("迟到B");
             }else{
                 record.setOnStatusCode(3);
                 record.setOnStatus("缺卡");
             }

         }else{
             //没有签到打卡记录
             record.setOnStatusCode(3);
             record.setOnStatus("缺卡");
         }
         //处理签退
         if(!TextUtils.isEmpty(clockOutTime)){
             long  clockOutTimeL =   CalendarSupport.getTimeValue(clockOutTime);
             if(CalendarSupport.timeCompare(clockOutTimeL, attendanceParams.offWorkTimeL)>=0){
                 record.setOffStatusCode(0);
                 record.setOffStatus("正常");
             }else if(CalendarSupport.timeCompare(clockOutTimeL, attendanceParams.permitOffLimitMinuteL)>=0){
                 record.setOffStatusCode(1);
                 record.setOffStatus("早退");
             }else{
                 record.setOffStatusCode(3);
                 record.setOffStatus("缺卡");
             }
         }else{
             record.setOffStatusCode(3);
             record.setOffStatus("缺卡");
         }
     }


    /**
     * 打卡记录过滤，获取有效的数据
     * handler
     * 1 2019-01-23 17:22:30  -convert to--->17:22:30
     * 2 17:22:30  --convert to------>long type value
     * 3 filter
     * @param clockRecord 数据格式  2019-01-23 17:22:30
     * @param earliestClockValidTime 最早打卡有效时间
     * @param lastestClockValidTime 最晚打卡有效时间
     * @return
     */
    public List<String> clockRecordFilter(String clockRecord, String earliestClockValidTime, String lastestClockValidTime){
        String[] clockRecordArray = clockRecord.split(",");
        List<String> clockTimeList= new ArrayList <>();

        long earliestClockValidTimeL = CalendarSupport.getTimeValue(earliestClockValidTime);
        long lastestClockValidTimeL = CalendarSupport.getTimeValue(lastestClockValidTime);

        if(clockRecordArray != null && clockRecordArray.length>0){
            int length = clockRecordArray.length;
            for (int i=0;i<length;i++){
                String clockTimeValue = CalendarSupport.dateToTimeStr(CalendarSupport.str2Date(clockRecordArray[i]));
                long clockTimeL = CalendarSupport.getTimeValue(clockTimeValue);
                if(clockTimeL >= earliestClockValidTimeL && clockTimeL <= lastestClockValidTimeL ){
                    clockTimeList.add(clockTimeValue);
                }
            }
        }

        Collections.sort(clockTimeList);

        return clockTimeList;
    }



    /**
     * 获取指定月份对应的工作日，节假日，休息日
     * @param selectedMonth
     * @return
     */
    public   HashMap<String,Object> getAttendanceInitDataByMonth(Integer empNo,String selectedMonth){
        int[] dateArr = CalendarSupport.getYearAndMonth(selectedMonth);
        int year = dateArr[0];
        int month = dateArr[1];
        System.out.println("指定============》year"+year+",month==>"+month);
        int maxDay =  CalendarSupport.getMaxDayByDate(year,month);

        //日期类型 0工作日，1节假日，2休息日
        HashMap<String,Object>attendanceInitData = new HashMap <>(maxDay);

        String selectMonthWrapper = selectedMonth+"-01";
        System.out.println("holiday==========>param"+selectMonthWrapper);
        List<HolidaySchedule> holidayScheduleList  = holidayScheduleService.selectListByMonth(selectMonthWrapper);
        System.out.println("holiday==========>result size"+holidayScheduleList.size());
        if(holidayScheduleList != null && holidayScheduleList.size()>0){
            // 请假日
            for (HolidaySchedule holidaySchedule : holidayScheduleList) {
               // System.out.println("holiday------day type value"+holidaySchedule.getHDay());
                //节假日
                if(holidaySchedule.getHType() == 2){
                    AttendanceRecordVo holidayRecord = new AttendanceRecordVo();
                    holidayRecord.setEmpNo(empNo);
                    holidayRecord.setDayType(2);
                    holidayRecord.setFestival(holidaySchedule.getHFestival());

                    attendanceInitData.put(holidaySchedule.getHDay(),holidayRecord);
                }
                //调休加班
                if(holidaySchedule.getHType() == 0){
                    AttendanceRecordVo overTimeRecord = new AttendanceRecordVo();
                    overTimeRecord.setEmpNo(empNo);
                    overTimeRecord.setDayType(0);

                    attendanceInitData.put(holidaySchedule.getHDay(), overTimeRecord);
                }
            }
        }
        //获取指定月份日期类别
        for(int i=1;i<=maxDay;i++){
            String dateStr = CalendarSupport.getDateStr(year, month, i);
            //去除节假日，调休加班（星期六，星期天）添加休息日
            if(!attendanceInitData.containsKey(dateStr)){
                if(CalendarSupport.isRestDay(CalendarSupport.strToDate(CalendarSupport.getDateStr(year, month, i)))){
                    AttendanceRecordVo restRecord = new AttendanceRecordVo();
                    restRecord.setEmpNo(empNo);
                    restRecord.setDayType(1);

                    attendanceInitData.put(dateStr, restRecord);
                }else{
                    //正常工作日
                    AttendanceRecordVo workRecord = new AttendanceRecordVo();
                    workRecord.setEmpNo(empNo);
                    workRecord.setDayType(0);

                    attendanceInitData.put(dateStr, workRecord);
                }
            }
        }

        return attendanceInitData;
    }




//    //获取请假数据 这个月的
//    boolean hasLeave =false;
//    List<Workflow> leaveList = workflowService.getListByApplicantAndMonth(empNo, selectMonthWrapper);
//                  if(leaveList != null && leaveList.size()>0){
//        for(int i=0;i<leaveList.size();i++){
//            Workflow workflow = leaveList.get(i);
//            //请假开始时间
//            String leaveStartDateStr = CalendarSupport.dateToStr(CalendarSupport.str2Date(workflow.getStartTime()));
//            //请假结束时间
//            String leaveEndDateStr  =   CalendarSupport.dateToStr(CalendarSupport.str2Date(workflow.getEndTime()));
//            System.out.println("leave date===========>"+leaveStartDateStr);
//            //有请假，且已经被同意 && workflow.getApprovalStatus()== 1
//            if(dayStr.equalsIgnoreCase(leaveStartDateStr) ){
//                hasLeave = true;
//                System.out.println("has leave record==============>"+dayStr);
//                record.setLeaveTypeCode(workflow.getFlowType());
//            }
//        }
//        if(!hasLeave){
//            record.setOnStatusCode(3);
//            record.setOnStatus("缺卡");
//            record.setOffStatusCode(3);
//            record.setOffStatus("缺卡");
//        }
//    }

//
//    /**
//     * 获取指定月份对应的工作日，节假日，休息日
//     * @param year
//     * @param month
//     * @return
//     */
//    public   HashMap<String,Integer> getDateTypeByMonth(int year ,int month,int maxDay){
//        //日期类型 0工作日，1节假日，2休息日
//        HashMap<String,Integer>dayTypeMap = new HashMap <>(maxDay);
//        List<HolidaySchedule> holidayScheduleList  = holidayScheduleService.selectListByMonth(month);
//        if(holidayScheduleList != null && holidayScheduleList.size()>0){
//            // 请假日
//            for (HolidaySchedule holidaySchedule : holidayScheduleList) {
//                //节假日
//                if(holidaySchedule.getHType() == 2){
//                    System.out.println("holiday+++节假日++++++++"+holidaySchedule.getHDay());
//                    dayTypeMap.put(holidaySchedule.getHDay(), 2);
//                }
//                //调休加班
//                if(holidaySchedule.getHType() == 0){
//                    System.out.println("holiday++调休加班+++++++++"+holidaySchedule.getHDay());
//                    dayTypeMap.put(holidaySchedule.getHDay(), 0);
//                }
//            }
//        }
//        //获取指定月份日期类别
//        for(int i=1;i<=maxDay;i++){
//            String dateStr = CalendarSupport.getDateStr(year, month, i);
//            //去除节假日，调休加班（星期六，星期天）添加休息日
//            if(!dayTypeMap.containsKey(dateStr)){
//                if(CalendarSupport.isRestDay(CalendarSupport.strToDate(CalendarSupport.getDateStr(year, month, i)))){
//                    System.out.println("去除节假日，调休加班（星期六，星期天）添加休息日=======>"+dateStr);
//                    dayTypeMap.put(dateStr, 1);
//                }else{
//                    //正常工作日
//                    dayTypeMap.put(dateStr, 0);
//                    System.out.println("正常工作日========>"+dateStr);
//                }
//
//            }
//        }
//        System.out.println(JSONObject.toJSON(dayTypeMap));
//
//        return dayTypeMap;
//    }
//
//
//
//
//    public HashMap <String, Object> getRecordMapByMonth2(Integer empNo, String selectedMonth) {
//        int[] dateArr = CalendarSupport.getYearAndMonth(selectedMonth);
//        int year = dateArr[0];
//        int month = dateArr[1];
//        System.out.println("指定============》year"+year+",month==>"+month);
//        int maxDay =  CalendarSupport.getMaxDayByDate(year,month);
//        //step1： 选择的月份是否为过去月份
//        Boolean isPrevious = CalendarSupport.isBeforeNow(year,month);
//        System.out.println("指定的日期比当前月份早-------》"+isPrevious);
//        HashMap<String,Integer>dayTypeMap = getDateTypeByMonth(year,month ,maxDay);
//        HashMap<String,Object> attendanceRecordVoMap = new HashMap <>(maxDay);
//
//
//        //遍历月日期类型
//        for(Map.Entry<String, Integer> entry: dayTypeMap.entrySet()) {
//            String dayStr = entry.getKey();
//            Integer dayType = entry.getValue();
//            System.out.println("遍历 日期=========》"+dayStr);
//            System.out.println("遍历 日期=========》"+entry.getValue());
//            //节假日
//            if (dayType == 2) {
//                attendanceRecordVoMap.put(dayStr, 2);
//            }
//            //休息日
//            if (dayType == 1) {
//                attendanceRecordVoMap.put(dayStr, 1);
//
//            }
//            //工作日（包括调休）
//            if (dayType == 0) {
//                //工作日，考勤数据才有效，添加考勤数据
//                List <ClockRecordVo> clockRecordVoList = clockRecordService.selectClockRecordListByMonth(empNo, selectedMonth);
//                for (int j = 0; j < clockRecordVoList.size(); j++) {
//                    ClockRecordVo clockRecordVo = clockRecordVoList.get(j);
//                    String clockDayStr = clockRecordVo.getClockDay();
//                    if (dayStr.equalsIgnoreCase(clockDayStr)) {
//                        attendanceRecordVoMap.put(dayStr, clockRecordVo);
//
//                    }
//                }
//            }
//        }
//
//        return attendanceRecordVoMap;
//    }


}
