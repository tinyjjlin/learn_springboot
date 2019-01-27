<template>
  <div class="organization-manage">
    <div class="tree">
      <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
      <!-- el-tree  树形控件 -->
      <!-- 
        data      展示数据
        props     配置选项 
        default-expand-all       是否默认展开所有节点    boolean  默认值为false
        filter-node-method    对树节点进行筛选时执行的方法，返回 false 则表示这个节点会被隐藏
      -->
      <el-tree
        class="filter-tree"
        :data="organizationList"
        :props="defaultProps"
        :filter-node-method="filterNode"
        :expand-on-click-node="false"
        @current-change="getDeptInfo"
        ref="tree2"
      ></el-tree>
    </div>
    <div class="line"></div>
    <div class="main">
      <div class="header">
        <div>
          <img src="../../assets/images/organization-top.png" alt>
        </div>
        <div></div>
        <div>
          <h4>企业</h4>
          <span>企业</span>
        </div>
      </div>
      <div class="content">
        <div class="center">
          <div id="content">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="部门信息" name="first">
                <ul>
                  <li>
                    <span>部门名称：</span>
                    <div>{{ deptInfo.name }}</div>
                  </li>
                  <li>
                    <span>管理者：</span>
                    <div>{{ deptInfo.manage }}</div>
                  </li>
                </ul>
              </el-tab-pane>
              <!-- <el-tab-pane label="组织成员" name="second">
                组织成员
              </el-tab-pane>
              <el-tab-pane label="组织职务" name="third">
                组织职务
              </el-tab-pane>
              <el-tab-pane label="组织属性" name="fourth">
                组织属性
              </el-tab-pane>-->
            </el-tabs>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getDepartmentList } from "@/api/staff";

export default {
  data() {
    return {
      filterText: "",
      organizationList: [],
      defaultProps: {
        children: "children",
        label: "deptName",
      },      
      deptInfo: {
        name: "",
        manage: ""
      },
      activeName: "first",

    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val);
    }
  },
  created() {
    getDepartmentList().then(res => {

      // var list = [
      //   {deptId:1,deptName:"总经理",deptNo:1001,parentNo:0},
      //   {deptId:2,deptName:"财务部",deptNo:1002,parentNo:1001},
      //   {deptId:3,deptName:"编辑部",deptNo:1003,parentNo:1001},
      //   {deptId:4,deptName:"it部",deptNo:1004,parentNo:1001},
      //   {deptId:5,deptName:"信息安全部",deptNo:1005,parentNo:1004},
      //   {deptId:6,deptName:"信息安全研发部",deptNo:1006,parentNo:1005},
      // ]
      
      if(res.data.code==200){
        
        // 获取树形列表
        this.organizationList = this.tree(res.data.data,0);

      }else{
        this.$message.error(res.data.code)
      }

      // 部门默认显示信息
      if(this.organizationList[0]){
        this.deptInfo.manage = this.organizationList[0].deptNo;
        this.deptInfo.name = this.organizationList[0].deptName;
      }
    });
  },
  methods: {
    // 获取树形列表
    tree(data){
      let arr = {};
      data.forEach((v,k)=>{
        arr[v.deptNo] = v;
      })
      
      var val = [];
      data.forEach((item)=>{
        var parent = arr[item.parentNo]
        if(parent){
          (parent.children || (parent.children=[]) ).push(item)
        }else{
          val.push(item)
        }
      })
      return val;
      
    },
    // 节点过滤,返回假则隐藏节点
    filterNode(value, data) {
      console.log(value,data)
      if (!value) return true;
      
      return data.deptName.indexOf(value) !== -1;
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    // 修改显示部门信息
    getDeptInfo(data, node) {
      this.deptInfo.name = data.deptName;
      this.deptInfo.manage = data.deptNo;
    }
  }
};
</script>

<style lang="scss" scoped>
.organization-manage {
  width: 95%;
  height: 780px;
  border: 2px solid #dddddd;
  margin: 30px auto;
  overflow: hidden;
  .tree {
    width: 15%;
    display: inline-block;
    height: 100%;
    padding: 20px;
    float: left;
    .el-input {
      margin-bottom: 25px;
    }
  }
  .line {
    display: inline-block;
    height: 100%;
    width: 1px;
    border: 0.5px solid #cccccc;
    float: left;
  }
  .main {
    display: inline-block;
    background: #ffffff;
    width: 84%;
    height: 100%;
    float: right;
    margin-right: 8px;
    .header {
      height: 98px;
      width: 100%;
      background: url(../../assets/images/organization-background.png) no-repeat
        center;
      padding: 10px 0;
      overflow: hidden;
      div {
        display: inline-block;
      }
      div:nth-child(1) {
        width: 80px;
        height: 80px;
        background: #ffffff;
        margin-left: 300px;
        border-radius: 40px;
        float: left;
        img {
          width: 78px;
          height: 78px;
        }
      }
      div:nth-child(2) {
        width: 1px;
        height: 100%;
        border: 0.5px solid #cccccc;
        margin: 0 20px;
        float: left;
      }
      div:nth-child(3) {
        float: left;
        h4 {
          height: 35px;
          font-size: 18px;
          line-height: 45px;
          font-weight: bold;
          text-align: left;
          color: rgb(102, 102, 102);
          margin: 0;
        }
        span {
          height: 35px;
          font-size: 14px;
          line-height: 20px;
          margin: 5px auto auto;
          color: rgb(136, 136, 136);
          text-align: left;
        }
      }
    }
    .content {
      margin-top: 10px;
      width: 100%;
      height: 58%;
      background: aliceblue;
      .center {
        width: 900px;
        height: 100%;
        margin: auto;
        #content {
          width: 820px;
          height: 100%;
          margin: auto;
          padding-top: 10px;
          /deep/.el-tabs {
            .el-tabs__nav-scroll {
              overflow: hidden;
              width: 560px;
              margin: 0 auto;
              .el-tabs__item {
                font-size: 16px;
                padding: 0 26px;
              }
              .el-tabs__active-bar {
                width: 134px;
                background-color: rgb(59, 116, 180);
              }
              .el-tabs__item.is-active {
                color: rgb(59, 116, 180);
              }
            }
            .el-tab-pane {
              ul {
                li {
                  list-style: none;
                  padding: 16px 0px;
                  span {
                    display: inline-block;
                    width: 11%;
                  }
                  div {
                    display: inline-block;
                    width: 88%;
                    border-bottom: 1px solid #0f0f0f;
                    height: 20px;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>