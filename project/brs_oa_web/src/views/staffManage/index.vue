<template>
  <div class="user-list">

    <div class="table-bar">
      <el-row>
        <el-col :span="12">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-plus"
            @click="userAddLayer = true"
          >新增</el-button>
        </el-col>
      </el-row>
    </div>
    <el-table
      :header-cell-style="{background:'#eef1f6'}"
      border
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="date"
        label="日期"
        width="180"/>
      <el-table-column
        prop="name"
        label="姓名"
        width="180"/>
      <el-table-column
        prop="address"
        label="地址"/>
      <el-table-column
        width="240"
        label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-setting"
            @click="roleSetting" >配置角色</el-button>
          <el-button
            size="mini"
            type="danger"
            icon="el-icon-delete"
            @click="roleDelete">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <!-- 配置角色弹出层 -->
    <el-dialog
      :visible.sync="roleEditLayer"
      :before-close="handleClose"
      width="30%"
      title="配置用户角色"
      @open="dialogOpen"
      @close="dialogClose">
      <el-tree
        ref="tree"
        :data="roles"
        :default-expanded-keys="[2]"
        :default-checked-keys="[2]"
        :props="defaultProps"
        show-checkbox
        node-key="id"/>

      <span slot="footer" class="dialog-footer">
        <el-button @click="roleEditLayer = false">取 消</el-button>
        <el-button type="primary" @click="roleSave">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 增加用户弹出层 -->
    <el-dialog :visible.sync="userAddLayer" title="收货地址">
      <el-form :model="form">
        <el-form-item :label-width="formLabelWidth" label="活动名称">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth" label="活动区域">
          <el-select v-model="form.region" placeholder="请选择活动区域">
            <el-option label="区域一" value="shanghai"/>
            <el-option label="区域二" value="beijing"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userAddLayer = false">取 消</el-button>
        <el-button type="primary" @click="userAddLayer = false">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data: () => {
    return {
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }
      ],
      roles: [
        {
          id: 1,
          name: '超级管理员',
          is: true,
          children: []
        },
        {
          id: 2,
          name: '客服主管',
          is: false,
          children: [
            { id: 20, name: '售前客服', children: [], is: true },
            { id: 21, name: '售后客服', children: [], is: true }
          ]
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      roleEditLayer: false,
      userAddLayer: false,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      formLabelWidth: '120px'

    }
  },
  computed: {

  },
  mounted() {

  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    dialogOpen() {
      console.log('打开dialog!')
    },
    dialogClose() {
      console.log('关闭dialog!')
    },
    roleSetting() {
      this.roleEditLayer = true
      console.log('配置角色!')
    },
    roleSave() {
      // 获取所有选中的角色id
      // this.$refs.tree.getCheckedKeys()
      console.log(this.$refs.tree.getCheckedKeys())

      console.log('保存修改!')
    },
    roleDelete() {
      console.log('删除用户!')
    }
  }
}
</script>

<style>
.user-list {
    padding:20px;
    background-color: #fff;
}
.table-bar {
    padding:20px 0;
}
.tree {
    border:1px solid #ccc;
    line-height:22px;
}
.tree ul{
    padding-left:20px;
    list-style: none;

}
</style>
