<template>
  <div class="train-edit-panel">
    <div class="train-edit">
      <el-form ref="trainRecord" :model="trainRecord" label-width="100px" :rules="rule">
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

        <el-form-item class="form-item" label="培训机构：" prop="trainAgency">
          <div class="item-wrapper">
            <el-input v-model="trainRecord.trainAgency" placeholder="请输入培训机构名称"></el-input>
          </div>
        </el-form-item>

        <el-form-item class="form-item" label="课程：" prop="trainCourse">
          <div class="item-wrapper">
            <el-input v-model="trainRecord.trainCourse" placeholder="请课程名称"></el-input>
          </div>
        </el-form-item>

        <el-form-item class="form-item">
          <el-button round type="primary" @click="update">保存</el-button>
          <el-button round type="info" @click="closeView">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { dateRangeToStr, strToDateRange } from "@/utils/tools.js";

export default {
  name: "trainEditView",
  props: ["passTrainObj", "passViewType"],
  data() {
    return {
      trainRecord: {},
      startEndTime: "",
      viewType: 0,
      rule: {
        trainAgency: [
          { required: true, message: "培训机构不能为空", trigger: "blur" }
        ],
        trainCourse: [
          { required: true, message: "课程不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    //初始数据
    this.initView();
  },
  methods: {
    initView() {
      //浅拷贝（不涉及属性是对象类型）
      this.trainRecord = Object.assign({}, this.passTrainObj);
      this.viewType = this.passViewType;
      this.startEndTime = strToDateRange(this.trainRecord.trainStartEndTime);
    },
    closeView() {
      this.$emit("closeTrainEditView");
    },
    update() {
      this.$refs.trainRecord.validate(valid => {
        if (valid) {
          this.$emit("updateTrainRecord", this.getEditedData(), this.viewType);
        }
      });
    },
    //编辑完的数据
    getEditedData() {
      //时间范围数值，转换为字符串
      this.trainRecord.trainStartEndTime = dateRangeToStr(this.startEndTime);
      return this.trainRecord;
    }
  }
};
</script>
