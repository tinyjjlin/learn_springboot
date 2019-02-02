<template>
  <!--edit important person info -->
  <div>
    <el-form ref="importantInfo" :model="importantInfo" label-width="110px" :rules="rule">
      <el-form-item label="最高学历">
        <el-select v-model="importantInfo.highestDegree" disabled="disabled" placeholder="请选择学历">
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
        <el-radio-group disabled="disabled" v-model="importantInfo.maritalStatus">
          <el-radio :label="0">未婚</el-radio>
          <el-radio :label="1">已婚</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="现在居住地址">
        <el-input disabled="disabled" v-model="importantInfo.currentAddress"></el-input>
      </el-form-item>

      <el-form-item label="身份证号" prop="nationIdcard">
        <el-input disabled="disabled" v-model="importantInfo.nationIdcard"></el-input>
      </el-form-item>

      <el-form-item label="户籍">
        <el-input disabled="disabled" v-model="importantInfo.address"></el-input>
      </el-form-item>

      <el-form-item label="社保账号">
        <el-input disabled="disabled" v-model="importantInfo.socialInsuranceAccount"></el-input>
      </el-form-item>
      <el-form-item label="银行账号">
        <el-input disabled="disabled" v-model="importantInfo.bankCard"></el-input>
      </el-form-item>
      <el-form-item label="紧急联系人" prop="emergencyContact">
        <el-input disabled="disabled" v-model="importantInfo.emergencyContact"></el-input>
      </el-form-item>
      <el-form-item label="紧急联系人电话" prop="emergencyContactPhone">
        <el-input disabled="disabled" v-model="importantInfo.emergencyContactPhone"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" plain  disabled="disabled" @click="update(importantInfo)">保存</el-button>
        <el-button @click="close">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "impInfoEditView",
  props: ["passImportInfo"],
  data() {
    //   身份证号
    const nationIdcard = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("身份证号不能为空！"));
      } else if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value)) {
        callback(new Error("格式错误！"));
      } else {
        callback();
      }
    };
    // 紧急联系人
    const emergencyContact = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("紧急联系人不能为空！"));
      } else if (!/^[\u4E00-\u9FA5]{2,4}$/.test(value)) {
        callback(new Error("格式错误！"));
      } else {
        callback();
      }
    };
    // 紧急联系人电话
    const emergencyContactPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("紧急联系人电话不能为空！"));
      } else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test(value)) {
        callback(new Error("格式错误！"));
      } else {
        callback();
      }
    };
    return {
      importantInfo: Object.assign({}, this.passImportInfo),
      rule: {
        nationIdcard: [{ validator: nationIdcard, trigger: "blur" }],
        emergencyContact: [{ validator: emergencyContact, trigger: "blur" }],
        emergencyContactPhone: [
          { validator: emergencyContactPhone, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    close() {
      this.$emit("closeImportantEdit", 0);
    },
    update() {
      this.$refs.importantInfo.validate(valid => {
        if (valid) {
          this.$emit("updateImportant", this.importantInfo);
          this.$emit("closeImportantEdit", 0);
        }
      });
    }
  }
};
</script>
