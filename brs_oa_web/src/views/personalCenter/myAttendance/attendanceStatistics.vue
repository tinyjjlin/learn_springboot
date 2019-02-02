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
        <el-table-column label="请假信息">
            <el-table-column prop="annualLeaveDay" label="年假"></el-table-column>
            <el-table-column prop="personalLeaveDay" label="事假"></el-table-column>
            <el-table-column prop="sickLeaveDay" label="病假"></el-table-column>
            <el-table-column prop="marriageLeaveDay" label="婚假"></el-table-column>
            <el-table-column prop="maternityLeaveDay" label="产假"></el-table-column>
            <el-table-column prop="funeralLeaveDay" label="丧假"></el-table-column>
            <el-table-column prop="paternityLeaveDay" label="陪产假"></el-table-column>
            <el-table-column prop="casualLeaveDay" label="例假"></el-table-column>
        </el-table-column>
      </el-table>
    </div>
    <div class="echarts-box">
      <div ref="echarts" id="echarts"></div>
      <div id="echarts1" ref="echarts1"></div>
    </div>
  </div>
</template>

<script>
import {getPersonalAttendanceStatistics} from "@/api/attendance"
import {formatTimeToStr} from "@/utils/tools"

// 引入基本模板
let echarts = require('echarts/lib/echarts')
// 引入饼图
require('echarts/lib/chart/pie')
// 引入主题
require('echarts/theme/macarons')
// 引入提示框和标题组件
require('echarts/lib/component/tooltip')
require('echarts/lib/component/title')

export default {
  data: () => {
    return {
      // config
      tableHead:[
        // annualLeaveDay: 0
        // attendanceDateList: null
        // casualLeaveDay: 0
        // daysOffDay: 0
        // empNo: 100091
        // funeralLeaveDay: 0
        // holidayDayCount: 1
        // lackOfCardCount: 24
        // lateACount: 1
        // lateBCount: 0
        // lateCCount: 4
        // leaveEarlyACount: 0
        // leaveEarlyBCount: 0
        // marriageLeaveDay: 0
        // maternityLeaveDay: 0
        // paternityLeaveDay: 0 陪产假
        // personalLeaveDay: 0  事假
        // realWorkDay: 6       出勤天数
        // realWorkTimeSum: 48  工作时数
        // reissueCardCount: 0 补卡次数
        // restDayCount: 8  休息日
        // sickLeaveDay: 0   病假
        // workDayCount: 22  工作日天数
        {prop:"workDayCount",label:"工作日"},
        {prop:"realWorkDay",label:"正常出勤天数"},
        {prop:"lateACount",label:"迟到A次数"},
        {prop:"lateBCount",label:"迟到B次数"},
        {prop:"lateCCount",label:"迟到C次数"},
        {prop:"leaveEarlyACount",label:"早退A次数"},
        {prop:"leaveEarlyBCount",label:"早退B次数"},
        {prop:"lackOfCardCount",label:"缺卡次数"},
        {prop:"reissueCardCount",label:"补卡次数"},
        {prop:"holidayDayCount",label:"节假日"},
        {prop:"restDayCount",label:"休息日"},

        // {prop:"annualLeaveDay",label:"年假"},
        // {prop:"personalLeaveDay",label:"事假"},
        // {prop:"sickLeaveDay",label:"病假"},
        // {prop:"marriageLeaveDay",label:"婚假"},
        // {prop:"maternityLeaveDay",label:"产假"},
        // {prop:"funeralLeaveDay",label:"丧假"},
        // {prop:"paternityLeaveDay",label:"陪产假"},
        // {prop:"casualLeaveDay",label:"例假"},
      ],
      query:{
        selectMonth:formatTimeToStr(new Date(),"yyyy-MM"),
        limit:1,
        page:1
        // selectMonth:" ",
      },
      data:[],
      chart:"",
      chart1:"",

    }
  },
  mounted:function(){
    // 获取数据
    this.getData()
    
    // 初始化ECharts
    this.initCharts(this.data)
  },
  computed: {

  },
  methods: {
    // 获取数据
    getData:function(){
      getPersonalAttendanceStatistics(this.query)
      .then(res=>{
 
        if(res.data.code==200){
          console.log(res.data)
          if(res.data.data){
            this.data = [res.data.data];  
          }else{
            this.$message({
              type:"wraning",
              message:"没有获取到相关数据,请稍后重试!"
            })
          }
        }else{
          this.$message.error(res.data.msg);
        }
      })
      .catch(error=>{
        this.$message.error(error)
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
    // 初始化Echarts
    initCharts(){
      this.$refs.echarts.style.display = "block";
      this.chart = echarts.init(this.$refs.echarts);
      this.chart1 = echarts.init(this.$refs.echarts1);
      
      this.setOptions();

      // 图表自适应
      let that = this;
      window.onresize = function(){
        that.chart.resize();
        that.chart.resize();
      }
    },
    setOptions(data){
  
      if(!data){
        return ;
      
      }
      let seriesData = [
        {value:data.realWorkDay,name:"正常"},
        {value:data. workDayCount-data.realWorkDay,name:"缺勤"},
      ];

      this.chart.setOption({
        title : {
            text: '个人考勤统计图',
            // subtext: '纯属虚构',
            x:'left'
        },
        color:["#334b5c","#c23531"],
        tooltip : {
            trigger: 'item',                    	            
            formatter: "{a} <br/>{b} : {c}天 ({d}%)"

        },
        legend: {
            data:['正常','缺勤'],
            orient:"vertical",
            x:"right",
            padding:[30,50,10,0]
        },
        series : [
            {
                name:'出勤情况汇总',
                type:'pie',
                radius : "25%",
                center : ['30%', '35%'],
                data:seriesData,
                itemStyle:{ 
                    normal:{ 
                        label:{ 
                          show: true, 
                          formatter: '{b} : {c}天 ({d}%)' 
                        }, 
                        labelLine :{show:true} 
                    } 
                } 
            },
        ]
      })

      let seriesData1 = [
        {value:data.annualLeaveDay,name:"年假"},
        {value:data. personalLeaveDay,name:"事假"},
        {value:data.sickLeaveDay ,name:"病假"},
        {value:data.marriageLeaveDay ,name:"婚假"},
        {value:data.maternityLeaveDay ,name:"产假"},
        {value:data.funeralLeaveDay ,name:"丧假"},
        {value:data.paternityLeaveDay ,name:"陪产假"},
        {value:data.casualLeaveDay ,name:"例假"},
        
      ];
      
      this.chart1.setOption({
        title : {
            text: '个人请假统计图',
            // subtext: '纯属虚构',
            x:'left'
        },
        // color:["#334b5c","#c23531"],
        tooltip : {
            trigger: 'item',                    	            
            formatter: "{a} <br/>{b} : {c}天 ({d}%)"

        },
        legend: {
            data:seriesData1.map(item=>item.name),
            orient:"vertical",
            x:"right",
            padding:[30,50,10,0]
        },
        series : [
            {
                name:'请假情况汇总',
                type:'pie',
                radius : "25%",
                center : ['30%', '35%'],
                data:seriesData1,
                itemStyle:{ 
                    normal:{ 
                        label:{ 
                          show: true, 
                          formatter: '{b} : {c}天 ({d}%)' 
                        }, 
                        labelLine :{show:true} 
                    } 
                } 
            },
        ]
      })
    }
  },
  watch: {
    data(val) {
      console.log(val[0])
      this.setOptions(val[0])
    }
  }
}
</script>

<style scoped>
.attendance-statistics {
  padding:20px;
  background-color: #fff;
}
.controller-bar {
  padding:20px 0;
}
.echarts-box {
  display:flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
#echarts ,#echarts1{
  margin-top:20px;
  width:40%;
  min-height:500px;
}
</style>
