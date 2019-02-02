<template>
    <div class="workflow-preview">
        <div class="form-box">
			<div class="form-container">
				<el-form ref="form" :model="form"  label-width="10%">

					<!-- <el-form-item label="申请类型" prop="flowType">
					  	<el-select  v-model="form.flowType" disabled placeholder="请选择">
							<el-option
								v-for="item in applyTypes"
								:key="item.value"
								:label="item.label"
								:value="item.value">
							</el-option>
						</el-select>
					</el-form-item>

					<el-form-item label="申请名称" prop="workName">
						<el-input  v-model="form.workName" disabled></el-input>
					</el-form-item>
					<el-form-item label="开始时间" prop="startTime">
						<el-date-picker
                            disabled
							v-model="form.startTime"
							type="datetime"
							value-format="yyyy-MM-dd HH:mm:ss"
							placeholder="选择日期时间">
						</el-date-picker>
					</el-form-item>

					<el-form-item label="结束时间" prop="endTime">
						<el-date-picker
                            disabled
							v-model="form.endTime"
							type="datetime"
							value-format="yyyy-MM-dd HH:mm:ss"
							placeholder="选择日期时间">
						</el-date-picker>
					</el-form-item>

					<el-form-item label="请假时间" prop="duration">
						<el-input-number  v-model="form.duration" :step="0.5" :min="0.5" :max="365" disabled></el-input-number>天
					</el-form-item>
					 -->

					<el-form-item label="图片" prop="proveImg">
						<el-upload
							:action="uploadConfig.action"
							list-type="picture-card"
							:file-list="uploadConfig.uploadList"
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
<!-- 
					<el-form-item label="申请内容" prop="content">
                        <el-input type="textarea"  v-model="form.content" disabled></el-input>
					</el-form-item>

					
					<el-form-item label="审批人"  prop="approver">
						<el-select v-model="form.approver" disabled placeholder="请选择">
							<el-option
								v-for="item in approves"
								:key="item.value"
								:label="item.label"
								:value="item.value">
							</el-option>
						</el-select>
					</el-form-item> -->
					
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

						<el-button type="primary" @click="onSubmit">保存</el-button>
					
                    </el-form-item>
				</el-form>
			</div>
		</div>
    </div>
</template>
<script>
import {saveAttendanceApply,getAttendanceApplyTypes,getApproves,showWorkflow,getUploadUrl,addWorkflowImage} from "@/api/workProcess"
import {formatTimeToStr} from "@/utils/tools"
export default {
    name: 'workflowPreview',
    props:['serialNumber','workflowInfo'],
    data(){
        return {
            form: {
				applicationTime: formatTimeToStr(new Date(),"yyyy-MM-dd hh:mm:ss"), // 申请时间
				approvalStatus: 0, // 审核状态 
				approver: 0, // 审核人 id
				content: "内容", 
				startTime: "",  
				endTime: "", // 
				duration:"", //请假时长
				flowType: "", // 请假类型  number
				proveImg: "string", //  图片
				workName: "" ,
				// copyPeople: '',
				// "applicant": 0,  // 申请人 no
				// "approvalTime": "2019-01-14T09:28:29.674Z", // 审核时间 no
				serialNumber: 0, // 流水号
            },
			uploadConfig:{
				header:{
					Authorization:this.$store.state.user.token
				},
				action:getUploadUrl(),
				uploadList:[],
				fileList:[],
			},
            applyTypes:[],
			approves:[],
			dialogImageUrl: '',
            dialogVisible: false,
        }
    },
    mounted(){

		let that = this;
		
		this.form = Object.assign({},this.workflowInfo)
		this.form.proveImg = this.form.proveImg.split("|");

		// 获取旧图片
		this.uploadConfig.uploadList = [];
		if(this.form.proveImg){
			this.form.proveImg.forEach((v)=>{
				if(v.trim()){
						that.uploadConfig.uploadList.push({url:"http://172.18.19.3:8066/"+v})

					}
				// that.uploadConfig.fileList.push({url:v})
			})
		}

		this.initView();

    },
    methods:{
        initView() {

            // // 请假信息
            // showWorkflow(this.serialNumber)
            // .then(res=>{
            //     if(res.data.code==200){
            //         this.workflowInfo = res.data.data;
            //     }else{
            //         this.$message.error(res.data.msg)
            //     }
            // })
            // .catch(error=>{
            //     this.$message.error(res.data.msg)
            // })

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
				if(res.data.code==200){
					this.approves = res.data.data.map(o=>{return{value:o.empNo, label:o.name}});
				}else{
					this.$message.error(this.data.msg)
				}
			})
			.catch(error=>{
				this.$message.error(error)
			})
		},
		onSubmit() {
			
			// 取出图片地址
			this.form.proveImg = this.uploadConfig.uploadList.map(function(item){ return item.url.replace(/http:\/\/172.18.19.3:8066\//,"") });
			this.form.proveImg = this.form.proveImg.join("|");
			console.log(this.form);
			
			// console.log(this.form.proveImg.join("|"))
			let query = {serialNumber:this.workflowInfo.serialNumber,proveImg:this.form.proveImg}
			console.log(query)
			addWorkflowImage( query )
			.then(res=>{
				if(res.data.code!=200){
					this.$message.error( res.data.msg );
				}else{
					this.$message({
						type: 'success',
						message: res.data.msg
					})
					
					this.$refs['form'].resetFields()
					this.$emit('callback',1)
				}
			})
			.catch(error=>{
				this.$message.error( error )
			})
		},
		handleChangeFlowType() {
			
		},
		handleRemove(file, fileList) {
			let that = this;

			// 删除
			this.uploadConfig.uploadList.forEach((v,k)=>{
				if(v.url==file.url){
					console.log("del============"+v.url)

					that.uploadConfig.uploadList.splice(k,1)

				}
			})
		},
		handlePictureCardPreview(file) {
			this.dialogImageUrl = file.url;
			this.dialogVisible = true;
			console.log(this.dialogImageUrl)
		},
		handleUploadSuccess(response,file,fileList) {
			// 添加新图片
			if(response.code==2000){
				this.uploadConfig.uploadList.push({url:"http://172.18.19.3:8066/"+response.data})
			}
		},
		handleUploadError(err,file,fileList) {

		}
    },
  	watch:{
		workflowInfo(){
			let that = this;
		
			this.form = Object.assign({},this.form,this.workflowInfo)
			this.form.proveImg = this.form.proveImg.split("|");
			
			// 获取旧图片
			this.uploadConfig.uploadList = [];
			if(this.form.proveImg){
				this.form.proveImg.forEach((v)=>{ 
					if(v.trim()){
						that.uploadConfig.uploadList.push({url:"http://172.18.19.3:8066/"+v})

					}

					// that.uploadConfig.fileList.push({url:v})
				})
			}
		}
	}
}
</script>
<style>
.workflow-preview {
    padding:20px;
}
.form-box {
	width:95%;
	margin:30px auto;
}
.form-container .tips {
	margin-left:10px;
	font-size:12px;
	color:rgb(243, 64, 64);
	line-height: normal;
	vertical-align: bottom;
}
</style>
