<template>
	<div class="schedule">
		<div class="form-box">
			<div class="form-container">
				<el-form ref="form" :model="form" :rules="rules" label-width="210px">

					<el-form-item label="考勤参数设置名称">
						<el-input v-model="form.name" disabled></el-input>
					</el-form-item>

					<el-form-item label="上班时间">
						<el-col :span="11">
							<el-time-picker
								arrow-control
								:disabled="!isEdit"
								v-model="form.workingHours"
								value-format="HH:mm:ss"
								placeholder="上班时间">
							</el-time-picker>
						</el-col>
					</el-form-item>

					<el-form-item label="下班时间">
						<el-col :span="11">
							<el-time-picker
								arrow-control
								:disabled="!isEdit"
								v-model="form.offHours"
								value-format="HH:mm:ss"
								placeholder="下班时间">
							</el-time-picker>
						</el-col>
					</el-form-item>
					<el-form-item label="迟到时间范围">
							<el-input-number :disabled="!isEdit" v-model="form.permitLateTime" label="允许迟到时间"></el-input-number>
							<!-- <span class="tips">x分钟内算为迟到,超出x分钟后算为旷工。</span> -->
					</el-form-item>

					
					<el-form-item label="早退时间范围">
							<el-input-number :disabled="!isEdit" v-model="form.permitOffLimit" label="早退时间范围"></el-input-number>
							<!-- <span class="tips">x分钟内算为早退,超出x分钟后算为旷工。</span> -->
					</el-form-item>
					
					<el-form-item label="旷工时间范围">
							<el-input-number :disabled="!isEdit" v-model="form.permitOnLimit" label="旷工时间范围"></el-input-number>
							<!-- <span class="tips">迟到x分钟后算为旷工。</span> -->
					</el-form-item>
					
					<el-form-item label="允许忘记打卡次数">
							<el-input-number :disabled="!isEdit" v-model="form.permitForgetClock" label="允许忘记打卡次数"></el-input-number>
					</el-form-item>

					<el-form-item label="允许迟到规定分钟的次数">
							<el-input-number :disabled="!isEdit" v-model="form.permitLateOccasion" label="允许迟到规定分钟的次数"></el-input-number>
					</el-form-item>
					
					<el-form-item>
						<el-button type="warning" @click="onSubmit" v-if="isEdit" >立即保存</el-button>
						<el-button type="danger" @click="changeEditStatus" v-if="isEdit" >取消修改</el-button>
						
						<el-button type="primary" @click="changeEditStatus" v-if="!isEdit" >我要修改</el-button>

					</el-form-item>
				</el-form>
			</div>
		</div>
	</div>
</template>

<script>
import {saveSchedule,getSchedule} from "@/api/attendance"
export default {
    data(){
		return {
			form: {
				name: '',
				workingHours: '',
				offHours: '',
				permitLateTime: '',
				permitOffLimit: '',
				permitOnLimit: '',
				permitForgetClock: '',
				permitLateOccasion: '',
			},
			rules: {
				name: [
					{ required: true, message: '请输入考勤参数名称!', trigger: 'blur' },
				],
				workingHours: [
					{ required: true, message: '上班时间不能为空!', trigger: 'blur' },
				],
				offHours: [
					{ required: true, message: '下班时间不能为空!', trigger: 'blur' },
				],
				permitLateTime: [
					{ required: true, message: '迟到时间范围不能为空!', trigger: 'blur' },					
				],
				permitOffLimit: [
					{ required: true, message: '早退时间范围不能为空!', trigger: 'blur' },
				],
				permitOnLimit: [
					{ required: true, message: '旷工时间范围不能为空!', trigger: 'blur' },
				],
				permitForgetClock: [
					{ required: true, message: '允许忘记打卡次数不能为空!', trigger: 'blur' },
				],
				permitLateOccasion: [
					{ required: true, message: '允许迟到规定分钟的次数不能为空!', trigger: 'blur' },
				],
			},
			isEdit:false,
		}
	},
	mounted:function(){
		getSchedule()
		.then(res=>{
			// console.log(res)
			if(res.data.code==200){
				this.form = res.data.data;
			}else{
				this.$message.error(res.data.msg)
			}

		})
		.catch(error=>{
			this.$message.error(error)
		})
	},
	methods:{
		onSubmit() {
			this.$refs['form'].validate((valid) => {
				if (valid) {
					saveSchedule( this.form )
					.then(res=>{
						if(res.data.code!=200){
							this.$message.error( res.data.msg );
						}else{
							this.$message({
								type: 'success',
								message: res.data.msg
							})
							this.isEdit = false;
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
		changeEditStatus(){
			this.isEdit = !this.isEdit;
		}
	}
}
</script>
<style>
.schedule {
	padding:20px;
}
.form-box {
	width:95%;
	margin:30px auto;
}
.form-container {
	width:700px;
}
.form-container .tips {
	margin-left:10px;
	font-size:12px;
	color:rgb(243, 64, 64);
	line-height: normal;
	vertical-align: bottom;
}
</style>
