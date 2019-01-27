<template>
  <div class="positionMng">
    <div class="content">
      <div class="header">
        <el-row>
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="addNewPosition"
          >{{ addNewPositionTitle }}</el-button>
        </el-row>
        <!-- <el-input placeholder="请输入内容" v-model="searchText">
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>-->
      </div>

      <div class="main">
        <el-table ref="multipleTable" :data="positionList" tooltip-effect="dark">
          <el-table-column prop="id" label="编号" width="300"></el-table-column>
          <el-table-column prop="name" label="职位" width="700"></el-table-column>
          <el-table-column prop="enabled" label="启用" width="300"></el-table-column>
          <!-- <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column> -->
        </el-table>
      </div>
      <!-- 分页 -->
      <div class="footer">
        <pagination
          v-show="total"
          :total="total"
          :page.sync="listQuery.page"
          :limit.sync="listQuery.limit"
          @pagination="getList"
        />
      </div>
    </div>
    <el-dialog
      title="添加职位"
      :width="formDialogWidth"
      @close="closePosition"
      :visible.sync="dialogFormVisible"
    >
      <el-form ref="adminForm" :model="adminForm" :rules="ruleForm">
        <el-form-item label="职位" :label-width="formLabelWidth" prop="name">
          <el-input width="300px" v-model="adminForm.name" placeholder="请输入职位"></el-input>
        </el-form-item>
        <el-form-item label="启用" :label-width="formLabelWidth" prop="enabled">
          <el-radio v-model="adminForm.enabled" label="1">启用</el-radio>
          <el-radio v-model="adminForm.enabled" label="0">禁用</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :plain="true" @click="cancel">取 消</el-button>
        <el-button :plain="true" type="primary" @click="submit(adminForm)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getPositionListByPaging,
  addPosition,
  deletePosition
} from "@/api/position";
import { getRoleList, getAdminIdRoleList } from "@/api/role";
import Pagination from "@/components/Pagination";

export default {
  name: "positionMng",
  components: { Pagination },
  data() {
    return {
      title: "职位管理",
      addNewPositionTitle: "创建",
      positionList: [],
      dialogFormVisible: false, // 弹出框的显示
      adminForm: {
        name: "",
        enabled: ""
      },
      ruleForm: {
        name: [
          { required: true, message: "职位名称不能为空", trigger: "blur" }
        ],
        enabled: [
          { required: true, message: "请选择启用与否", trigger: 'blur' }
        ]
      },
      formLabelWidth: "100px",
      formDialogWidth: "550px",
      searchText: "",
      count: 10,
      dialogConfigRole: false,

      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: "+id"
      },
      total: 0,
      listLoading: true,
      tableData: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.listLoading = true;
      getPositionListByPaging(this.listQuery).then(res => {
        this.positionList = res.data.data.list;
        this.total = res.data.data.total;
      });
    },

    // 新增
    addNewPosition() {
      this.dialogFormVisible = true;
    },

    // handleDelete() {},
    closePosition() {
      this.$refs.adminForm.resetFields();
    },
    cancel() {
      this.dialogFormVisible = false;
      this.$refs.adminForm.resetFields();
      this.$message({
        message: "取消添加权限",
        type: "info"
      });
    },
    submit(data) {
      this.$refs.adminForm.validate(valid => {
        if(valid) {
          addPosition(data).then(res => {
            if(res.data.code == 200) {
              this.dialogFormVisible = false;
              this.$refs.adminForm.resetFields();
              this.$message({
                type: 'success',
                message: '职位添加成功'
              })
              this.getList()
            } else {
              this.$refs.adminForm.resetFields(); 
              this.$message({
                type: 'error',
                message: '请重新填写信息'
              })
            }
          })
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.positionMng {
  width: 95%;
  height: 100%;
  margin: 20px auto;
  .title {
    display: inline-block;
    margin: 0;
    padding: 27px 0 9px 26px;
    font-size: 20px;
    font-weight: 550;
  }
  .content {
    width: 1600px;
    height: 100%;
    background: #fff;
    margin: auto;
    .el-row {
      margin-left: 30px;
      padding-top: 20px;
      display: inline-block;
    }
    .el-input {
      display: inline-block;
      width: 200px;
      top: 20px;
    }
    .el-table {
      width: 1500px;
      margin: auto;
      padding: 22px;
      margin-top: 20px;
    }
    .footer {
      text-align: center;
      margin-top: 35px;
      line-height: 60px;
    }
  }
  .el-dialog:nth-child(1) {
    .el-checkbox + .el-checkbox {
      margin-left: 68px;
    }
  }
}
</style>