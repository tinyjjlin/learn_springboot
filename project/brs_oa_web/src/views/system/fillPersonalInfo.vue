<template>
  <div>
    <el-form
      :model="baseInfoForm"
      ref="baseInfoForm"
      label-width="150px"
      class="demo-ruleForm"
      status-icon
      :rules="rulesInfo"
    >
      <el-form-item label="姓名" prop="name">
        <el-input type="age" v-model.number="baseInfoForm.name"></el-input>
      </el-form-item>

      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="baseInfoForm.gender">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="出生日期" prop="birthday">
        <el-input type="age" v-model.number="baseInfoForm.birthday"></el-input>
      </el-form-item>

      <el-form-item label="政治面貌" prop="politicsStatus">
        <el-select v-model="baseInfoForm.politicsStatus" placeholder="请选择">
          <el-option
            v-for="item in politicsStatus"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="民族" prop="nation">
        <el-select v-model="baseInfoForm.nation" placeholder="请选择">
          <el-option
            v-for="item in nation"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="baseInfoForm.mobile"></el-input>
      </el-form-item>

      <el-form-item
        label="邮箱"
        prop="email"
        :rules="[
      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur'] }
    ]"
      >
        <el-input v-model="baseInfoForm.email"></el-input>
      </el-form-item>

      <el-form-item label="最高学历" prop="highestDegree">
        <el-select v-model="baseInfoForm.highestDegree" placeholder="请选择">
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
      <el-form-item label="婚姻状况" prop="maritalStatus">
        <el-radio-group v-model="baseInfoForm.maritalStatus">
          <el-radio :label="0">未婚</el-radio>
          <el-radio :label="1">已婚</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="现在居住地址" prop="currentAddress">
        <el-input v-model="baseInfoForm.currentAddress"></el-input>
      </el-form-item>

      <el-form-item label="身份证号" prop="nationIdcard">
        <el-input v-model="baseInfoForm.nationIdcard"></el-input>
      </el-form-item>

      <el-form-item label="户籍" prop="address">
        <el-input v-model="baseInfoForm.address"></el-input>
      </el-form-item>

      <el-form-item label="社保账号" prop="socialInsuranceAccount">
        <el-input v-model="baseInfoForm.socialInsuranceAccount"></el-input>
      </el-form-item>
      <el-form-item label="银行名称" prop="bankName">
        <el-select v-model="baseInfoForm.bankName" placeholder="请选择">
          <el-option
            v-for="item in bankName"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="银行账号" prop="bankCard">
        <el-input v-model="baseInfoForm.bankCard"></el-input>
      </el-form-item>
      <el-form-item label="紧急联系人" prop="emergencyContact">
        <el-input v-model="baseInfoForm.emergencyContact"></el-input>
      </el-form-item>
      <el-form-item label="紧急联系人电话" prop="emergencyContactPhone">
        <el-input v-model="baseInfoForm.emergencyContactPhone"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(baseInfoForm)">提交</el-button>
        <el-button @click="resetForm()">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getPoliticsStatus, getNation, getBankName } from "@/utils/shareData";
import { addGeneralInfo } from '@/api/staff'

export default {
  name: "fillPersonalInfo",
  props: ['info'],
  data() {
    const checkUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("姓名不能为空！"));
      } else if (!/^[\u4E00-\u9FA5]{2,4}$/.test(value)) {
        callback(new Error("格式错误"));
      } else {
        callback();
      }
    };
    const checkMobile = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("手机号不能为空！"));
      } else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test(value)) {
        callback(new Error("输入的手机号格式不正确！"));
      } else {
        callback();
      }
    };
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
    const checkBankCard = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("银行账号不能为空！"));
      } else if (!/^([1-9]{1})(\d{15}|\d{18})$/.test(value)
      ) {
        callback(new Error("请输入正确的银行账号"));
      } else {
        callback();
      }
    }
    return {
      politicsStatus: [],
      nation: [],
      bankName: [],
      baseInfoForm: {
        name: "",
        gender: "",
        birthday: '',
        politicsStatus: "",
        nation: "",
        mobile: "",
        email: "",
        highestDegree: "",
        maritalStatus: "",
        currentAddress: "",
        nationIdcard: "",
        address: "",
        socialInsuranceAccount: "",
        bankName: "",
        bankCard: "",
        emergencyContact: "",
        emergencyContactPhone: ""
      },
      rulesInfo: {
        name: [{ required: true, validator: checkUsername, trigger: "blur" }],
        gender: [{ required: true, message: "性别不能为空", trigger: "change" }],
        birthday: [{ required: true, message: '出生日期不能为空', trigger: 'change' }],
        politicsStatus: [
          { required: true, message: "政治面貌不能为空", trigger: "change" }
        ],
        nation: [{ required: true, message: "民族不能为空", trigger: "change" }],
        mobile: [{ required: true, validator: checkMobile, trigger: "blur" }],
        highestDegree: [
          { required: true, message: "最高学历不能为空", trigger: "change" }
        ],
        maritalStatus: [
          { required: true, message: "婚姻状态不能为空", trigger: "change" }
        ],
        currentAddress: [
          { required: true, message: "现在居住地址不能为空", trigger: "change" }
        ],
        nationIdcard: [{ required: true, validator: nationIdcard, trigger: "blur" }],
        address: [
          { required: true, message: "户籍不能为空", trigger: "change" }
        ],
        socialInsuranceAccount: [
          { required: true, message: "社保账号不能为空", trigger: "blur" }
        ],
        bankName: [
          { required: true, message: "银行名称不能为空", trigger: "change" }
        ],
        bankCard: [
          { required: true, validator: checkBankCard, trigger: "blur" },
        ],
        emergencyContact: [
          { required: true, validator: emergencyContact, trigger: "blur" }
        ],
        emergencyContactPhone: [
          { required: true, validator: checkMobile, trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.politicsStatus = getPoliticsStatus();
    this.nation = getNation();
    this.bankName = getBankName();
  },
  methods: {
    submitForm(data) {
      console.log(data)
      this.$refs.baseInfoForm.validate((valid) => {
        if(valid) {
          addGeneralInfo(data).then((res) => {
            if(res.data.code == 200) {
              this.info = this.baseInfoForm
              console.log(res)
              this.$emit('tip',false)
              this.$message({
                type: 'success',
                message: '填写成功'
              })
            } else {
              this.$message({
                type: 'error',
                message: '请重新填写信息'
              })
            }
          })
        }
      })
    },
    resetForm() {
      this.$refs.baseInfoForm.resetFields();
    }
  }
};
</script>