<template>
	<div class="form-container">
		<el-form ref="addApplyForm"  status-icon :model="form" :rules="rules" label-width="150px">
			<el-form-item label="补卡时间" prop="reissueCardTime">
					<el-select v-model="form.reissueCardTime" disabled placeholder="请选择补卡时间">
						<el-option :label="form.reissueCardTime" :value="form.reissueCardTime">
					</el-option>
				</el-select>
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

			<el-form-item>
				<el-button type="primary" @click="onSubmit">立即创建</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
import {saveCardWorkflowApply,getApproves,getUploadUrl} from "@/api/workProcess"
import {formatTimeToStr} from "@/utils/tools"
import { mapGetters } from 'vuex'
export default {
	props:['date','dayStatus'],
    data(){
		return {
			form: {
				reissueCardTime: "",
				proveImg: "string", //  图片
				content: "", 
				approver: "", // 审核人 id
				applicationTime: formatTimeToStr(new Date(),"yyyy-MM-dd hh:mm:ss"), // 申请时间
				approvalStatus: 0, // 审核状态 
				// copyPeople: '',
				// "applicant": 0,  // 申请人 no
				// "approvalTime": "2019-01-14T09:28:29.674Z", // 审核时间 no
				// serialNumber: 0, // 流水号


				// "applicant": 0,
				// "applicantName": "string",
				// "approvalTime": "string",
				// "approverName": "string",
				// "serialNumber": 0
			},
			patchs:[
				{value:"2019-01-21 08:30:00",label:"2019-01-21 08:30:00"},
				{value:"2019-01-25 18:00:00",label:"2019-01-25 18:00:00"},
				{value:"2019-01-26 18:00:00",label:"2019-01-26 18:00:00"},
				{value:"2019-01-28 18:00:00",label:"2019-01-28 18:00:00"},
				{value:"2019-01-29 08:30:00",label:"2019-01-29 08:30:00"},
				{value:"2019-01-30 08:30:00",label:"2019-01-30 08:30:00"},
			],
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
				reissueCardTime: [
					{required:true,message:"请选择补卡时间",trigger:'blur'}
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
		if(this.date){
			let patchDate = new Date(this.date);
			if(this.dayStatus=="上午"){

				patchDate.setHours(8)
				patchDate.setMinutes(30)
			}else{
				patchDate.setHours(18)
				patchDate.setMinutes(0)
			}
			console.log(formatTimeToStr(patchDate ,"yyyy-MM-dd hh:mm:ss"));
			this.form.reissueCardTime = formatTimeToStr(patchDate,"yyyy-MM-dd hh:mm:ss")
			
		}
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
					saveCardWorkflowApply( this.form )
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
		
		}
	}
}
</script>
<style>
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
