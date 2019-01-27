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
import {getAttendanceStatistics} from "@/api/attendance"
import {getDeptInfo} from "@/api/clockRecord"
import {formatTimeToStr} from "@/utils/tools"
export default {
	data(){
		return {
			// config
      tableHead:[
        {prop:"empNo",label:"员工号"},
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
      // this.getData();
    },
    // 页码改变事件
    changePage:function(page){
      console.log(page)
    },
    search:function(){
      if(this.query.empNo && this.query.selectMonth){
			  this.getData();
      }else{
        this.$message({
          type:"warning",
          message:"搜索条件不能为空!"
        });
      }
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
		}
	}
}
</script>
<style>
.attendance-statistics {
    padding:20px;
    background-color: #fff;
}
.controller-bar {
		display:flex;
		justify-content: flex-start;
    padding:20px 0;
}
.controller-bar>div{
	margin-right:30px;
}
.paginate {
	text-align: center;
}
</style>
