<template>
  <div>
    <el-form :model="form" ref="form" status-icon label-width="180px" :rules="rulesForm">
      <!-- 申请类型 -->
      <el-form-item label="申请类型" prop="applicationTypeValue">
        <el-select
          v-model="form.applicationTypeValue"
          clearable
          @change="applyType"
          placeholder="请选择活动区域"
        >
          <el-option
            v-for="item in applicationType"
            :key="item.code"
            :label="item.description"
            :value="item.code"
          ></el-option>
        </el-select>
      </el-form-item>
      <!-- 申请时间 -->
      <el-form-item label="申请时间" prop="applicationTime">
        <el-date-picker
          v-model="form.applicationTime"
          align="right"
          type="date"
          @change="chooseApplyTime"
          value-format="yyyy-MM-dd"
          placeholder="选择日期"
          :picker-options="pickerOptions1"
        ></el-date-picker>
      </el-form-item>

      <!-- 根据申请类型显示不同的内容 -->
      <!-- 手机号 -->
      <el-form-item label="手机号(旧)" v-if="applicationTypeValue === 1">
        <el-input disabled :value="personalInfo.mobile"></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="mobileInfo" v-if="applicationTypeValue === 1">
        <el-input v-model="form.mobileInfo" placeholder="请输入手机号"></el-input>
      </el-form-item>

      <!-- 邮箱 -->
      <el-form-item label="邮箱(旧)" v-if="applicationTypeValue === 2">
        <el-input disabled :value="personalInfo.email"></el-input>
      </el-form-item>

      <el-form-item label="邮箱" prop="emailInfo" v-if="applicationTypeValue === 2">
        <el-input v-model="form.emailInfo"></el-input>
      </el-form-item>

      <!-- 最高学历 -->
      <el-form-item label="最高学历(旧)" v-if="applicationTypeValue === 3">
        <el-input disabled :value="personalInfo.highestRecordSchool"></el-input>
      </el-form-item>

      <el-form-item label="最高学历" prop="highestRecordSchoolInfo" v-if="applicationTypeValue === 3">
        <el-select v-model="form.highestRecordSchoolInfo" placeholder="请选择学历">
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

      <!-- 婚姻 -->
      <el-form-item label="婚姻状况(旧)" v-if="applicationTypeValue === 4">
        <el-input disabled :value="personalInfo.marriage == 0 ? '未婚' : '已婚'"></el-input>
      </el-form-item>

      <el-form-item label="婚姻状况" prop="marriageInfo" v-if="applicationTypeValue === 4">
        <el-radio-group v-model="form.marriageInfo">
          <el-radio label="0">未婚</el-radio>
          <el-radio label="1">已婚</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- 现在居住地址 -->
      <el-form-item label="现在居住地址(旧)" v-if="applicationTypeValue === 5">
        <el-input disabled :value="personalInfo.currentAddress"></el-input>
      </el-form-item>

      <el-form-item label="现在居住地址" prop="currentAddressInfo" v-if="applicationTypeValue === 5">
        <el-input v-model="form.currentAddressInfo"></el-input>
      </el-form-item>

      <!-- 身份证号 -->
      <el-form-item label="身份证号(旧)" v-if="applicationTypeValue === 6">
        <el-input disabled :value="personalInfo.nationidcard"></el-input>
      </el-form-item>

      <el-form-item label="身份证号" prop="nationidCardInfo" v-if="applicationTypeValue === 6">
        <el-input v-model="form.nationidCardInfo"></el-input>
      </el-form-item>

      <!-- 户籍 -->
      <el-form-item label="户籍(旧)" v-if="applicationTypeValue === 7">
        <el-input disabled :value="personalInfo.address"></el-input>
      </el-form-item>

      <el-form-item label="户籍" prop="addressInfo" v-if="applicationTypeValue === 7">
        <el-input v-model="form.addressInfo"></el-input>
      </el-form-item>

      <!-- 社保账号 -->
      <el-form-item label="社保账号(旧)" v-if="applicationTypeValue === 8">
        <el-input disabled :value="personalInfo.socialInsuranceAccount"></el-input>
      </el-form-item>

      <el-form-item
        label="社保账号"
        prop="socialInsuranceAccountInfo"
        v-if="applicationTypeValue === 8"
      >
        <el-input v-model="form.socialInsuranceAccountInfo"></el-input>
      </el-form-item>

      <!-- 银行账号 -->
      <div v-if="applicationTypeValue === 9">
        <el-form-item label="银行名称(旧)">
          <el-input disabled :value="personalInfo.bankName"></el-input>
        </el-form-item>
        <el-form-item label="银行账号(旧)">
          <el-input disabled :value="personalInfo.bankCard"></el-input>
        </el-form-item>
      </div>

      <div v-if="applicationTypeValue === 9">
        <el-form-item label="银行名称" prop="bankNameInfo">
          <el-select v-model="form.bankNameInfo" placeholder="请选择银行名称">
            <el-option
              v-for="(item, index) in bankName"
              :key="index"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="银行账号" prop="bankCardInfo">
          <el-input v-model="form.bankCardInfo"></el-input>
        </el-form-item>
      </div>

      <!-- 紧急联系方式 -->
      <div v-if="applicationTypeValue === 10">
        <el-form-item label="紧急联系人(旧)">
          <el-input disabled :value="personalInfo.emergencyContact"></el-input>
        </el-form-item>
        <el-form-item label="紧急联系人电话(旧)">
          <el-input disabled :value="personalInfo.emergencyContactPhone"></el-input>
        </el-form-item>
      </div>

      <div v-if="applicationTypeValue === 10">
        <el-form-item label="紧急联系人" prop="emergencyContact">
          <el-input v-model="form.emergencyContact"></el-input>
        </el-form-item>
        <el-form-item label="紧急联系人电话" prop="emergencyContactPhone">
          <el-input v-model="form.emergencyContactPhone"></el-input>
        </el-form-item>
      </div>

      <!-- 申请人 -->
      <el-form-item label="审批人" prop="approver">
        <el-select v-model="form.approver" @change="chooseApplicant" clearable placeholder="请选择审批人">
          <el-option
            v-for="item in approves"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="apply()">申请</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { getBankName } from '@/utils/shareData'
import {
  getStaffInfoItem,
  apply,
  getPersonnelApprover,
  getStaffIdinfo
} from "@/api/staff";

export default {
  props: ["applicationTypeValue", "approverValue", "appliValueTime"],
  computed: {
    ...mapGetters(["empNo"])
  },
  data() {
    //  手机号
    const checkMobile = (rule, value, callback) => {
      console.log(value);
      if (!value) {
        return callback(new Error("手机号不能为空！"));
      } else if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test(value)) {
        callback(new Error("输入的手机号格式不正确！"));
      } else {
        callback();
      }
    };
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
    const checkEmail = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("邮箱不能为空！"));
      } else if (
        !/^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test(value)
      ) {
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
      form: {
        mobileInfo: "",
        emailInfo: "",
        highestRecordSchoolInfo: "",
        marriageInfo: "",
        currentAddressInfo: "",
        nationidCardInfo: "",
        addressInfo: "",
        socialInsuranceAccountInfo: "",
        bankNameInfo: "",
        bankCardInfo: "",
        emergencyContact: "",
        emergencyContactPhone: "",
        applicationTypeValue: "",
        applicationTime: null,
        approver: ""
      },
      rulesForm: {
        applicationTypeValue: [
          {
            required: true,
            message: "申请类型不为空",
            trigger: "change"
          }
        ],
        applicationTime: [
          { required: true, message: "申请时间不为空", trigger: "change" }
        ],
        mobileInfo: [
          { required: true, validator: checkMobile, trigger: "blur" }
        ],
        emailInfo: [{ required: true, validator: checkEmail, trigger: "blur" }],
        highestRecordSchoolInfo: [
          { required: true, message: "学历不为空", trigger: "change" }
        ],
        marriageInfo: [
          { required: true, message: "婚姻状况不为空", trigger: "blur" }
        ],
        currentAddressInfo: [
          { required: true, message: "现在居住地址不为空", trigger: "blur" }
        ],
        nationidCardInfo: [
          { required: true, validator: nationIdcard, trigger: "blur" }
        ],
        addressInfo: [
          { required: true, message: "户籍不为空", trigger: "blur" }
        ],
        socialInsuranceAccountInfo: [
          { required: true, message: "社保账号不为空", trigger: "blur" }
        ],
        bankNameInfo: [
          { required: true, message: "银行名称不能为空", trigger: "change" }
        ],
        bankCardInfo: [
          { required: true, validator: checkBankCard, trigger: "blur" },
        ],
        emergencyContact: [
          { required: true, validator: emergencyContact, trigger: "blur" }
        ],
        emergencyContactPhone: [
          { required: true, validator: checkMobile, trigger: "blur" }
        ],
        approver: [
          { required: true, message: "请选择审批人", trigger: "change" }
        ]
      },
      applicationType: [],
      pickerOptions1: {
        //   禁用日期
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        //   快捷选项
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            }
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            }
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            }
          }
        ]
      },
      approves: [],
      personalInfo: {
        mobile: "",
        email: "",
        highestRecordSchool: "",
        marriage: "",
        currentAddress: "",
        nationidcard: "",
        address: "",
        socialInsuranceAccount: "",
        bankName: "",
        bankCard: "",
        emergencyContact: "",
        emergencyContactPhone: ""
      },
      bankName: []
    };
  },
  watch: {
    applicationTypeValue(val) {
      this.form.applicationTypeValue = this.applicationTypeValue;
    },
    approverValue(val) {
      this.form.approver = this.approverValue;
    },
    appliValueTime(val) {
      this.form.applicationTime = this.appliValueTime;
    }
  },
  created() {
    getStaffInfoItem().then(res => {
      this.applicationType = res.data.data;
    });
    this.form.applicationTypeValue = this.applicationTypeValue;
    this.form.approver = this.approverValue;
    this.form.applicationTime = this.appliValueTime;
    getPersonnelApprover()
      .then(res => {
        if (res.data.code == 200) {
          this.approves = res.data.data.map(o => {
            return { value: o.empNo, label: o.name };
          });
        } else {
          this.$message.error(this.data.msg);
        }
      })
      .catch(error => {
        this.$message.error(error);
      });
    getStaffIdinfo(this.empNo).then(res => {
      this.personalInfo = {
        mobile: res.data.data.mobile,
        email: res.data.data.email,
        highestRecordSchool: res.data.data.highestDegree,
        marriage: res.data.data.maritalStatus,
        currentAddress: res.data.data.currentAddress,
        nationidcard: res.data.data.nationIdcard,
        address: res.data.data.address,
        socialInsuranceAccount: res.data.data.socialInsuranceAccount,
        bankName: res.data.data.bankName,
        bankCard: res.data.data.bankCard,
        emergencyContact: res.data.data.emergencyContact,
        emergencyContactPhone: res.data.data.emergencyContactPhone
      };
    });
    this.bankName = getBankName();
  },
  methods: {
    apply() {
      let data = {};
      this.$refs.form.validate(valid => {
        if (valid) {
          let arr = [];
          for (var item in this.applicationType) {
            arr[this.applicationType[item].code] = this.applicationType[
              item
            ].description;
          }

          data.applyTime = this.form.applicationTime;
          data.approver = this.form.approver;
          //  data.flowCategory =arr[this.form.applicationTypeValue];
          data.flowCategory = this.form.applicationTypeValue;

          if (this.form.applicationTypeValue === 1) {
            data.firstItem = arr[1];
            data.firstItemOldValue = this.personalInfo.mobile;
            data.firstItemNewValue = this.form.mobileInfo;
          } else if (this.form.applicationTypeValue === 2) {
            data.firstItem = arr[2];
            data.firstItemOldValue = this.personalInfo.email;
            data.firstItemNewValue = this.form.emailInfo;
          } else if (this.form.applicationTypeValue === 3) {
            data.firstItem = arr[3];
            data.firstItemOldValue = this.personalInfo.highestRecordSchool;
            data.firstItemNewValue = this.form.highestRecordSchoolInfo;
          } else if (this.form.applicationTypeValue === 4) {
            data.firstItem = arr[4];
            data.firstItemOldValue = this.personalInfo.marriage;
            data.firstItemNewValue = this.form.marriageInfo;
          } else if (this.form.applicationTypeValue === 5) {
            data.firstItem = arr[5];
            data.firstItemOldValue = this.personalInfo.currentAddress;
            data.firstItemNewValue = this.form.currentAddressInfo;
          } else if (this.form.applicationTypeValue === 6) {
            data.firstItem = arr[6];
            data.firstItemOldValue = this.personalInfo.nationidcard;
            data.firstItemNewValue = this.form.nationidCardInfo;
          } else if (this.form.applicationTypeValue === 7) {
            data.firstItem = arr[7];
            data.firstItemOldValue = this.personalInfo.address;
            data.firstItemNewValue = this.form.addressInfo;
          } else if (this.form.applicationTypeValue === 8) {
            data.firstItem = arr[8];
            data.firstItemOldValue = this.personalInfo.socialInsuranceAccount;
            data.firstItemNewValue = this.form.socialInsuranceAccountInfo;
          } else if (this.form.applicationTypeValue === 9) {
            data.firstItem = "银行名称";
            data.firstItemOldValue = this.personalInfo.bankName;
            data.firstItemNewValue = this.form.bankNameInfo;
            data.secondItem = "银行卡号";
            data.firstItemOldValue = this.personalInfo.bankCard;
            data.secondItemNewValue = this.form.bankCardInfo;
          } else if (this.form.applicationTypeValue === 10) {
            data.firstItem = "紧急联系人";
            data.firstItemOldValue = this.personalInfo.emergencyContact;
            data.firstItemNewValue = this.form.emergencyContact;
            data.secondItem = "紧急联系人手机号";
            data.firstItemOldValue = this.personalInfo.emergencyContactPhone;
            data.secondItemNewValue = this.form.emergencyContactPhone;
          }
          // 审核状态 0 默认未审核
          data.approvalStatus = 0;

          apply(data).then(res => {
            if (res.data.code == 200) {
              this.$message({
                type: "success",
                message: "申请成功，请等待审批完成"
              });
              this.$refs.form.resetFields();
              this.$emit("applySuccess", 1);
            } else {
              this.$message({
                type: "error",
                message: "申请失败，请重新申请"
              });
              this.$refs.form.resetFields();
              this.$emit("applySuccess", 0);
            }
          });
        } else {
          this.$message({
            type: "error",
            message: "请补全信息，再进行提交"
          });
        }
      });
    },
    applyType() {
      let index = this.form.applicationTypeValue;
      const rule = this.$refs.form.rules;
      this.$emit("applicationType", this.form.applicationTypeValue);
      this.$emit("applicant", this.form.approver);
      this.$emit("applyForTime", this.form.applicationTime);
      //  移除表单项的校验结果。传入待移除的表单项的 prop 属性或者 prop 组成的数组，如不传则移除整个表单的校验结果
      this.$refs.form.clearValidate();
      this.$refs.form.rules = rule;
    },
    chooseApplicant() {
      this.$emit("applicant", this.form.approver);
    },
    chooseApplyTime() {
      this.$emit("applyForTime", this.form.applicationTime);
    } 
  }
};
</script>