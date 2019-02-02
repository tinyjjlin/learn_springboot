<template>
  <div class="work-edit-panel">
    <div class="work-edit">
      <el-form ref="workRecord" :model="workRecord" label-width="150px" :rules="rules2">
        <el-form-item class="form-item" label="工作单位：" prop="companyName">
          <div class="item-wrapper">
            <el-input v-model="workRecord.companyName" placeholder="请输入工作单位"></el-input>
          </div>
        </el-form-item>

        <el-form-item class="form-item" label="起止时间：">
          <div class="item-wrapper">
            <el-date-picker
              v-model="startEndTime"
              type="daterange"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </div>
        </el-form-item>

        <el-form-item class="form-item" label="职位：">
          <div class="item-wrapper">
            <el-input v-model="workRecord.oldPosition" placeholder="请输入职位名称"></el-input>
          </div>
        </el-form-item>

        <el-form-item class="form-item" label=" 离职原因：" prop="reasonToLeave">
          <div class="item-wrapper">
            <el-input v-model="workRecord.reasonToLeave" placeholder="请输入离职原因"></el-input>
          </div>
        </el-form-item>

        <el-form-item class="form-item" label=" 证明人：" prop="certifier">
          <div class="item-wrapper">
            <el-input v-model="workRecord.certifier" placeholder="请输入证明人姓名"></el-input>
          </div>
        </el-form-item>

        <el-form-item class="form-item" label=" 证明人电话：" prop="certifierMobile">
          <div class="item-wrapper">
            <el-input v-model="workRecord.certifierMobile" placeholder="请输入证明人电话"></el-input>
          </div>
        </el-form-item>
        <el-form-item class="form-item">
          <el-button round type="primary" @click="update(workRecord)">保存</el-button>
          <el-button round type="info" @click="closeView">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { dateRangeToStr, strToDateRange } from "@/utils/tools.js";
export default {
  name: "workEditView",
  props: ["passWorkRecord", "passOpenViewType"],
  data() {
    const certifierMobile = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("证明人手机号不能为空！"));
      } else if (!/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(value)) {
        callback(new Error("格式错误！"));
      } else {
        callback();
      }
    };
    return {
      workRecord: {},
      startEndTime: "",
      viewType: this.passOpenViewType, //视图类型
      rules2: {
        companyName: [
          { required: true, message: "工作单位不能为空！", trigger: "blur" }
        ],
        reasonToLeave: [
          { required: true, message: "离职原因不能为空！", trigger: "blur" }
        ],
        certifier: [
          { required: true, message: "证明人不能为空！", trigger: "blur" }
        ],
        certifierMobile: [{ required: true, validator: certifierMobile, trigger: "blur" }]
      }
    };
  },
  created() {
    this.initView();
  },
  methods: {
    initView() {
      this.workRecord = Object.assign({}, this.passWorkRecord);
      console.log(this.workRecord)
      this.startEndTime = strToDateRange(this.workRecord.workStartEndTime);
      this.viewType = this.passOpenViewType;
    },
    closeView() {
      this.$emit("closeWorkEditView");
    },
    update(form) {
      this.$refs.workRecord.validate(valid => {
        if (valid) {
          this.$emit(
            "updateWorkRecord",
            this.getEditedData(form),
            this.viewType
          );
        }
      });
    },
    //编辑完的数据
    getEditedData(workRecord) {
      //时间范围数值，转换为字符串
      this.workRecord.workStartEndTime = dateRangeToStr(this.startEndTime);
      return this.workRecord;
    }
  }
};
</script>
