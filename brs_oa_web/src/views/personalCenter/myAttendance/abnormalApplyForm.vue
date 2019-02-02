<template>
    <div class="abnormal-apply-form">
        <el-form  label-width="150px">

        <el-form-item label="请选择申请类型" prop="workName">
            <el-select v-model="whichForm" placeholder="请选择申请类型">
                <el-option  label="补卡" value="1"></el-option>
                <el-option  label="请假" value="2"></el-option>
            </el-select>     
        </el-form-item>
        </el-form>
        <patch-card-apply-form :date="date" :dayStatus="dayStatus" v-if="whichForm==1"  @callback="submitCallback"></patch-card-apply-form>
        <workflow-apply-form :date="date" :startDayStatus="dayStatus" v-if="whichForm==2"  @callback="submitCallback"></workflow-apply-form>
    </div>
</template>
<script>
import patchCardApplyForm from "../../workProcess/components/patchCardApplyForm"
import workflowApplyForm from "../../workProcess/components/workflowApplyForm"

export default {
    components:{
        patchCardApplyForm,workflowApplyForm
    },
    props:['date','dayStatus'],
    data(){
        return {
            whichForm:""
        }
    },
    methods:{
        submitCallback(){
            this.$emit("callback",1)
        }
    }
}
</script>
<style>

</style>
