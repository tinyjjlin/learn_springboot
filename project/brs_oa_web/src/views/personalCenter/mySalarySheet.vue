<template>
<div class="app-container">
  <div class="lock" v-if="isRePassword">
      <el-input
        type="password"
        placeholder="请输入密码"
        v-model="password"
        class="pws-input"
        >
        <!-- <i slot="prefix" class="el-input__icon el-icon-search"></i> -->
        <svg-icon slot="prefix" class="lock-icon"  icon-class="lock1" />
      </el-input>
      <el-button type="primary" @click="unlock" >确定</el-button>
  </div>
  <div class="controller-bar" v-if="!isRePassword">
    <div class="search-bar">
      <div class="block">
        <span class="demonstration">日期:</span>
        <el-date-picker
          v-model="listQuery.selectedMonth"
          type="month"
          placeholder="选择月"
          value-format="yyyy-MM"
          @change="changeDate">
        </el-date-picker>
      </div>
      <div class="search">
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </div>
    </div>
  </div>
  <div class="main" v-if="!isRePassword">
      <el-table
      :data="tableData"
      style="width: 100%">

      <el-table-column
        prop="settlementDay"
        label="日期"
  
        align="center">
      </el-table-column>

      <el-table-column
        prop="empName"
        label="姓名"
  
        align="center">
      </el-table-column>

      <el-table-column
        prop="basicSalary"
        label="基本工资"
      
        align="center">
      </el-table-column>

      <el-table-column
        prop="workdayFactor"
        label="系数"
      
        align="center">
      </el-table-column>

      <el-table-column
        prop="commission"
        label="提成"
      
        align="center">
      </el-table-column>

      <el-table-column
        prop="allowance"
        label="津贴"
      
        align="center">
      </el-table-column>

      <el-table-column
        prop="payment"
        label="个人税前工资"
      
        align="center">
      </el-table-column>

      <el-table-column label="代扣款项" align="center">
          <el-table-column
            prop="housingFund"
            label="公积金"
          
            align="center">
          </el-table-column>
          <el-table-column
            prop="socialInsurance"
            label="社保"
            width="80"
            align="center">
          </el-table-column>
          <el-table-column
            prop="taxOfStaff"
            label="个人所得税"
            width="110"
            align="center">
          </el-table-column>
      </el-table-column>

      <el-table-column
        prop="paymentInHand"
        label="个人税后工资"
        align="center">
      </el-table-column>
    </el-table>
    <div class="paginate">
      <!-- <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" /> -->
    </div>
  </div>

</div>
</template>

<script>
    import { fetchPersonalSalaryMonthlyReport } from '@/api/salary'
    import { verifyLoginPwd } from "@/api/login"
    import waves from '@/directive/waves' // Waves directive
    import { parseTime } from '@/utils'
    import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

   export default {
    name: 'stafflist',
    components: { Pagination },
    directives: { waves },
    data() {
    return {
      isRePassword:true,
      password:"",
      tableKey: 0,
      tableData: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id',
        selectedMonth:""
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      textMap: {
        update: 'Edit',
        create: 'Create'
      },
      downloadLoading: false,

    }
  },
  created() {
    // localStorage.removeItem("lock");

    let lock = JSON.parse( localStorage.getItem("lock") ) || {effectiveTime:0};
    
    // 当有效时间已经失效时
    if( lock.effectiveTime<=new Date().getTime() ){
      this.isRePassword = true;
    }else{
      this.isRePassword = false;
    }

    this.getList()
  },
  beforeUpdate(){
    if(this.password){
      return ;
    }
    let lock = JSON.parse( localStorage.getItem("lock") );

    // 当有效时间已经失效时
    if( lock.effectiveTime<=new Date().getTime() ){
      this.$message({
        type:"info",
        message:'请重新输入密码!'
      })
      this.isRePassword = true;
    }else{
      this.isRePassword = false;
    }
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchPersonalSalaryMonthlyReport(this.listQuery).then(response => {
        this.tableData = response.data.data.list
        this.total = response.data.data.total
      })
    },
    unlock(){
      let query = {password:this.password}
      console.log(verifyLoginPwd(query))
      // return ;
      verifyLoginPwd(query).then(response => {
          if(response.data.code==200){
            this.isRePassword = false;
            this.password = "";
            let lock = {effectiveTime :new Date().getTime()+1000*60*5}
            localStorage.setItem("lock",JSON.stringify(lock))
            
          }else{
            this.$message.error(response.data.msg)
          }
      })

    },
    changeDate(newValue){

    },
    search(){
      if(!this.listQuery.selectedMonth){
        this.$message({
          type:"warning",
          message:"请选择月份!"
        })
        return ;
      }
      this.getList()
    }
  }
 }

</script>
<style>
.lock {
  width:350px;
  position:absolute;
  top:50%;
  left:50%;
  margin-left:-200px;
  margin-top:-100px;
  z-index:10;
  display:flex;
}
.lock-icon {
  height:1.1em !important;
  font-size:2em;
  margin:2px;
}
.pws-input input{
  padding-left:38px!important;
}
.controller-bar {
    padding:20px 0;
		display:flex;
		justify-content: space-between;
}
.search-bar {
	display:flex;
	justify-content: space-between;
}
.search-bar>div {
	padding:0 5px;
}
.paginate {
    text-align: center;
}
</style>
