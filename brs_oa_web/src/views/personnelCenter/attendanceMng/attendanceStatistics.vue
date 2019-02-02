<template>
	<div class="attendance-statistics">
    <div class="controller-bar">
      <div class="search-bar">
        <div class="block">
          <span class="demonstration">日期:</span>
            <el-date-picker
            v-model="query.selectMonth"
            :default-value="query.selectMonth"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择月"
            @change="changeDate"/>
        </div>
        <div class="staff">
          <span class="demonstration">员工:</span>
          <el-cascader
          placeholder="选择员工"
          :options="adjustDeptInfo"
          filterable
          clearable
          @change="changeStaff">
          </el-cascader>
        </div>
        <div class="submit">
          <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        </div>
      </div>
      <div class="operation">
				<el-button type="primary" icon="el-icon-plus" @click="exportExcel">导出</el-button>
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
            <el-table-column label="请假天数" >
              <template slot-scope="scope">	
                {{getLeaveCount(scope.row)}}
              </template>
            </el-table-column>
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
    
    <div ref="echarts" id="echarts-bar"></div>
  </div>
</template>

<script>
import {getAttendanceStatistics} from "@/api/attendance"
import {getDeptInfo} from "@/api/clockRecord"
import {formatTimeToStr} from "@/utils/tools"


// 引入基本模板
let echarts = require('echarts/lib/echarts')
// 引入柱状图
require('echarts/lib/chart/bar')
// 引入主题
require('echarts/theme/macarons')
// 引入提示框和标题组件
require('echarts/lib/component/tooltip')
require('echarts/lib/component/title')
export default {
	data(){
		return {
			// config
      tableHead:[
        {prop:"empNo",label:"员工号"},
        {prop:"empName",label:"姓名"},
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
      data:[],
      deptInfo:[],
      query:{
        selectMonth:formatTimeToStr(new Date(),"yyyy-MM"),
        empNo:"",
      }

		}
	},
	mounted:function(){
    // 获取员工
		getDeptInfo()
		.then(res=>{
			if(res.data.code!=200){
				this.$message.error(res.data.msg);
				return ;
      }
      this.deptInfo = res.data.data;
		})
		.catch(error=>{
				this.$message.error(error)
    })
    this.getData();

    
    // 初始化ECharts
    this.initCharts(this.data)
  },
	methods:{
		changeStaff:function(newSearch){
			this.query.empNo = newSearch[1];
		},
		 // 获取数据
    getData:function(){
      getAttendanceStatistics(this.query)
      .then(res=>{
 
        if(res.data.code==200){

          this.data = res.data.data.list;
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
      // this.getData();
    },
    // 页码改变事件
    changePage:function(page){
      console.log(page)
    },
    search:function(){
      // if(this.query.empNo && this.query.selectMonth){
			  this.getData();
      // }else{
      //   this.$message({
      //     type:"warning",
      //     message:"搜索条件不能为空!"
      //   });
      // }
    },
    // 初始化Echarts
    initCharts(){
      this.$refs.echarts.style.display = "block";
      this.chart = echarts.init(this.$refs.echarts);
      this.setOptions();

      // 图表自适应
      let that = this;
      window.onresize = function(){
        that.chart.resize();
      }
    },
    setOptions(data){
      this.chart.setOption({
        title : {
            text: '出勤情况分布统计',
            subtext: '单位:天'
        },
        color:["#334b5c","#c23531"],
        tooltip : {
            trigger: 'axis',                  	            
            formatter: "{b} <br/> {a1} : {c1}天  <br/> {a0} : {c0}天"
        },
        legend: {
            data:['正常出勤','缺勤']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                data : ['IT部','财务部','编辑部']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'正常出勤',
                type:'bar',
                data:[2.0, 4.9, 7.0],
            },
            {
                name:'缺勤',
                type:'bar',
                data:[2.6, 5.9, 9.0],

            }
        ]
      })
    },
    exportExcel(){
      if(!this.data.length){
        this.$message({
          type:"wraning",
          message:"数据不能为空!"
        })
        return ;
      }

      import('@/vendor/Export2Excel').then(excel => {
        
        let that = this;
        let name = "";
        // this.deptInfo.forEach((v,k)=>{
        //   console.log(v.staffList)
        //   if(v.staffList.length){
        //     v.staffList.forEach((v1,k1)=>{
        //       if(v1.empNo==that.query.empNo){
        //         name = v1.name+"-";
        //       }
        //     })
        //   }
        // })
      
        const tHeader = this.tableHead.map(item => item.label).concat(['请假天数',"年假","事假","病假","婚假","产假","丧假","陪产假","例假"])
        // {prop:"annualLeaveDay",label:"年假"},
        // {prop:"personalLeaveDay",label:"事假"},
        // {prop:"sickLeaveDay",label:"病假"},
        // {prop:"marriageLeaveDay",label:"婚假"},
        // {prop:"maternityLeaveDay",label:"产假"},
        // {prop:"funeralLeaveDay",label:"丧假"},
        // {prop:"paternityLeaveDay",label:"陪产假"},
        // {prop:"casualLeaveDay",label:"例假"},
        
        const filterVal = this.tableHead.map(item => item.prop).concat(["LeaveCount","annualLeaveDay","personalLeaveDay","sickLeaveDay","marriageLeaveDay","maternityLeaveDay","funeralLeaveDay","paternityLeaveDay","casualLeaveDay"])
        const list = this.data;
        const data = this.formatJson(filterVal, list);
        excel.export_json_to_excel({
          header: tHeader, //表头 必填
          data, //具体数据 必填
          filename: name+"考勤统计"+this.query.selectMonth, //非必填
          // autoWidth: true, //非必填
          // bookType: 'xlsx' //非必填
        })
      })

    },
    formatJson(filterVal, jsonData) {
      // return jsonData.map(v => filterVal.map(j => v[j]))
      return jsonData.map((v)=>{
        return filterVal.map((j)=>{
          if(j=="LeaveCount"){
            return this.getLeaveCount(v)  
          }else{
            return v[j]
          }
        })
      })
    }
   
	},
	computed:{
		adjustDeptInfo:function(){
			const that = this;
			var res = [];
			
			// 获取部门及员工信息并格式化
			if(this.deptInfo){
				// 格式化部门
				this.deptInfo.forEach(function(v,k){
					let temp = {
						value:v.deptNo,
						label:v.deptName
					}
					res[k] = temp;
					// 格式化部门下的员工
					v.staffList.forEach(function(v1,k1){
						if(!res[k].children) res[k].children=[];
						let length = res[k].children.length;
						let tmp = {
							value:v1.empNo,
							label:v1.name
						}
						res[k].children[length] = tmp;
					})
				})
			}
			return res;
    },
    getLeaveCount(row){
      return (row)=>{
        return row.annualLeaveDay
        +row.personalLeaveDay+
        row.sickLeaveDay+
        row.marriageLeaveDay+
        row.maternityLeaveDay+
        row.funeralLeaveDay+
        row.paternityLeaveDay+
        row.casualLeaveDay
      }
    },
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
  padding: 20px 0;
  display: flex;
  justify-content: space-between;
}
.controller-bar>div{
	margin-right:30px;
}
.search-bar {
  display: flex;
  justify-content: space-between;
}
.search-bar > div {
  padding: 0 5px;
}

.paginate {
	text-align: center;
}
#echarts-bar {
  margin:50px auto;
  width:50%;
  min-height:600px;
}
</style>
