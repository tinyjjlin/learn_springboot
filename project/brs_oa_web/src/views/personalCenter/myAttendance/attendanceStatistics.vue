<template>
  <div class="attendance-statistics">
    <div class="controller-bar">
      <div class="block">
        <span class="demonstration">日期:</span>
        <el-date-picker
          v-model="query.selectMonth"
          :default-value="query.selectMonth"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择月"
          :clearable="false"
          @change="changeDate"/>
      </div>
    </div>
    <div class="main">
      <el-table
      :header-cell-style="{background:'#eef1f6'}"
      border
      :data="data"
      style="width: 100%">
        <el-table-column
          v-for="(v,k) in tableHead"
          :key="k"
          :prop="v.prop"
          :label="v.label"
          :width="v.width">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import {getPersonalAttendanceStatistics} from "@/api/attendance"
import {formatTimeToStr} from "@/utils/tools"

export default {
  data: () => {
    return {
      // config
      tableHead:[
        {prop:"realWorkDay",label:"出勤天数"},
        {prop:"commonLateCount",label:"迟到A次数"},
        {prop:"seriousLateCount",label:"迟到B次数"},
        {prop:"leaveEarlyCount",label:"早退次数"},
        {prop:"absentDay",label:"旷工"},
        {prop:"lackOfCardCount",label:"缺卡次数"},
        {prop:"holidayDayCount",label:"节假日"},
        {prop:"restDayCount",label:"休息日"},
        {prop:"annualLeaveDay",label:"年假"},
        {prop:"personalLeaveDay",label:"事假"},
        {prop:"sickLeaveDay",label:"病假"},
        {prop:"marriageLeaveDay",label:"婚假"},
        {prop:"maternityLeaveDay",label:"产假"},
        {prop:"funeralLeaveDay",label:"丧假"},
        {prop:"paternityLeaveDay",label:"陪产假"},
        {prop:"casualLeaveDay",label:"例假"},
      ],
      query:{
        selectMonth:formatTimeToStr(new Date(),"yyyy-MM"),
        limit:1,
        page:1
        // selectMonth:" ",
      },
      data:[],
    }
  },
  mounted:function(){
    // 获取数据
    this.getData()
  },
  computed: {

  },
  methods: {
    // 获取数据
    getData:function(){
      getPersonalAttendanceStatistics(this.query)
      .then(res=>{
 
        if(res.data.code==200){
          this.data = [res.data.data];
        }else{
          this.$message.error(res.data.msg);
        }
      })
      .catch(error=>{
        this.$message.error(res.data.msg)
      })
    },
    // 日期改变事件
    changeDate:function(newDate){
      if(!newDate){
        this.$message({
          type:"warning",
          message:"请选择日期!"
        })
        return ;
      }
      this.getData();
    },
    // 页码改变事件
    changePage:function(page){
      console.log(page)
    },
  }
}
</script>

<style>
.attendance-statistics {
  padding:20px;
  background-color: #fff;
}
.controller-bar {
  padding:20px 0;
}

</style>
