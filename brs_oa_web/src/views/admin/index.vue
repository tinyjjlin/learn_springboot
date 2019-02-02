<template>
  <div class="roleMng">
    <div class="content">
      <div class="header">
        <el-row>
          <el-button type="primary" icon="el-icon-plus" @click="addAdmin">{{ buttonPrivilage }}</el-button>
        </el-row>
        <!-- <el-input placeholder="请输入内容" v-model="searchText">
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>-->
      </div>

      <div class="main">
        <el-table ref="multipleTable" style="100%" :data="adminList" tooltip-effect="dark">
          <el-table-column prop="empNo" label="工号" width="120"></el-table-column>
          <el-table-column prop="empName" label="姓名" width="150"></el-table-column>
          <!-- <el-table-column prop="loginName" label="登陆账号" width="150"></el-table-column> -->
          <el-table-column prop="deptName" label="部门" width="150"></el-table-column>
          <el-table-column prop="positionName" label="职位" width="200"></el-table-column>
          <el-table-column label="操作" width="230">
            <template slot-scope="scope">
              <el-button
                size="mini"
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button>
              <el-button
                size="mini"
                icon="el-icon-setting"
                type="primary"
                @click="handleConfig(scope.$index, scope.row)"
              >配置角色</el-button>
              <!-- <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
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
    <!-- 配置角色 -->
    <el-dialog title="配置角色" :width="formDialogWidth" :visible.sync="dialogConfigRole">
      <el-form ref="role" :model="role">
        <input type="hidden" :name="role.adminId">
        <el-form-item label="角色名称">
          <el-checkbox-group :min="1" v-model="role.name">
            <el-checkbox
              v-for="item in role.list"
              :label="item.roleNo"
              :key="item.roleNo"
              name="type"
              style="display: block;"
            >{{ item.roleName }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :plain="true" @click="configRoleCancel">取 消</el-button>
        <!-- success  形参： 判断是编辑还是新增 -->
        <el-button :plain="true" type="primary" @click="configRoleSubmit(role)">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 增、改 用户 -->
    <el-dialog
      :title="tip == 1 ? '添加用户' : '编辑用户'"
      :width="formDialogWidth"
      @close="addUser"
      :visible.sync="dialogFormVisible"
    >
      <el-form ref="adminForm" :model="adminForm" :rules="ruleForm">
        <el-form-item label="工号" :label-width="formLabelWidth" prop="empNo">
          <el-input width="300px" v-model="adminForm.empNo" placeholder="请输入工号"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth" prop="empName">
          <el-input width="300px" v-model="adminForm.empName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <!-- <el-form-item label="登陆账号" :label-width="formLabelWidth" prop="name">
          <el-input width="300px" v-model="adminForm.logAccount" placeholder="请输入登陆账号"></el-input>
        </el-form-item>-->
        <el-form-item label="部门" :label-width="formLabelWidth" prop="deptNo">
          <el-select v-model="adminForm.deptNo" clearable placeholder="请选择">
            <el-option
              v-for="item in department"
              :key="item.deptNo"
              :label="item.deptName"
              :value="item.deptNo"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位" :label-width="formLabelWidth" prop="positionNo">
          <el-select v-model="adminForm.positionNo" clearable placeholder="请选择">
            <el-option v-for="item in position" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
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
  getStaffInitialInfoList,
  addRoleAdminId,
  delAdmin,
  createNewStaff,
  getDepartmentList,
  editNewStaff
} from "@/api/staff";
import { getRoleList, getAdminIdRoleList } from "@/api/role";
import { getPositionList } from "@/api/position";
import Pagination from "@/components/Pagination";

export default {
  name: "roleMng",
  components: { Pagination },
  data() {
    const validatorpass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.adminForm.password !== "") {
          this.$refs.adminForm.validateField("password");
        }
      }
    };
    return {
      title: "用户管理",
      buttonPrivilage: "新增",
      tip: 1,
      adminList: [],
      // multipleSelection: [], // 全选
      dialogFormVisible: false, // 弹出框的显示
      adminForm: {
        empNo: "",
        empName: "",
        // logAccount: '',
        deptNo: "",
        positionNo: ""
      },
      ruleForm: {
        empNo: [{ required: true, message: "工号不能为空", trigger: "blur" }],
        empName: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        deptNo: [
          { required: true, message: "部门不能为空", trigger: "change" }
        ],
        positionNo: [
          { required: true, message: "职位不能为空", trigger: "change" }
        ]
      },
      formLabelWidth: "100px",
      formDialogWidth: "550px",
      searchText: "",
      count: 10,
      dialogConfigRole: false,
      role: {
        list: [],
        name: "",
        adminId: ""
      },
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
      tableData: null,
      department: [],
      position: []
    };
  },
  created() {
    this.getList();
    getDepartmentList().then(res => {
      this.department = res.data.data;
    });
    getPositionList().then(res => {
      this.position = res.data.data;
    });
  },
  methods: {
    getList() {
      this.listLoading = true;
      getStaffInitialInfoList(this.listQuery).then(res => {
        this.adminList = res.data.data.list;
        this.total = res.data.data.total;
      });
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
    // // 选择状态，全选 非全选
    // handleSelectionChange(val) {
    //   this.multipleSelection = val;
    // },
    // 删除
    // handleDelete(index, row) {
    //   this.$confirm("此操作将永久删除该权限，是否继续？", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   })
    //     .then(() => {
    //       delAdmin(index)
    //         .then(res => {
    //           if (res.data.code == 200) {
    //             // 移除数组中的元素
    //             this.adminList.splice(index, 1);
    //             this.$message({
    //               type: "success",
    //               message: "删除成功！"
    //             });
    //           }
    //         })
    //         .catch(error => {
    //           this.$message({
    //             type: "error",
    //             message: error
    //           });
    //         });
    //     })
    //     .catch(() => {
    //       this.$message({
    //         type: "info",
    //         message: "已取消删除"
    //       });
    //     });
    // },
    // 新增
    addAdmin() {
      this.tip = 1;
      this.dialogFormVisible = true;
      console.log(this.adminForm)
      if (this.adminForm.empNo !== "") {
        console.log('res');
        this.$refs.adminForm.resetFields();
      }
    },
    // 编辑
    handleEdit(index, row) {
      this.tip = 0;
      this.dialogFormVisible = true;
      this.adminForm = {
        empNo: row.empNo,
        empName: row.empName,
        deptNo: row.deptName,
        positionNo: row.positionName
      };
    },
    //  取消
    cancel() {
      this.dialogFormVisible = false;
      this.$refs.adminForm.resetFields();
      this.$message({
        message: "取消添加用户",
        type: "info"
      });
    },
    addUser() {
      this.$refs.adminForm.resetFields();
    },
    //  成功
    submit(data, tip) {
      //  判断类型
      if (typeof data.deptNo == "string") {
        this.department.forEach(value => {
          if ((value.deptName = data.deptNo)) {
            data.deptNo = value.deptNo;
          }
        });
      }
      if (typeof data.positionNo == "string") {
        this.position.forEach(value => {
          if ((value.name = data.positionNo)) {
            data.positionNo = value.id;
          }
        });
      }
      this.$refs.adminForm.validate(valid => {
        if (valid) {
          //  将数据提交到数据库
          if(tip == 1) {
            createNewStaff(data).then(res => {
              if (res.data.code == 200) {
                this.dialogFormVisible = false;
                this.$refs.adminForm.resetFields();
                this.$message({
                  message: "用户添加成功",
                  type: "success"
                });
                this.getList();
              } else {
                this.$refs.adminForm.resetFields();
                this.$message({
                  message: "用户添加失败，请重新添加",
                  type: "error"
                });
              }
            });
          }
          if(tip == 0) {
            editNewStaff(data).then(res => {
              if (res.data.code == 200) {
                this.dialogFormVisible = false;
                this.$refs.adminForm.resetFields();
                this.$message({
                  message: "用户编辑成功",
                  type: "success"
                });
                this.getList();
              } else {
                this.$refs.adminForm.resetFields();
                this.$message({
                  message: "用户编辑失败，请重新编辑",
                  type: "error"
                });
              }
            });
          }
        }
      })
    },
    //  配置角色
    handleConfig(index, row) {
      this.dialogConfigRole = true;
      this.role.adminId = row.empNo;
      // 获取所有角色
      getRoleList().then(res => {
        this.role.list = res.data.data.list;
      });
      getAdminIdRoleList(row.empNo).then(res => {
        var roleList = res.data.data.list == null ? "" : res.data.data.list;
        var roleArr = [];
        roleList.forEach(function(value) {
          roleArr.push(value.roleNo);
        });
        this.role.name = roleArr;
      });
    },
    //  取消配置角色
    configRoleCancel() {
      this.dialogConfigRole = false;
    },
    //  提交角色
    configRoleSubmit(data) {
      var admin = {
        empNo: this.role.adminId,
        roleNoArr: data.name
      };
      addRoleAdminId(admin).then(res => {
        if (res.data.code == 200) {
          this.dialogConfigRole = false;
          this.$message({
            message: "修改角色成功",
            type: "success"
          });
        }
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
  .el-dialog:nth-child(1) {
    .el-checkbox + .el-checkbox {
      margin-left: 68px;
    }
  }
}
</style>