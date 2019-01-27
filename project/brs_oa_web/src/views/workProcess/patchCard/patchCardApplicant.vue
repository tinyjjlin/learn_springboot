<template>
	<div class="patchcard-apply-list">
		
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
						@change="changeDate">
					</el-date-picker>
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
			<el-table
			:header-cell-style="{background:'#eef1f6'}"
			border
			:data="list"
			style="width: 100%">
				<el-table-column
				v-for="(v,k) in tableHead"
				:key="k"
				:prop="v.prop"
				:label="v.label"
				:width="v.width">
				</el-table-column>


				<el-table-column label="状态" width="100" prop="approvalStatus">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.approvalStatus==0">等待审批</el-tag>		
						<el-tag v-if="scope.row.approvalStatus==1" type="success">已通过</el-tag>
						<el-tag v-if="scope.row.approvalStatus==2" type="danger">未通过</el-tag>
					</template>
				</el-table-column>
			</el-table>
			
			<div class="paginate">
				<pagination v-show="paginate.total>0" :total="paginate.total" :page.sync="paginate.currentPage" :limit.sync="paginate.limit" :layout="paginate.layout" @pagination="changePage" />
			</div>
		</div>
		
		<el-dialog title="新增申请" :visible.sync="workflowInsert" :close="resetForm()">
			<patch-Card-apply-form ref=addFormBox @callback="submitCallback"></patch-Card-apply-form >
		</el-dialog>

		
	</div>
</template>
<script>
import pagination from "@/components/Pagination";
import patchCardApplyForm from "./patchCardApplyForm";

import {
  getWorkflowApplicant,
  getAttendanceApplyTypes
} from "@/api/workProcess";
export default {
	components:{
		pagination,patchCardApplyForm
	},
	data(){
		return {
			// config
			tableHead:[
				{prop:"serialNumber",label:"编号",width:100},
				{prop:"workName",label:"申请名称",width:200},
				{prop:"startTime",label:"开始时间",width:200},
				{prop:"endTime",label:"结束时间",width:200},
				{prop:"duration",label:"申请时长",width:100},
				{prop:"content",label:"申请内容"},
				// {prop:"applicationTime",label:"申请时间",width:200},
				{prop:"approverName",label:"审批人",width:200},
				// {prop:"copyPeople",label:"抄送人",width:200},
				// {prop:"flowType",label:"申请类型",width:150},
			],
			paginate:{
				limit:10,
				total:0,
				currentPage:1,
				background:true,
				layout:"prev, pager, next"
			},
			datePickerConfig:{
				disabledDate(time) {
				return time.getTime() > Date.now();
				},
			},
			list:[],
			query:{
				selectedDate:"",
				approvalStatus:"",
			},
			workflowInsert:false,
		}
	},
	mounted(){
		this.initView();

	},
  	methods: {
		initView: function() {
		this.query.limit = this.paginate.limit;
		this.query.page = this.paginate.currentPage;
		getWorkflowApplicant(this.query)
			.then(res => {
				if (res.data.code == 200) {
					// 分页参数
					this.paginate.limit = res.data.data.pageSize;
					this.paginate.total = res.data.data.total;
					this.paginate.currentPage = res.data.data.pageNum;

					this.list = res.data.data.list;
					console.log(this.list)
					// if(this.list.length){
					// 	console.log(this.list.length)
					// }else{
					// 	console.log("no")
					// }
				}else{
					this.$message.error(res.data.msg);
				}
			})
			.catch(error=>{
				this.$message.error(res.data.msg)
			})
				

		},
		changeDate:function(newDate){
			this.query.selectedDate = newDate;
		},
		changePage:function(newPage){
			this.initView()
			console.log(newPage)
        },
        addApply:function(){
            this.workflowInsert = true;
        },
		search:function(){
			this.initView();
		},
		submitCallback:function(){
			this.initView()
			this.workflowInsert = false;
		},
		resetForm:function(){
		
			this.$nextTick(()=>{
				
				if(this.$refs.addFormBox){
					this.$refs.addFormBox.$refs.addApplyForm.resetFields()
					this.$refs.addFormBox.uploadConfig.fileList = [];
					this.$refs.addFormBox.uploadConfig.uploadList = [];
				}
			})
		}
	}
}
</script>
<style>
.patchcard-apply-list {
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
