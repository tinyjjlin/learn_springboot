<template>
    <div class="department-list">
        <div class="title">部门管理</div>
        <div class="controller-bar">
            <!-- <el-button type="primary">添加顶级部门</el-button> -->
        </div>
        <div class="main">
            <el-tree
                class="filter-tree"
                :data="organizationList"
                :props="defaultProps"
                default-expand-all	
                :filter-node-method="filterNode"
                :expand-on-click-node="false"
                ref="tree2">
                <div class="custom-tree-node" slot-scope="{ node, data }">
                    <span class="label">{{ node.label }}</span>
                    <span class="btns">
                    <el-button
                        type="primary"
                        size="mini"
                        @click="() => append(data)">
                        添加
                    </el-button>
                    <el-button
                        type="danger"
                        size="mini"
                        @click="() => remove(node, data)">
                        删除
                    </el-button>
                    </span>
                </div>
            </el-tree>
        </div>

    </div>
</template>
<script>
import { getDepartmentList } from "@/api/staff";
import {saveDepartment,deleteDepartmentById} from "@/api/department"
export default {
    data(){
        return {
            form:{
                deptName:"",
                parentNo:"",
            },
            filterText: "",
            organizationList: [],
            defaultProps: {
                children: "children",
                label: "deptName",
            },
            isShowAddForm:false,
        }
    },
    created(){
        this.initView()
    },
    methods:{
        initView(){
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

            });
        },

        append(data) {

            this.$prompt('请输入部门名称', '添加部门', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({ value }) => {
                this.form.parentNo = data.deptNo;
                this.form.deptName = value;

                saveDepartment(this.form)
                .then(res=>{
                    if(res.data.code==200){
                        this.$message({
                            type:"success",
                            message:res.data.msg
                        })
                        
                        // 将节点添加到tree中
                        const newChild = {  deptNo: res.data.data, deptName: this.form.deptName, parentNo: this.form.parentNo, children: [] };
                        if (!data.children) {
                            this.$set(data, 'children', []);
                        }
                        data.children.push(newChild);
                        this.isShowAddForm = false;
                        // this.initView();

                    }else{
                        this.$message.error(res.data.msg)
                    }

                })
                .catch(error=>{
                    this.$message.error(error)
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '取消添加'
                });       
            });


        },

        remove(node, data) {
            this.$confirm('此操作将永久删除该部门及所有下属部门, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {

                deleteDepartmentById(data.deptNo)
                .then(res=>{
                    if(res.data.code==200){
                        this.$message({
                            type:"success",
                            message:res.data.msg
                        })
                        this.initView()

                    }else{
                        this.$message.error(res.data.msg)
                    }
                })
                .catch(error=>{
                    this.$message.error(error)
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });

            
        },
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
    },
    watch: {
        filterText(val) {
            this.$refs.tree2.filter(val);
        }
    },
}
</script>

<style>

.department-list {
    padding:20px;
    background-color: #fff;
}
.department-list .title {
    padding:20px 100px;
    font-size:22px;
}
.department-list .controller-bar {
    padding:0px 100px;
    width:80%;
    display:flex;
    justify-content: flex-end;
}
.department-list .main {
    padding:20px 100px;
    width:80%;
}
.department-list .el-tree-node__content {
    height:35px;
}
.department-list .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 16px;
    padding-right: 8px;
  }

</style>

