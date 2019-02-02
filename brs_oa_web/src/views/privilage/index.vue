<template>
  <div class="roleMng">
    <!-- <span class="title">{{ title }}：</span> -->
    <div class="content">
      <div class="header">
        <el-row>
          <el-button
            type="primary"
            icon="el-icon-plus"
            @click="addPrivilage12"
          >{{ buttonPrivilage }}</el-button>
        </el-row>
        <!-- <el-input placeholder="请输入内容" v-model="searchText">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input> -->
      </div>
      <div class="main">
        <el-table
          ref="multipleTable"
          :data="privilageList"
          tooltip-effect="dark"
        >
          <!-- <el-table-column type="selection" width="55"></el-table-column> -->
          <el-table-column prop="permissionNo" label="编号" width="100"></el-table-column>
          <el-table-column prop="permissionName" label="权限名称" width="300"></el-table-column>
          <el-table-column prop="permissionCode" label="权限标识" width="300"></el-table-column>
          <el-table-column prop="isEnabled" label="是否有效" width="120"></el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <el-button
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button>
              <el-button type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
    <!-- 新增权限 -->
    <el-dialog title="添加权限" :width="formDialogWidth" :visible.sync="dialogFormVisible">
      <el-form ref="privilageForm" :model="privilageForm" :rules="ruleForm">
        <!-- <el-form-item
          label="权限编号"
          :label-width="formLabelWidth"
          :rules="[{required: true, message: '请输入角色编号', trigger: 'blur' },
          { type:'number', message: '格式错误', trigger: 'blur'}]"
          prop="permissionNo"
        >
          <el-input width="300px" v-model="privilageForm.permissionNo" placeholder="请输入权限编号"></el-input>
        </el-form-item>-->
        <input type="hidden" :name="privilageForm.permissionNo">
        <el-form-item label="权限名称" :label-width="formLabelWidth" prop="permissionName">
          <el-input width="300px" v-model="privilageForm.permissionName" placeholder="请输入权限名称"></el-input>
        </el-form-item>
        <el-form-item label="权限地址" :label-width="formLabelWidth" prop="permissionCode">
          <el-input v-model="privilageForm.permissionCode" placeholder="请输入权限地址，例：salaryReportView"></el-input>
        </el-form-item>
        <el-form-item label="是否有效" :label-width="formLabelWidth">
          <el-radio-group v-model="privilageForm.isEnabled">
            <el-radio label="1">有效</el-radio>
            <el-radio label="0">无效</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :plain="true" @click="cancel">取 消</el-button>
        <!-- success  形参： 判断是编辑还是新增 -->
        <el-button :plain="true" type="primary" @click="submit(privilageForm)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getPrivilageList,
  addPrivilage,
  delPrivilage,
  editPrivilage
} from "@/api/privilage";
import Pagination from "@/components/Pagination";

export default {
  name: "roleMng",
  components: { Pagination },
  data() {
    return {
      title: "权限管理",
      buttonPrivilage: "新增",
      privilageList: [],
      // multipleSelection: [], // 全选
      dialogFormVisible: false, // 弹出框的显示
      privilageForm: {
        permissionNo: "",
        permissionName: "",
        permissionCode: "",
        isEnabled: "1"
      },
      ruleForm: {
        permissionName: [
          { required: true, message: "权限名称不能为空！", trigger: "blur" }
        ],
        permissionCode: [
          { required: true, message: "权限路径不能为空！", trigger: "blur" }
        ]
      },
      formLabelWidth: "100px",
      formDialogWidth: "550px",
      searchText: "",
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
      this.getListPrivilage(this.listQuery)
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    // 选择状态，全选 非全选
    // handleSelectionChange(val) {
    //   this.multipleSelection = val;
    // },
    // 删除
    handleDelete(index, row) {
      this.$confirm("此操作将永久删除该权限，是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delPrivilage(row.permissionNo)
            .then(res => {
              if (res.data.code == 200) {
                // 移除数组中的元素
                this.privilageList.splice(index, 1);
                this.message('success','删除成功！')
              }
            })
            .catch(error => {
              this.message('error',error)
            });
        })
        .catch(() => {
          this.message('info','已取消删除')
        });
    },
    // 新增
    addPrivilage12() {
      this.dialogFormVisible = true;
    },
    // 编辑
    handleEdit(index, row) {
      this.dialogFormVisible = true;
      let that = this;
      this.privilageList.forEach(function(value) {
        if (value.permissionNo == row.permissionNo) {
          that.privilageForm = {
            permissionName: value.permissionName,
            permissionCode: value.permissionCode,
            isEnabled: value.isEnabled.toString(),
            permissionNo: value.permissionNo
          };
        }
      });
    },
    //  取消
    cancel() {
      this.dialogFormVisible = false;
      this.message('取消添加权限')
      this.clearForm('','',"1",'')
    },
    //  成功
    submit(data) {
      this.$refs.privilageForm.validate(valid => {
        if (valid) {
          if (this.privilageForm.permissionNo == "") {
            //  将数据提交到数据库
            addPrivilage(data).then(res => {
              this.getListPrivilage(this.listQuery)
              this.dialogFormVisible = false;
              this.clearForm('','',"1",'')
              this.message('success','权限添加成功')
            });
          } else {
            editPrivilage(data)
              .then(res => {
                if (res.data.code == 200) {
                  this.getListPrivilage(this.listQuery)
                  this.dialogFormVisible = false;
                  this.clearForm('','',"1",'')
                  this.message('success','权限修改成功')
                }
              })
              .catch(error => {
                console.log(error);
              });
          }
        }
      });
    },
    /**
     * 解决重复代码
     */
    // 提示
    message(type,info) {
      this.$message({
        type: type,
        message: info
      });
    },
    //  赋值表单内容  privilageForm
    clearForm(name, code, isEnabled, no) {
      this.privilageForm = {
        permissionName: name,
        permissionCode: code,
        isEnabled: isEnabled,
        permissionNo: no
      };
    },
    //  获取权限列表
    getListPrivilage(data) {
      getPrivilageList(data).then(res => {
        this.privilageList = res.data.data.list;
        this.total = res.data.data.total;
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.roleMng {
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
      float: right;
      top: 20px;
      right: 30px;
    }
    .el-table {
      width: 95%;
      margin: auto;
      padding: 22px;
    }
    .footer {
      text-align: center;
      margin-top: 35px;
      line-height: 60px;
    }
  }
}
</style>