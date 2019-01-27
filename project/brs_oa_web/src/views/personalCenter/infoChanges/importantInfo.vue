<template>
  <div>
    <el-form-item label="最高学历">
      <el-select v-model="importantInfo.highestDegree" placeholder="请选择学历">
        <el-option label="大专" value="大专"></el-option>
        <el-option label="本科" value="本科"></el-option>
        <el-option label="硕士" value="硕士"></el-option>
        <el-option label="博士" value="博士"></el-option>
        <el-option label="MBA" value="MBA"></el-option>
        <el-option label="EMBA" value="EMBA"></el-option>
        <el-option label="高中" value="高中"></el-option>
        <el-option label="中专" value="中专"></el-option>
        <el-option label="初中" value="初中"></el-option>
        <el-option label="其他" value="其他"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="婚姻状况">
      <el-radio-group v-model="importantInfo.maritalStatus">
        <el-radio :label="0">未婚</el-radio>
        <el-radio :label="1">已婚</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="现在居住地址">
      <el-input v-model="importantInfo.currentAddress"></el-input>
    </el-form-item>

    <el-form-item label="身份证号" prop="nationIdcard">
      <el-input v-model="importantInfo.nationIdcard"></el-input>
    </el-form-item>

    

    
    
    
  </div>
</template>

<script>
export default {
  props: ["importantInfo"],
  data() {
      return {
        highestDegree: '',
        maritalStatus: '',
        currentAddress: '',
        nationIdcard: '',
        address: '',
        socialInsuranceAccount: '',
        bankCard: '',
        emergencyContact: '',
        emergencyContactPhone: ''
      }
  },
  created() {
    this.info = this.importantInfo
  }
};
</script>