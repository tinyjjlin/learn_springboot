<template>
  <div class="patchcard-approval-list">
    <div class="controller-bar">
      <div class="operation"></div>

      <div class="search-bar">
        <div class="block">
          <span class="demonstration">申请日期:</span>
          <el-date-picker
            v-model="query.selectedDate"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            :picker-options="datePickerConfig"
            @change="changeDate"
          ></el-date-picker>
        </div>
        <div class="approval-status-select">
					<span class="demonstration">审批状态:</span>
					<el-select v-model="query.approvalStatus" clearable placeholder="请选择">
						<el-option label="待审批" value="0"></el-option>
						<el-option label="已同意" value="1"></el-option>
						<el-option label="已拒绝" value="2"></el-option>
						
					</el-select>
				</div>
        <div class="search">
          <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        </div>
      </div>
    </div>
    <div class="main">
      <el-table :header-cell-style="{background:'#eef1f6'}" border :data="list" style="width: 100%">
        <el-table-column
          v-for="(v,k) in tableHead"
          :key="k"
          :prop="v.prop"
          :label="v.label"
          :width="v.width"
        ></el-table-column>


        <el-table-column label="审批状态" width="100" prop="flowType">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.approvalStatus==0" type="primary">待审批</el-tag>
            <el-tag v-if="scope.row.approvalStatus==1" type="success">已通过</el-tag>
            <el-tag v-if="scope.row.approvalStatus==2" type="danger">未通过</el-tag>
          </template>
        </el-table-column>
      
        <el-table-column label="操作" width="200" prop="status" align="center">
          <template slot-scope="scope">	
              <div class="yet"> 
                <el-button type="success" size="small" v-if="scope.row.approvalStatus==0" @click="updateWorkflowState(scope.row,'同意')">同意</el-button>
                <el-button type="danger" size="small"  v-if="scope.row.approvalStatus==0" @click="updateWorkflowState(scope.row,'拒绝')">拒绝</el-button>  
              </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="paginate">
        <pagination
          v-show="paginate.total"
          :total="paginate.total"
          :page.sync="paginate.currentPage"
          :limit.sync="paginate.limit"
          :layout="paginate.layout"
          @pagination="changePage"
        />
      </div>
    </div>
  </div>
</template>
<script>
import pagination from "@/components/Pagination";

import {
  getWorkflowApprover,
  getAttendanceApplyTypes,
  updateWorkflowApproval
} from "@/api/workProcess";
export default {
  components: {
    pagination
  },
  data() {
    return {
      // config
      tableHead: [
        { prop: "serialNumber", label: "编号", width: 100 },
        { prop: "patchTime", label: "补卡时间", width: 170 },
        { prop: "applicantName", label: "申请人", width: 100 },
        { prop: "content", label: "申请内容" },
        { prop: "applicationTime", label: "申请时间", width: 170 },
        { prop: "approverName", label: "审批人", width: 100 },
        { prop: "approverTime", label: "审批时间", width: 100 },
      ],
      paginate: {
        limit: 10,
        total: 0,
        currentPage: 1,
        background: true,
        layout: "prev, pager, next"
      },
      datePickerConfig:{
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },
      list: [],
      query: {
        selectedDate: "",
				approvalStatus:"",
      },
    };
  },
  mounted() {
    this.initView();

  },
  methods: {
    initView: function() {
      getWorkflowApprover(this.query)
        .then(res => {
          console.log(res.data);
          if (res.data.code == 200) {
            // 分页参数
            this.paginate.limit = res.data.data.pageSize;
            this.paginate.total = res.data.data.total;
            this.paginate.currentPage = res.data.data.pageNum;

            this.list = res.data.data.list;
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(error => {
          console.log(error);
          this.$message.error(res.data.msg);
        });
    },
    changeDate: function(newDate) {
      this.query.selectedDate = newDate;
    },
    changePage: function(newPage) {
      console.log(newPage);
    },
    search: function() {
      this.initView();
    },
    updateWorkflowState(row, value) {
      // return
      let state = value=="同意"? 1 : 2;
      let data = { serialNumber: row.serialNumber, approvalStatus: state };

      this.$confirm('你确定要'+value+'吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          updateWorkflowApproval(data)
          .then(res => {
            if (res.data.code == 200) {
              row.approvalStatus = state;
              this.$message({
                type: "success",
                message: res.data.msg
              });
            } else {
              this.$message.error(res.data.msg);
            }
          })
          .catch(error => {
            this.$message.error(res.data.msg);
          });
      })
     
    },
  }
};
</script>
<style>
.patchcard-approval-list {
  padding: 20px;
  background-color: #fff;
}
.controller-bar {
  padding: 20px 0;
  display: flex;
  justify-content: space-between;
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
</style>
