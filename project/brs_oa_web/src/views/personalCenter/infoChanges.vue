<template>
  <div class="attendance-apply-list">
    <div class="controller-bar">
      <div class="operation">
        <el-button type="primary" icon="el-icon-plus" @click="addApply">申请</el-button>
      </div>

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
        <div class="apply-type-select">
          <span class="demonstration">申请类型:</span>
          <el-select v-model="query.flowCategory" clearable placeholder="请选择">
            <el-option
              v-for="item in applyTypes"
              :key="item.code"
              :label="item.description"
              :value="item.code"
            ></el-option>
          </el-select>
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

        <!-- <el-table-column label="申请类型" width="100" prop="flowType">
          <template
            slot-scope="scope"
          >{{ scope.row.flowType }}
          {{ scope.row.flowType ? applyTypes[scope.row.flowType-1].label : "未知" }}</template>
        </el-table-column>-->
        <el-table-column label="状态" width="100" prop="approvalStatus">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.approvalStatus==0">等待审批</el-tag>
            <el-tag v-if="scope.row.approvalStatus==1" type="success">已通过</el-tag>
            <el-tag v-if="scope.row.approvalStatus==2" type="danger">未通过</el-tag>
          </template>
        </el-table-column>
        <!-- <el-table-column label="操作" width="160" prop="approvalStatus">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="small"
              icon="el-icon-view"
              circle
              @click="previewRow(scope.row.serialNumber)"
            ></el-button>
            <el-button
              type="success"
              size="small"
              v-if="scope.row.approvalStatus==0"
              @click="editRow(scope.$index)"
        >编辑</el-button>-->
        <!-- <el-button type="warning" size="small"  v-else>补充材料</el-button> -->
        <!-- </template>
        </el-table-column>-->
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

    <el-dialog
      title="申请"
      :visible.sync="dialogFormVisible"
      :before-close="dialogBeforeClose"
      @close="dialogClose"
    >
      <application-form
        :applicationTypeValue="applicationTypeValue"
        :approverValue="approverValue"
        :appliValueTime="appliValueTime"
        @applicationType="applicationType"
        @applicant="applicant"
        @applyForTime="applyForTime"
        @applySuccess="applySuccess"
      ></application-form>
    </el-dialog>
  </div>
</template>
<script>
import pagination from "@/components/Pagination";
import applicationForm from "./infoChanges/applicationForm";

import { applyManWorkflow, getStaffInfoItem } from "@/api/staff";
export default {
  components: {
    pagination,
    applicationForm
  },
  data() {
    return {
      applicationTypeValue: "",
      approverValue: "",
      appliValueTime: "",
      // config
      tableHead: [
        { prop: "serialNumber", label: "编号", width: 50 },
        { prop: "applyTime", label: "申请时间", width: 180 },
        { prop: "applicantName", label: "申请人", width: 100 },
        { prop: "flowCategoryDescription", label: "申请类型", width: 150 },
        { prop: "firstItem", label: "属性一", width: 100 },
        { prop: "firstItemOldValue", label: "旧值" },
        { prop: "firstItemNewValue", label: "新值" },
        { prop: "secondItem", label: "属性二", width: 100 },
        { prop: "secondItemOldValue", label: "旧值" },
        { prop: "secondItemNewValue", label: "新值" },
        { prop: "approverName", label: "审批人", width: 100 }
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
        flowCategory: "",
        approvalStatus: ""
      },
      applyTypes: [],
      dialogFormVisible: false
    };
  },
  created() {
    getStaffInfoItem().then(res => {
      this.applyTypes = res.data.data;
    });
  },
  mounted() {
    this.initView();
  },
  methods: {
    applySuccess: function(data) {
      if (data == 1) {
        this.dialogFormVisible = false;
      }
    },
    applicant: function(data) {
      this.approverValue = data;
    },
    applyForTime: function(data) {
      this.appliValueTime = data;
    },
    applicationType: function(data) {
      this.applicationTypeValue = data;
    },
    dialogBeforeClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          this.applicationTypeValue = "";
          this.approverValue = "";
          this.appliValueTime = "";
          done();
        })
        .catch(_ => {});
    },
    dialogClose() {
      this.applicationTypeValue = "";
      this.approverValue = "";
      this.appliValueTime = "";
      this.initView();
    },
    initView: function() {
      this.query.limit = this.paginate.limit;
      this.query.page = this.paginate.currentPage;
      applyManWorkflow(this.query)
        .then(res => {
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
          this.$message.error(res.data.msg);
        });
    },
    changeDate: function(newDate) {
      this.query.selectedDate = newDate;
    },
    changePage: function(newPage) {
      this.initView()
    },
    handleChangeAppleType: function() {},
    search: function() {
      this.initView();
    },
    addApply: function() {
      this.dialogFormVisible = true;
    },
    editRow: function(index) {
      this.workflowEdit.editIndex = index;
      this.workflowEdit.workflowEditBoxStatus = true;
    },
    previewRow: function(serialNumber) {
      this.workflowPreview.workflowPreviewBoxStatus = true;
    }
  }
};
</script>
<style>
.attendance-apply-list {
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
