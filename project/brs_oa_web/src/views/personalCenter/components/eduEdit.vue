<template>
  <div class="edu-edit-panel">
    <div class="edu-edit-ul">
      <el-form ref="eduRecord" :model="eduRecord" label-width="100px" :rules="rules">
        <el-form-item class="form-item" label="学校名称：" prop="schoolName">
          <div class="item-wrapper">
            <el-input v-model="eduRecord.schoolName" placeholder="请输入学校名称"></el-input>
          </div>
        </el-form-item>

        <el-form-item class="form-item" label="就读时间：">
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

        <el-form-item class="form-item" label="所学专业：" prop="major">
          <div class="item-wrapper">
            <el-input v-model="eduRecord.major" placeholder="请输入所学专业"></el-input>
            <!-- <el-select v-model="eduRecord.major" clearable placeholder="请输入所学专业">
              <el-option
                v-for="item in diplomasOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select> -->
            <!--
                      <el-autocomplete
                         v-model="eduRecord.major"
                         :fetch-suggestions="queryMajorAsync"
                         placeholder="请输入所学专业"
                         @select="handleSelect"
                      ></el-autocomplete>
            -->
          </div>
        </el-form-item>
        <el-form-item class="form-item" label=" 是否统招：">
          <div class="item-wrapper">
            <el-radio-group v-model="eduRecord.isNationalUnified">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>
        <el-form-item class="form-item" label="学历/学位：">
          <div class="item-wrapper">
            <el-select v-model="eduRecord.diplomas" clearable placeholder="请选择学历/学位">
              <el-option
                v-for="item in diplomasOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
        </el-form-item>
        <el-form-item class="form-item">
          <el-button round type="primary" @click="update">保存</el-button>
          <el-button round type="info" @click="close">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { dateRangeToStr, strToDateRange } from "@/utils/tools.js";
export default {
  name: "eduEditView",
  props: ["passEduRecord", "passOpenViewType"],
  data() {
    return {
      eduRecord: {},
      startEndTime: "",
      viewType: 0, //视图类型

      majors: [
        {
          value: "大专",
          label: "大专"
        }
      ],
      diplomasOption: [
        {
          value: "大专",
          label: "大专"
        },
        {
          value: "本科",
          label: "本科"
        },
        {
          value: "硕士",
          label: "硕士"
        },
        {
          value: "博士",
          label: "博士"
        },
        {
          value: "MBA",
          label: "MBA"
        },
        {
          value: "EMBA",
          label: "EMBA"
        },
        {
          value: "中专",
          label: "中专"
        },
        {
          value: "中技",
          label: "中技"
        },
        {
          value: "高中",
          label: "高中"
        },
        {
          value: "初中",
          label: "初中"
        },
        {
          value: "其他",
          label: "其他"
        }
      ],
      rules: {
        schoolName: [
          { required: true, message: "学校名称不能为空！", trigger: "blur" }
        ],
        major: [
          { required: true, message: "所学专业不能为空！", trigger: "blur" }
        ]
      }
    };
  },

  created() {
    this.initView();
  },
  methods: {
    initView() {
      this.eduRecord = Object.assign({}, this.passEduRecord);
      this.startEndTime = strToDateRange(this.eduRecord.educationStartEndTime);
      this.viewType = this.passOpenViewType;
    },
    close() {
      this.$emit("closeEduEdit");
    },
    update() {
      this.$refs.eduRecord.validate(valid => {
        if (valid) {
          this.$emit("updateEduRecord", this.getEditedData(), this.viewType);
        }
      });
    },
    //编辑完的数据
    getEditedData() {
      //时间范围数值，转换为字符串
      this.eduRecord.educationStartEndTime = dateRangeToStr(this.startEndTime);
      return this.eduRecord;
    }
  }
};
</script>
<style scoped>
.el-input--medium {
  width: 200px;
}
</style>