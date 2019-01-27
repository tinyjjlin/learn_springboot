<template>
	<div class="attendance-apply">
		<div class="form-box">
			<div class="form-container">
				<el-form ref="addApplyForm"  status-icon :model="form" :rules="rules" label-width="150px">

					<el-form-item label="申请名称" prop="workName">
						<el-input v-model="form.workName"></el-input>
					</el-form-item>
				
					<el-row>
						<el-col :span="11">
							<el-form-item label="补卡时间" prop="startDate">
								<el-date-picker
								v-model="form.startDate"
								type="date"
								value-format="yyyy-MM-dd"
								:change="changeDate()"
								placeholder="选择日期">
								</el-date-picker>
							</el-form-item>
						</el-col>
	
						<el-col :span="12">
							<el-form-item prop="dayStatus">
								 <el-select v-model="form.dayStatus" placeholder="选择时间">
									<el-option label="上午" value="上午"></el-option>
									<el-option label="下午" value="下午"></el-option>
								</el-select>
							</el-form-item>
						</el-col>
					</el-row>

					<el-form-item label="申请内容" prop="content">
							<el-input type="textarea" v-model="form.content"></el-input>
					</el-form-item>

					
					<el-form-item label="审批人" prop="approver">
						<el-select v-model="form.approver" placeholder="请选择">
							<el-option
								v-for="item in approves"
								:key="item.value"
								:label="item.label"
								:value="item.value">
							</el-option>
						</el-select>
					</el-form-item>

					<el-form-item>
						<el-button type="primary" @click="onSubmit">立即创建</el-button>
					</el-form-item>
				</el-form>
			</div>
		</div>
	</div>
</template>
<script>
import {saveAttendanceApply,getApproves,getUploadUrl} from "@/api/workProcess"
import {formatTimeToStr} from "@/utils/tools"
import { mapGetters } from 'vuex'
export default {
    data(){
		return {
			form: {
				flowType: "", // 请假类型  number
				workName: "" ,
				startDate:"", // 临时使用的日期
				startTime: "",
				endTime: "",
				duration:"", //请假时长
				proveImg: "string", //  图片
				content: "", 
				approver: "", // 审核人 id
				applicationTime: formatTimeToStr(new Date(),"yyyy-MM-dd hh:mm:ss"), // 申请时间
				approvalStatus: 0, // 审核状态 
				dayStatus:"",
				// copyPeople: '',
				// "applicant": 0,  // 申请人 no
				// "approvalTime": "2019-01-14T09:28:29.674Z", // 审核时间 no
				// serialNumber: 0, // 流水号
			},
			uploadConfig:{
				header:{
					Authorization:this.$store.state.user.token
				},
				action:getUploadUrl(),
				fileList:[],
				uploadList:[]
			},
			approves:[],
			dialogImageUrl: '',
			dialogVisible: false,
			header:{
				Authorization:this.$store.state.user.token
			},
			rules:{
				flowType: [
					{required:true,message:"请选择请假类型",trigger:"change"},
				],
				workName: [
					{required:true,message:"请输入申请名称",trigger:"blur"},
				],
				
				startDate: [
					{required:true,message:"请选择开始日期",trigger:'blur'}
				],
				dayStatus: [
					{required:true,message:"请选择开始时间",trigger:'blur'}
				],
				endTime: [
					{required:true,message:"请选择结束时间",trigger:'blur'}
				],
				duration: [
					{required:true,message:"请输入请假时间",trigger:'blur'}	
				],
				content: [
					{required:true,message:"请输入申请内容",trigger:'blur'}	
				],
				approver: [
					{required:true,message:"请选择审批人",trigger:'change'}	
				],

			},

			
		}
	},
	mounted(){
		this.initView();
	},
	methods:{
		initView() {

			// 获取审批人
			getApproves()
			.then(res=>{
				let that = this;
				res.data.data.forEach((v,k)=>{
					if(v.empNo!=that.$store.state.user.empNo){
						that.approves.push({value:v.empNo, label:v.name})
					}
				})
			})
			.catch(error=>{
				this.$message.error(error)
			})
		},
		onSubmit() {
			
			this.$refs['addApplyForm'].validate((valid) => {
				if (valid) {

					this.form.proveImg = this.uploadConfig.uploadList.join(";");
					saveAttendanceApply( this.form )
					.then(res=>{
						if(res.data.code!=200){
							this.$message.error( res.data.msg );
						}else{
							this.$message({
								type: 'success',
								message: res.data.msg
							})
							
							this.$refs['addApplyForm'].resetFields()
							this.$emit('callback',1)
						}
					})
					.catch(error=>{
						this.$message.error( error )
					})
				} else {
					console.log('error submit!!');
					return false;
				}
			});
		},

		changeDate(){
		
		}
	}
}
</script>
<style>
.patchcard-apply {
	padding:20px;
}
.form-box {
	width:600px;
	margin:30px auto;
}
.form-container .tips {
	margin-left:10px;
	font-size:12px;
	color:rgb(243, 64, 64);
	line-height: normal;
	vertical-align: bottom;
}
.form-box .el-form-item__error{
	min-width:150px;
}
</style>
