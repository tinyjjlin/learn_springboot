<template>
  <div class="emp-container">
    <el-select v-model="department" clearable filterable @change="departmentThis(department)" placeholder="请选择部门">
      <el-option v-for="item in departmentList" :key="item.deptId" 
        :label="item.deptName" :value="item.deptNo"></el-option>
    </el-select>
    <div class="emp-main">
      <div class="emp-list-wrapper">
        <ul class="emp-list clearfix">
          <li class="item" :key="index" v-for="(staff,index) in list">
            <div class="emp-info">
              <img class="emp-head" :src="'http://172.18.19.3:8066/' + staff.picture">
              <div class="emp-item">
                <div>
                  <i>部门：</i>
                  <span>{{staff.deptName}}</span>
                </div>
                <div>
                  <i>职位：</i>
                  <span>{{staff.position}}</span>
                </div>
                <div>
                  <i>姓名：</i>
                  <span>{{staff.name}}</span>
                </div>
                <div>
                  <i>邮箱：</i>
                  <span>{{staff.email}}</span>
                </div>
                <div>
                  <i>手机号：</i>
                  <span>{{staff.mobile}}</span>
                </div>
                <div>
                  <i>性别：</i>
                  <span>{{staff.gender == 0 ? '男' : '女'}}</span>
                </div>
                <div>
                  <i>工号：</i>
                  <span>{{staff.empNo}}</span>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div class="pagination-wrapper">
        <pagination
          v-show="total"
          :total="total"
          :page.sync="listQuery.page"
          :limit.sync="listQuery.limit"
          @pagination="getList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { fetchList , getDepartmentList , getDepartmentMan } from "@/api/staff";
import Pagination from "@/components/Pagination";

export default {
  name: "staffList",
  components: { Pagination },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,

      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: "+id"
      },
      title: "查看员工信息",
      searchText: "",
      departmentList: [],
      department: ""
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.listLoading = true;
      fetchList(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
      });
      getDepartmentList().then((res)=>{
        this.departmentList = res.data.data
      })
    },
    departmentThis(data) {
      let queryDate = {
        deptNo:data,
        page:this.listQuery.page,
        limit:this.listQuery.limit
      }
      getDepartmentMan(queryDate).then((res)=>{
        this.list = res.data.data.list
        this.total = res.data.data.total
      })
    }
  }
};
</script>

<style scoped>
.emp-container {
  width: 95%;
  height: 100%;
  border: 2px solid #dddddd;
  margin: 20px auto;
}
.el-select {
  width: 300px;
  margin: 20px 0 0 30px;
}
.clearfix::after {
  clear: both;
  content: ".";
  display: block;
  width: 0;
  height: 0;
  visibility: hidden;
}
ul,
li {
  list-style: none;
}
ul {
  display: block;
  margin: 0;
  border: 0;
  padding: 0;
}
.emp-main {
  padding: 20px 50px;
}
.emp-list li {
  float: left;
  margin-top: 25px;
  border: 1px solid #daadaf;
  margin-right: 50px;
  width: 330px;
  padding: 18px;
  border-radius: 10px;
  box-shadow: 12px 12px 12px 3px #c0c0c5;
}
img.emp-head {
  position: absolute;
  width: 70px;
  height: 70px;
  top: 0;
  left: 0;
  border: 1px solid #eaedf1;
  border-radius: 50%;
  -webkit-border-radius: 50%;
  -moz-border-radius: 50%;
}
.emp-info {
  position: relative;
  height: 155px;
}
.emp-item {
  position: relative;
  margin-left: 80px;
}
.emp-item i {
  display: inline-block;
  font-style: normal;
}
.emp-item span {
  display: inline-block;
  line-height: 20px;
  font-size: 14px;
  color: #9c9c93;
}

.pagination-wrapper {
  text-align: center;
}
</style>

