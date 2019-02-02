<template>
	<div class="attendance-apply">
		<div class="form-box">
			<patch-card-apply-form></patch-card-apply-form>
		</div>
	</div>
</template>
<script>
import patchCardApplyForm from "../components/patchCardApplyForm"

export default {
	components:{
		patchCardApplyForm
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
