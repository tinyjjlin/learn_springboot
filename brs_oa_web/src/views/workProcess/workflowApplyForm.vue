<template>
	<div class="attendance-apply">
		<div class="form-box">
			<workflow-apply-form ref="addFormBox" @callback="submitCallback"></workflow-apply-form>
		</div>
	</div>
</template>
<script>
import workflowApplyForm from "./components/workflowApplyForm"
export default {
    components:{
		workflowApplyForm
	},
	methods:{
		submitCallback(){
			this.$emit("callback",1)
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
