<template>
  <div class="record-list">
    <div class="controller-bar">
      <div class="block">
        <span class="demonstration">日期:</span>
        <el-date-picker
          v-model="query.selectMonth"
          type="month"
          placeholder="选择月"
          value-format="yyyy-MM"
          @change="changeDate">
        </el-date-picker>
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
      :data="pageList"
      style="width: 100%">
        <el-table-column
          v-for="(v,k) in tableHead"
          :key="k"
          :prop="v.prop"
          :label="v.label"
          :width="v.width">
        </el-table-column>

        <el-table-column label="日期类型" width="100" prop="dayType">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.dayType==0">工作日</el-tag>		
						<el-tag v-if="scope.row.dayType==1" type="success">休息日</el-tag>
						<el-tag v-if="scope.row.dayType==2" type="success">节假日</el-tag>
					</template>
				</el-table-column>

      </el-table>
      <div class="paginate">
        <pagination v-show="paginate.total>0" :total="paginate.total" :page.sync="paginate.currentPage" :limit.sync="paginate.limit" :layout="paginate.layout" @pagination="changePage" />
      </div>
    </div>
  </div>
</template>

<script>
import pagination from "@/components/Pagination";
import {formatTimeToStr} from "@/utils/tools"
import { mapGetters } from 'vuex';

import {getAttendanceRecord} from "@/api/attendance"
import {getDeptInfo} from "@/api/clockRecord"
export default {
  components:{
    pagination
  },
  // "dayType": 0 ,  日期类型  0；工作日 1:休息日 2：节假日
  // "offStatus": null, 下班状态  正常 早退 矿工
  // "onStatus": null,  上班状态  正常 迟到 矿工
  // "clockOutTime": null, 下班打卡时间 
  // "festival": null,      节假日名称
  // "empNo": null,          员工号
  // "clockInTime": null      上班打卡时间

  data: () => {
    return {
      // config
      tableHead:[ 
        {prop:"date",label:"日期"},
        {prop:"empNo",label:"员工号"},
        {prop:"empName",label:"姓名"},
        {prop:"clockInTime",label:"签到"},
        {prop:"clockOutTime",label:"签退"},
        {prop:"onStatus",label:"签到状态"},
        {prop:"offStatus",label:"签退状态"},
      ],
      paginate:{
        limit:10,
        total:0,
        currentPage:1,
        background:true,
        layout:"prev, pager, next"
      },
      deptInfo:[],
      query:{
        selectMonth:"",
        empNo:"",
      },
      list:[],
  
    }
  },
  mounted:function(){
		const that = this;
		// 获取选项
		getDeptInfo()
		.then(res=>{
			if(res.data.code!=200){
				this.$message.error(res.data.msg);
				return ;
      }
			that.deptInfo = res.data.data;
		})
		.catch(error=>{
				this.$message.error(error)
		})
		
		// this.getData()
		
  },
  computed: {
    ...mapGetters([
      'clcokIsUpdate'
    ]),
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
    pageList(){
      let start = (this.paginate.currentPage-1)*this.paginate.limit;
      
      return this.list.slice(start,start+this.paginate.limit)
    }
  },
  methods: {
    getData:function(){
 
      getAttendanceRecord(this.query)
      .then(res=>{
        if(res.data.code==200){
  
          var MonthDates = [];
          var tmpDates = [];
          for (let v in res.data.data) {
            tmpDates[tmpDates.length] = v;
          }
          tmpDates.sort(function(a,b){
            return a > b ? 1: -1;
          })
          tmpDates.forEach(function(v,k){
            let tmp = res.data.data[v];
            tmp.date = v;
            tmp.day = k+1;
            MonthDates[k] = tmp
          })
          this.list = MonthDates;
          this.paginate.total = this.list.length;
        }else{
          this.$message.error(res.data.msg);
        }
        // console.log(res.data)
      })
      .catch(error=>{
        this.$message.error(res.data.msg)
      })
    },
    changeDate:function(newDate){

		},
		changeStaff:function(newSearch){
      this.query.empNo = newSearch[1];
		},
		changePage:function(newPage){
			// console.log(newPage)
		},
		search:function(){
      
      if(this.query.empNo || this.query.selectedDate){
			  this.getData();
      }else{
        this.$message({
          type:"warning",
          message:"搜索条件不能为空!"
        });
      }
		}
  },
  watch:{
    clcokIsUpdate:function(newValue){
      // console.log(newValue)
      this.getData();
    }
  }
}
</script>

<style>
.record-list {
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
