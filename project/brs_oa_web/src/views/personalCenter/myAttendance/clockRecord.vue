<template>
  <div class="record-list">
    <div class="controller-bar">
      <div class="block">
        <span class="demonstration">日期:</span>
        <el-date-picker
          v-model="query.selectedDate"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
          :picker-options="datePickerConfig"
          @change="changeDate">
        </el-date-picker>
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
        <pagination v-show="paginate.total>0" :total="paginate.total" :page.sync="paginate.currentPage" :limit.sync="paginate.limit" :layout="paginate.layout" @pagination="changePage" />
      </div>

    </div>
  </div>
</template>

<script>
import pagination from "@/components/Pagination";
import {getPersonalClockRecord} from "@/api/clockRecord"
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
        {prop:"clockDay",label:"日期",width:200},
        // {prop:"clockInTime",label:"签到",width:200},
        // {prop:"clockOutTime",label:"签退",width:200},
        {prop:"clockTimes",label:"打卡记录"}
      ],
      paginate:{
        limit:10,
        total:1000,
        currentPage:1,
        background:true,
        layout:"prev, pager, next"
      },
      datePickerConfig:{
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      list:[],
      query:{
        selectedDate:""
      },
    }
  },
  mounted:function(){
    this.getData()
  },
  computed: {
    ...mapGetters([
      'clcokIsUpdate'
    ]),
  },
  methods: {
    getData:function(){
      this.query.limit = this.paginate.limit
      this.query.page = this.paginate.currentPage
      
      getPersonalClockRecord(this.query)
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
      })
      .catch(error=>{
        this.$message.error(res.data.msg)
      })
    },
    changeDate:function(newDate){
      this.getData();
    },
    changePage:function(newPage){
			console.log(newPage)
		},
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
    padding:20px 0;
}
.paginate {
    text-align: center;
}
</style>
