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
          <el-table-column prop="id" label="编号" width="200%"></el-table-column>
          <el-table-column prop="name" label="职位" width="200%"></el-table-column>
          <el-table-column prop="enabled" label="启用" width="200%"></el-table-column>
          <el-table-column label="操作" width="200%">
            <template slot-scope="scope">
              <el-button
                size="mini"
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
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
      :title="tip == 1 ? '新增职位' : '编辑职位'"
      :width="formDialogWidth"
      @close="closePosition"
      :visible.sync="dialogFormVisible"
    >
      <el-form ref="adminForm" :model="adminForm" :rules="ruleForm">
        <el-form-item prop="id">
          <el-input type="hidden" v-model="adminForm.id"></el-input>
        </el-form-item>
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
        <el-button :plain="true" type="primary" @click="submit(adminForm, tip)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getPositionListByPaging,
  addPosition,
  deletePosition,
  editPosition
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
      tip: 1,
      positionList: [],
      dialogFormVisible: false, // 弹出框的显示
      adminForm: {
        id: '',
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
      this.tip = 1
      this.dialogFormVisible = true;
      if(this.adminForm.id !== '') {
        this.$refs.adminForm.resetFields();
      }
    },
    // 编辑
    handleEdit(index, row) {
      this.tip = 0;
      this.dialogFormVisible = true;
      this.adminForm = {
        id: row.id,
        name: row.name,
        enabled: String(row.enabled)
      }
      console.log(this.adminForm)
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm("此操作将永久删除该职位，是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deletePosition(row.id)
            .then(res => {
              if (res.data.code == 200) {
                // 移除数组中的元素
                this.positionList.splice(index, 1);
                this.$message({
                  type: "success",
                  message: "删除成功"
                });
              }
            })
            .catch(error => {
              this.$message({
                type: "error",
                message: error
              });
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
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
    submit(data, tip) {
      console.log(data)
      this.$refs.adminForm.validate(valid => {
        if(valid) {
          if(tip == 1) {
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
          if(tip == 0) {
            editPosition(data).then(res => {
              if(res.data.code == 200) {
                this.dialogFormVisible = false;
                this.$refs.adminForm.resetFields();
                this.$message({
                  type: 'success',
                  message: '职位编辑成功'
                })
                this.getList()
              } else {
                this.$refs.adminForm.resetFields();
                this.$message({
                  type: 'error',
                  message: '请重新编辑信息'
                })
              }
            })
          }
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
    width: 96%;
    height: 100%;
    background: #fff;
    margin: auto;
    .el-row {
      margin-left: 2%;
      padding-top: 1.5%;
      display: inline-block;
    }
    .el-input {
      display: inline-block;
      width: 200px;
      top: 20px;
    }
    .el-table {
      width: 95%;
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
  .el-dialog .el-form-item:nth-child(1) {
    margin-bottom: 0px;
    width: 0;
    height: 0;
  }
}
</style>