<template>
    <div class="form-container">
        <el-form ref="addApplyForm"  status-icon :model="form" :rules="rules" label-width="150px">

            <el-form-item label="请假类型" prop="flowType">
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
						:picker-options="startPickerConfig"
                        :change="changeDate()"
                        placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item prop="startDayStatus">
						<el-select v-model="form.startDayStatus" placeholder="选择时间">
                            <el-option label="08:30" value="上午"></el-option>
                            <el-option label="13:30" value="下午"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="11">
                    <el-form-item label="结束时间" prop="endDate">
                        <el-date-picker
                        v-model="form.endDate"
                        type="date"
                        value-format="yyyy-MM-dd"
						:picker-options="endPickerConfig"
                        :change="changeDate()"
                        placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                </el-col>

                <el-col :span="11">
                    <el-form-item prop="endDayStatus">
                            <el-select v-model="form.endDayStatus" placeholder="选择时间">
                            <el-option label="12:00" value="上午"></el-option>
                            <el-option label="18:00" value="下午"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
    
            <!-- <el-form-item label="请假时间" prop="duration">
                <el-input-number v-model="form.duration" :step="0.5" :min="0.5"></el-input-number>天
            </el-form-item> -->
            

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
</template>
<script>
import {saveAttendanceApply,getAttendanceApplyTypes,getApproves,getUploadUrl} from "@/api/workProcess"
import {formatTimeToStr} from "@/utils/tools"
import { mapGetters } from 'vuex'
export default {
	props:['date','startDayStatus'],
    data(){
		let that = this;
		return {
			form: {
				flowType: "", // 请假类型  number
				workName: "" ,
				startDate:"", // 临时使用的日期
				startTime: "",
				endDate:"",
				endTime: "",
				// duration:"", //请假时长
				proveImg: "string", //  图片
				content: "", 
				approver: "", // 审核人 id
				applicationTime: formatTimeToStr(new Date(),"yyyy-MM-dd hh:mm:ss"), // 申请时间
				approvalStatus: 0, // 审核状态 
				startDayStatus:"",
				endDayStatus:"",
				
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
			startPickerConfig:{
				// disabledDate(time) {
				// 	return time.getTime() < Date.now();
				// },
			},
			endPickerConfig:{
				// disabledDate(time) {

				// 	if(that.form.startDate==""){
				// 		return false;
				// 	}else{
				// 		console.log(time.getTime())

				// 		return time.getTime() < new Date(that.form.startDate).getTime();
				// 	}
				// }
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
				startDayStatus: [
					{required:true,message:"请选择开始时间",trigger:'blur'}
				],
				endDate: [
					{required:true,message:"请选择结束日期",trigger:'blur'}
				],
				endDayStatus: [
					{required:true,message:"请选择结束时间",trigger:'blur'}
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
			
			// 初始化传入的时间为开始时间
			this.form.startDate = this.date;
			this.form.startDayStatus = this.startDayStatus;
		}
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
			
			if(this.form.startDate && this.form.startDayStatus){
				let tmpDate = new Date(this.form.startDate);
				if(this.form.startDayStatus=="上午"){
					tmpDate.setHours(8)
					tmpDate.setMinutes(30)
				}else{
					tmpDate.setHours(13)
					tmpDate.setMinutes(30)	
				}
				
				this.form.startTime = formatTimeToStr(tmpDate,"yyyy-MM-dd hh:mm:ss");
			}

			if(this.form.endDate && this.form.endDayStatus){
				let tmpDate = new Date(this.form.endDate);
				if(this.form.endDayStatus=="上午"){
					tmpDate.setHours(12)
					tmpDate.setMinutes(0)
				}else{
					tmpDate.setHours(18)
					tmpDate.setMinutes(0)	
				}
				this.form.endTime = formatTimeToStr(tmpDate,"yyyy-MM-dd hh:mm:ss");
			}

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