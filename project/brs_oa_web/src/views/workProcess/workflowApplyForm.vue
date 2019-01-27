<template>
	<div class="attendance-apply">
		<div class="form-box">
			<div class="form-container">
				<el-form ref="addApplyForm"  status-icon :model="form" :rules="rules" label-width="150px">

					<el-form-item label="申请类型" prop="flowType">
					  	<el-select v-model="form.flowType" placeholder="请选择">
							<el-option
								v-for="item in applyTypes"
								:key="item.value"
								:label="item.label"
								:value="item.value">
							</el-option>
						</el-select>
					</el-form-item>

					<el-form-item label="申请名称" prop="workName">
						<el-input v-model="form.workName"></el-input>
					</el-form-item>
				
					<el-row>
						<el-col :span="11">
							<el-form-item label="开始时间" prop="startDate">
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
					<!-- <el-row>
						<el-col :span="11">
							<el-form-item label="结束时间" prop="endDate">
								<el-date-picker
								v-model="form.endDate"
								type="date"
								value-format="yyyy-MM-dd"
								:change="changeStartDate()"
								placeholder="选择日期">
								</el-date-picker>
							</el-form-item>
						</el-col>
	
						<el-col :span="11">
							<el-form-item  prop="endTime">
								<el-time-select
								placeholder="选择时间"
								v-model="form.endTime"
								:picker-options="{
									start: '08:30',
									step: '05:00',
									end: '13:30'
								}">
								</el-time-select>
							</el-form-item>
						</el-col>
					</el-row> -->
			
					<el-form-item label="请假时间" prop="duration">
						<el-input-number v-model="form.duration" :step="0.5" :min="0.5"></el-input-number>天
					</el-form-item>
					

					<el-form-item label="图片" prop="proveImg">
						<el-upload
							:action="uploadConfig.action"
							list-type="picture-card"
							:headers="header"
							:file-list="uploadConfig.fileList"
							:on-success="handleUploadSuccess"
							:on-error	="handleUploadError"
							:on-preview="handlePictureCardPreview"
							:on-remove="handleRemove">
							<i class="el-icon-plus"></i>
						</el-upload>
						<el-dialog :visible.sync="dialogVisible">
							<img width="100%" :src="dialogImageUrl" alt="">
						</el-dialog>
					</el-form-item>

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
					
					<!-- <el-form-item label="抄送人" prop="copyPeople">
							<el-select v-model="form.copyPeople" multiple placeholder="请选择">
								<el-option
									v-for="item in approves"
									:key="item.value"
									:label="item.label"
									:value="item.value">
								</el-option>
							</el-select>
							<span class="tips">审批通过后,通知抄送人</span>
					</el-form-item> -->

					<el-form-item>
						<el-button type="primary" @click="onSubmit">立即创建</el-button>
					</el-form-item>
				</el-form>
			</div>
		</div>
	</div>
</template>
<script>
import {saveAttendanceApply,getAttendanceApplyTypes,getApproves,getUploadUrl} from "@/api/workProcess"
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
			applyTypes:[],
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
			// 获取请假类型
			getAttendanceApplyTypes()
			.then(res=>{
				if(res.data.code==200){
					
					this.applyTypes = res.data.data.map(o=>{return{value:o.code, label:o.name}});
					
				}else{
					this.$message.error(this.data.msg)
				}
			})
			.catch(error=>{
				this.$message.error(error)
			})

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
		handleChangeFlowType() {
			
		},
		handleRemove(file, fileList) {
			let that = this;
			this.uploadConfig.uploadList.forEach((v,k)=>{
				if(v==file.response.data){
					console.log("del============"+v.url)

					that.uploadConfig.uploadList.splice(k,1)

				}
			})
			console.log(this.uploadConfig.uploadList.join(";"))
		},
		handlePictureCardPreview(file) {
			this.dialogImageUrl = file.url;
			this.dialogVisible = true;
			console.log(this.dialogImageUrl)
		},
		handleUploadSuccess(response,file,fileList) {
			
			if(response.code==2000){
				this.uploadConfig.uploadList.push(response.data)
			}

		},
		handleUploadError(err,file,fileList) {
			this.$message.error(err)
		},
		changeDate(){
			if(this.form.startDate && this.form.dayStatus && this.form.duration){

				let startDate = new Date(this.form.startDate);
				let endDate = new Date(this.form.startDate);
				let num = 0;
				let decimal = this.form.duration*10%10;
				console.log(decimal);

					
				if(this.form.dayStatus=="上午"){
					startDate.setHours(8)
					startDate.setMinutes(30)
				}else{
					startDate.setHours(12)
					startDate.setMinutes(0)
				}

				// 是否有半天
				if(decimal>1){
					
					// 我从上午开始请假 半天 下午来上班
					if(this.form.dayStatus=="上午"){

						endDate.setHours(12)
						endDate.setMinutes(0)
						
					}else{
						// 我从下午开始请假 半天 明天上午来上班
						endDate.setHours(18)
						endDate.setMinutes(0)
						
					}
	
				}else{
					console.log(this.form.dayStatus+"============")
					num--;
					// 我从上午开始请假 明天上午来上班
					if(this.form.dayStatus=="上午"){
						// 结束
						endDate.setHours(18)
						endDate.setMinutes(0)

					}else{
						// 我从下午开始请假 明天下午来上班
						// 结束
						endDate.setHours(12)
						endDate.setMinutes(0)

					}
				}
				endDate.setDate(endDate.getDate() + Math.floor(this.form.duration*1+num));
				// console.log(formatTimeToStr(date,"yyyy-MM-dd hh:mm:ss"))
				this.form.endTime = formatTimeToStr(endDate,"yyyy-MM-dd hh:mm:ss");
				this.form.startTime = formatTimeToStr(startDate,"yyyy-MM-dd hh:mm:ss");
			}
		}
	}
}
</script>
<style>
.attendance-apply {
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
