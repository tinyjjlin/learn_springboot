<template>
  <div class="picker-box">
    <div class="controller-bar">
      <div class="block">
        <span class="demonstration">选择一个时间:</span>
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
    <div class="picker">
      <div class="main">
        <div class="content">
          <table border="1" cellspacing="0" cellpadding="0">
            <tr class="head">
              <th>一</th>
              <th>二</th>
              <th>三</th>
              <th>四</th>
              <th>五</th>
              <th>六</th>
              <th>日</th>
            </tr>
            <tr v-for="(item,key) in getNormDat" :key="key">
              <td
                v-for="(v,k) in item"
                :key="k"
                :class="[{active:(key+1)*7-(7-k)===active}]"
                :style="[,{backgroundImage:'url(' + getSignImage(v.onStatus,v.offStatus) + ')'},{backgroundPosition:'center center'}]"
                @click="changeActive(key,k)">

                <span v-if="v.dayType==2" class="isholiday">节假日</span>
                <span :class="['daynumber',{week:(k>=item.length-2) },{filling:v.notCur}]">{{ v.day }}</span>
                <span v-if="v.festival && v.dayType==2" class="holidayName">{{ v.festival }}</span>
              </td>
            </tr>
          </table>
        </div>
      </div>
      <div :class="['sidebar',{'sidebar-red':active && data[active].dayType==2}]">
        <div class="isshow"  v-if="data[active]">
          <div class="dayType">
            <span>{{data[active].dayType==0 ? "工作日" : (data[active].dayType==1 ? "休息日" : "节假日") }}</span> 
          </div>
          <div class="date">
            {{ data[active].date}}
          </div>
          <div class="attend">
            <img :src="getSignEmoji(data[active].onStatus,data[active].offStatus)" alt="">
            <div/>
          </div>
          <div class="attendanceTime">
            上班打卡时间
            <div class="clockInTime">
              {{ data[active].clockInTime }}
              
              <!-- <span v-if="data[active].onStatus==1" class="attendanceWrong">迟到啦!</span> -->
              <!-- <span v-if="data[active].onStatus==2" class="attendanceWrong">旷工!</span> -->
              <span class="attendanceWrong">{{data[active].onStatus}}</span>
            </div>
            下班打卡时间
            <div class="clockOutTime">
              {{ data[active].clockOutTime }}
              <!-- <span v-if="data[active].offStatus==1" class="attendanceWrong">早退啦!</span> -->
              <!-- <span v-if="data[active].offStatus==2" class="attendanceWrong">旷工!</span> -->
              <span class="attendanceWrong">{{data[active].offStatus}}</span>
            </div>
          </div> 
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import calendar from '@/utils/calendar'
import {formatTimeToStr} from "@/utils/tools"
import {getPersonalAttendanceRecord} from "@/api/attendance"
import {getDeptInfo} from "@/api/clockRecord"
export default {
  data() {
    return {
      data: [],
      selectMonth: '',
      active: "",
      selectMonthDates:[],
      deptInfo:[],
      query:{
        selectMonth:"2018-12",
        empNo:"",
      }
    }
  },
  computed: {
    // 将35天分为5组
    getNormDat: function() {
      var data = []
      var count = 0
      var num = 0
      // console.log(this.getTable)
      this.data.forEach(function(e) {
        data[num] = data[num] || []

        data[num].push(e)
        count++
        if (count >= 7) {
          count = 0
          num++
        }
      })
      return data
    },
    // 根据状态值获取背景图片
    getSignImage(onStatus,offStatus) {
      return function(onStatus,offStatus) {
        const images = ['', 'signed.png', 'late.png', 'early.png', 'absenteeism.png']
        
        let num = 0;
        if(onStatus==2 || offStatus==2){
          num = 4;
        }else if(onStatus ==1){
          num = 2;
        }else if(offStatus ==1){
          num = 3;
        }else if(offStatus==0 && onStatus==0){
          num = 1;
        }else{
          num =0;
        }

        return 'src/assets/sign_status/' + images[num]
      }
    },
    getSignEmoji(onStatus,offStatus){
        return function(onStatus,offStatus){

            let file = offStatus==0 && onStatus==0 ? "happy.png" : "sad.png";
           
            return 'src/assets/sign_status/'+file;

        }
    },
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
  created() {
    // 保存当前时间
    // this.selectMonth = formatTimeToStr(,"yyyy-MM")
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
    
    this.getSelectMonthDate()
  },
  methods: {
    getTable: function() {

        // "dayType": 0 ,  日期类型  0；工作日 1:休息日 2：节假日
        // "offStatus": null, 下班状态  正常 早退 矿工
        // "onStatus": null,  上班状态  正常 迟到 矿工
        // "clockOutTime": null, 下班打卡时间 
        // "festival": null,      节假日名称
        // "empNo": null,          员工号
        // "clockInTime": null      上班打卡时间
      

      // 获取当前该月份
      let arr=this.query.selectMonth.split("-");

      const year = arr[0];
      const month = arr[1]-1;
      // 上個月的時間對象
      const preDate = new Date(year, month, 0)
      // 這個月的時間對象
      const tmpDate = new Date(year, month + 1, 0)

      // 獲取這個月第一天是星期幾
      var tmp = new Date(year, month, 1).getDay() || 7

      // 獲取這個月星期一之前的日期,填充滿表格
      var data = []
      if (tmp != 1) {
        for (let i = tmp - 2; i >= 0; i--) {
          data.push({
            day:preDate.getDate() - i,
            notCur:true
          })
        }
      }
    
      // 将该月数据放入数组
      this.selectMonthDates.forEach(function(v,k){
        data.push(v)
      })

      // 用下個月天數補滿表格
      if (data.length < 35) {
        const count = data.length
        for (let i = 1; i <= 35 - count; i++) {
          data.push({
            day:i,
            notCur:true
          })
        }
      }

      // 当月天数已经超出35格,补全该行
      if(data.length%7!==0){
        let hang = Math.ceil(data.length/7);
        let count = data.length;
        for (let i = 1; i <= hang*7 - count; i++) {
            data.push({
              day:i,
              notCur:true
            })
        }
      }

      this.data = data

    },
    changeDate: function(newValue) {
      if (!newValue) return
      this.getTable()
    },
    changeActive: function(key, k) {
      let index = (key + 1) * 7 - (7 - k);
      if(!this.data[index].notCur){
        this.active = index;
        
        console.log(this.data[this.active])
      }
    },
    getSelectMonthDate: function(){
      // let query = {selectMonth:this.selectMonth};
      // let query = {selectMonth:"2018-12"};
      
      getPersonalAttendanceRecord(this.query)
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
          this.selectMonthDates = MonthDates;
          this.getTable();
          // console.log(this.selectMonthDates);
          
        }else{
          this.$message.error(res.data.msg)
        }
      })
      .catch(error=>{
        this.$message.error(error)
      })

    },
    changeStaff: function(){
      
    },
    search:function(){
      if(this.query.empNo || this.query.selectMonth){
			  this.getData();
      }else{
        this.$message({
          type:"warning",
          message:"搜索条件不能为空!"
        });
      }
		}

  }
}
</script>

<style>
.picker-box {
    margin:30px auto;
    padding:10px;
}
.picker {
    width:100%;
    display:flex;
}
.controller-bar {
    display:flex;
		justify-content: flex-start;
    padding:20px 0;
}
.controller-bar>div{
	margin-right:30px;
}
.main {
    flex:1;

}
.content table {
    width:100%;
    font-size: 30px;
    border-color:#68aff9;
}
.content table tr td {border-color:#68aff9; border:1px solid #68aff9; }
.content .head th:nth-last-child(-n+2){
    color:red;
}
.content th{
    padding:10px;
    flex-grow:1;
    justify-content: center;
    line-height:30px;
    font-weight: 400;
    text-align: center;
    font-weight: bold;
}
.content td {
    /* padding:10px; */
    height:100px;
    position:relative;
    text-align: center;
    cursor: pointer;
}
.content td:hover {
    background-color: rgba(34,139,34 ,.1);
}
.content td.active {
    background-color: rgba(34,139,34 ,.1);
}

.content .week {
    color:red;
}
.content .filling {
    color:#bfbfbf;
}
.content .holidayName {
    display:block;
    font-size:16px;
    color:red;
}
.content .isholiday {
    position:absolute;
    top:0px;
    left:0px;
    padding:3px;
    background-color: #f43;
    color:#fff;
    font-size:14px;
}
.content .signed {
    background-color: rgba(103,194,58,0.6);
}

.sidebar {
    min-width:350px;
    padding:20px;
    margin-left:20px;
    background-color: skyblue;
    text-align: center;
    color:#fff;
    font-size:20px;
}
.sidebar .date {
    margin-bottom:5px;
}
.sidebar .attend {
    margin:20px;
}
.sidebar .attendanceStatus {
  display:inline-block;
  padding:5px 20px; 
  min-width:250px;
  margin-bottom:20px;
  font-size:25px;
  font-weight: bold;
  color:red;
  border:3px solid red;
  border-radius: 5px;
}
.sidebar .attendanceTime {

  line-height:34px;
}
.sidebar .attendanceTime .clockOutTime,.sidebar .attendanceTime .clockInTime{
  /* color:rgb(200, 224, 188); */
}
.sidebar .attendanceTime .attendanceWrong {
  padding-left:10px;
  color:red;
  font-weight: bold;
}
</style>
