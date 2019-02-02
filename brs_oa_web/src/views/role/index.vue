<template>
  <div class="roleMng">
    <!-- <span class="title">{{ title }}：</span> -->
    <div class="content">
      <div class="header">
        <el-row>
          <el-button type="primary" icon="el-icon-plus" @click="addRole12">{{ buttonPrivilage }}</el-button>
        </el-row>
        <!-- <el-input placeholder="请输入内容" v-model="searchText">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input> -->
      </div>
      <div class="main">
        <el-table
          ref="multipleTable"
          :data="roleList"
          tooltip-effect="dark"
        >
          <!-- <el-table-column type="selection" width="55"></el-table-column> -->
          <el-table-column prop="roleNo" label="编号" width="120"></el-table-column>
          <el-table-column prop="roleName" label="角色名称" width="300"></el-table-column>
          <el-table-column prop="roleDesc" label="角色描述" width="300"></el-table-column>
          <el-table-column prop="isEnabled" label="启用" width="120"></el-table-column>
          <el-table-column label="操作" width="300">
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
              >配置权限</el-button>
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
    <!-- 配置权限 -->
    <el-dialog title="配置权限" :width="formDialogWidth" :visible.sync="dialogConfigPrivilage">
      <el-form ref="privilage" :model="privilage">
        <input type="hidden" :name="privilage.roleId">
        <el-form-item label="权限列表">
          <el-checkbox-group :min="1" v-model="privilage.name">
            <el-checkbox
              v-for="item in privilage.list"
              :label="item.permissionNo"
              :key="item.permissionNo"
              name="type"
              style="display: block;"
            >{{ item.permissionName }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :plain="true" @click="configRoleCancel">取 消</el-button>
        <!-- success  形参： 判断是编辑还是新增 -->
        <el-button :plain="true" type="primary" @click="configRoleSubmit(privilage)">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 增、改角色 -->
    <el-dialog title="添加角色" :width="formDialogWidth" :visible.sync="dialogFormVisible">
      <el-form ref="roleForm" :model="roleForm" :rules="ruleForm2">
        <!-- <el-form-item
          label="角色编号"
          :label-width="formLabelWidth"
          prop="roleNo"
          :rules="[{required: true, message: '请输入角色编号', trigger: 'blur' },
          { type:'number', message: '格式错误', trigger: 'blur'}]"
        >
          <el-input width="300px" v-model="roleForm.roleNo" placeholder="请输入角色编号"></el-input>
        </el-form-item>-->
        <input type="hidden" :name="roleForm.roleNo">
        <el-form-item label="角色名称" :label-width="formLabelWidth" prop="roleName">
          <el-input width="300px" v-model="roleForm.roleName" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" :label-width="formLabelWidth" prop="roleDesc">
          <el-input width="300px" v-model="roleForm.roleDesc" placeholder="请输入角色描述"></el-input>
        </el-form-item>
        <el-form-item label="启用" :label-width="formLabelWidth">
          <el-radio-group v-model="roleForm.isEnabled">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :plain="true" @click="cancel">取 消</el-button>
        <!-- success  形参： 判断是编辑还是新增 -->
        <el-button :plain="true" type="primary" @click="submit(roleForm)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getRoleList,
  addRole,
  delRole,
  addRolePrivilage,
  editRole
} from "@/api/role";
import { getPrivilageList, getRolePrivilageInfo } from "@/api/privilage";
import Pagination from "@/components/Pagination";

export default {
  name: "roleMng",
  components: { Pagination },
  data() {
    return {
      title: "角色管理",
      buttonPrivilage: "新增",
      roleList: [],
      // multipleSelection: [], // 全选
      dialogFormVisible: false, // 弹出框的显示
      roleForm: {
        roleNo: "",
        roleName: "",
        roleDesc: "",
        isEnabled: "1"
      },
      ruleForm2: {
        roleName: [
          { required: true, message: "角色名称不能为空！", trigger: "blur" }
        ],
        roleDesc: [
          { required: true, message: "角色描述不能为空", trigger: "blur" }
        ]
      },
      formLabelWidth: "100px",
      formDialogWidth: "550px",
      searchText: "",
      count: 10,
      privilage: {
        list: [],
        name: [],
        roleId: ""
      },
      dialogConfigPrivilage: false,
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
    getPrivilageList().then(res => {
      this.privilage.list = res.data.data.list;
    });
  },
  methods: {
    getList() {
      this.listLoading = true;
      this.getListRole(this.listQuery);
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
      this.$confirm("此操作将永久删除该角色，是否继续？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delRole(row.roleNo)
            .then(res => {
              if (res.data.code == 200) {
                // 移除数组中的元素
                this.roleList.splice(index, 1);
                this.message('success','删除成功')
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
    addRole12() {
      this.dialogFormVisible = true;
    },
    // 编辑
    handleEdit(index, row) {
      this.dialogFormVisible = true;
      //  根据下标取出数据
      let that = this;
      this.roleList.forEach(function(value) {
        if (value.roleNo == row.roleNo) {
          that.roleForm = {
            roleNo: value.roleNo,
            roleName: value.roleName,
            roleDesc: value.roleDesc,
            isEnabled: value.isEnabled.toString()
          };
        }
      });
    },
    //  取消
    cancel() {
      this.dialogFormVisible = false;
      this.message('info','取消添加权限')
      this.clearForm('','','','1')
    },
    //  成功
    submit(data) {
      this.$refs.roleForm.validate(valid => {
        if (valid) {
          if (this.roleForm.roleNo == "") {
            //  将数据提交到数据库
            addRole(data).then(res => {
              if (res.data.code == 200) {
                this.getListRole(this.listQuery);
                this.dialogFormVisible = false;
                //  清空
                this.clearForm('','','','1')
                this.message('success','角色添加成功')
              }
            });
          } else {
            editRole(data)
              .then(res => {
                if (res.data.code == 200) {
                  this.getListRole(this.listQuery);
                  this.dialogFormVisible = false;
                  this.clearForm('','','','1')
                  this.message('success','角色修改成功')
                }
              })
              .catch(error => {
                console.log(error);
              });
          }
        }
      });
    },
    //  配置权限
    handleConfig(index, row) {
      // 取出角色对应的权限
      getRolePrivilageInfo(row.roleNo).then(res => {
        this.privilage.roleId = row.roleNo;
        var rolePrivilage =
          res.data.data.list == null ? "" : res.data.data.list;
        var roleArr = [];
        rolePrivilage.forEach(function(value) {
          roleArr.push(value.permissionNo);
        });
        this.privilage.name = roleArr;
      });
      // setTimeout
      this.dialogConfigPrivilage = true;
    },
    //  取消配置权限
    configRoleCancel() {
      this.dialogConfigPrivilage = false;
    },
 
    //  提交权限
    configRoleSubmit(data, row) {
      var role = {
        permissionNoArr: data.name,
        roleNo: this.privilage.roleId
      };
      addRolePrivilage(role).then(res => {
        if (res.data.code == 200) {
          this.dialogConfigPrivilage = false;
          this.message('success','权限添加成功')
        }
      });
    },
    /**
     * 解决重复代码
     */
    //  提示
    message(type, info) {
      this.$message({
        type: type,
        message: info
      });
    },
    //  赋值表单内容  roleForm
    clearForm(no, name, desc, isEnabled) {
      this.roleForm = {
        roleNo: no,
        roleName: name,
        roleDesc: desc,
        isEnabled: isEnabled
      };
    },
    //  获取角色列表
    getListRole(data) {
      getRoleList(data).then(res => {
        this.roleList = res.data.data.list;
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
  .el-dialog:nth-child(1) {
    .el-checkbox + .el-checkbox {
      margin-left: 68px;
    }
  }
}
</style>