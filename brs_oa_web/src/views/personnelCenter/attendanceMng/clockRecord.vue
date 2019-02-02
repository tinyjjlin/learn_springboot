<template>
  <div class="record-list">
    <div class="controller-bar">
      <div class="block">
        <span class="demonstration">日期:</span>
        <el-date-picker
          v-model="query.selectedDate"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="datePickerConfig"
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
      :data="list"
      style="width: 100%">
        <el-table-column
          v-for="(v,k) in tableHead"
          :key="k"
          :prop="v.prop"
          :label="v.label"
          :width="v.width">
        </el-table-column>
      </el-table>
      <div class="paginate">
        <pagination v-show="paginate.total" :total="paginate.total" :page.sync="paginate.currentPage" :limit.sync="paginate.limit" :layout="paginate.layout" @pagination="changePage" />
      </div>
    </div>
  </div>
</template>

<script>
import pagination from "@/components/Pagination";
import {getClockRecord,getDeptInfo} from "@/api/clockRecord"

import {formatTimeToStr} from "@/utils/tools"
import { mapGetters } from 'vuex';
export default {
  components:{
    pagination
  },
  data: () => {
    return {
      // config
      tableHead:[ 
        {prop:"empNo",label:"员工号",width:150},
        {prop:"empName",label:"姓名",width:150},
        {prop:"clockDay",label:"日期",width:200},
        // {prop:"clockInTime",label:"签到",width:200},
        // {prop:"clockOutTime",label:"签退",width:200},
        {prop:"clockTimes",label:"打卡记录"}
      ],
      paginate:{
        limit:10,
        total:0,
        currentPage:1,
        background:true,
        layout:"prev, pager, next"
      },
      datePickerConfig:{
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      deptInfo:[],
      query:{
        empNo:"",
        selectedDate:"",
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
		
		this.getData()
		
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
		}
  },
  methods: {
    getData:function(){
      this.query.limit = this.paginate.limit
      this.query.page = this.paginate.currentPage
      
      getClockRecord(this.query)
      .then(res=>{
        if(res.data.code==200){
          this.list = res.data.data.list;
          // 分页参数
          this.paginate.limit = res.data.data.pageSize;
          this.paginate.total = res.data.data.total;
          this.paginate.currentPage = res.data.data.pageNum;
          
          // 格式化时间
          this.list.forEach((v,k)=>{
            this.list[k].clockDay = formatTimeToStr(v.clockDay,"yyyy-MM-dd")
            this.list[k].clockInTime = formatTimeToStr(v.clockInTime,"hh:mm:ss")
            this.list[k].clockOutTime = formatTimeToStr(v.clockOutTime,"hh:mm:ss")

            // 排序
            this.list[k].clockTimes = this.list[k].clockTimes.split(",").sort((a,b)=>{
              return a > b ? 1 : -1;
            }).map(item=>{
              return formatTimeToStr(item,"hh:mm:ss")
            }).join(" , ")
          })
        }else{
          this.$message.error(res.data.msg);
        }
        console.log(res.data)
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
			this.getData();
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
      console.log(newValue)
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
