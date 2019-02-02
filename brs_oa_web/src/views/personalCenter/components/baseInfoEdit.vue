<template>
  <!-- edit base info -->
  <div class="edit-profile-base">
    <div>
      <div>
        <div class="img">
          <!-- 上传头像 -->
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :headers="header"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img :src="'http://172.18.19.3:8066/'+imageUrl" class="avatar">
            <!-- <i v-else class="el-icon-plus avatar-uploader-icon"></i> -->
          </el-upload>
          <label for>上传头像</label>
        </div>

        <!-- 表单 -->
        <div id="info">
          <!-- <el-form
            ref="baseInfo"
            :model="baseInfo"
            :rules="rules2"
            label-width="80px"
            hide-required-asterisk="false"
          >
            <el-form-item label="姓名" prop="name">
              <el-input v-model="baseInfo.name" key="baseInfo.name" :disabled="isDisabledName"></el-input>
            </el-form-item>

            <el-form-item label="性别" prop="gender">
              <el-radio-group
                :disabled="isDisabledGender"
                v-model="baseInfo.gender"
                @click="changeRadio"
              >
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="政治面貌" prop="politicsStatus">
              <el-select
                v-model="baseInfo.politicsStatus"
                placeholder="请选择政治面貌"
                :disabled="isDisabledPoliticsStatus"
              >
                <el-option
                  v-for="item in politicsStatus"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            
            <el-form-item label="民族" prop="nation">
              <el-select v-model="baseInfo.nation" placeholder="请选择民族">
                <el-option
                  v-for="item in nation"
                  :disabled="isDisabledNation"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="手机号" prop="mobile">
              <el-input v-model="baseInfo.mobile" :disabled="isDisableMobile"></el-input>
            </el-form-item>

            <el-form-item
              label="邮箱"
              prop="email"
            >
              <el-input v-model="baseInfo.email" :disabled="isDisableEmail"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button
                plain
                :disabled="isDisableButton"
                type="primary"
                @click="update(baseInfo)"
              >保存更新</el-button>
              <el-button @click="close">取消</el-button>
            </el-form-item>
          </el-form> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getPoliticsStatus , getNation } from '@/utils/shareData'
export default {
  name: "baseInfoEditView",
  props: ["passBaseInfo"],
  /*
  computed:{
      baseInfo:function(){
          let baseInfo = {};
          if(this.passBaseInfo != null){
              baseInfo = Object.assign({},this.passBaseInfo);
          }
          return baseInfo;
      }
  },
  */
  data() {
    const checkUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("姓名不能为空！"));
      } else if (!/^[\u4E00-\u9FA5]{2,50}$/.test(value)) {
        callback(new Error("格式错误"));
      } else {
        callback();
      }
    };
    // const checkMobile = (rule, value, callback) => {
    //   if (!value) {
    //     return callback(new Error("手机号不能为空！"));
    //   } else if (!/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(value)) {
    //     callback(new Error("输入的手机号格式不正确！"));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      uploadUrl: "http://172.18.19.3:8082/api/uploadImage",
      baseInfo: Object.assign({}, this.passBaseInfo),
      imageUrl: "",
      rules2: {
        name: [{ validator: checkUsername, trigger: "blur" }]
        // mobile: [{ validator: checkMobile, trigger: "blur" }]
      },
      isDisabledName: false,
      isDisabledGender: false,
      isDisabledPoliticsStatus: false,
      isDisabledNation: false,
      isDisableMobile: true,
      isDisableEmail: true,
      isDisableButton: false,
      header: {
        Authorization: this.$store.state.user.token
      },
      politicsStatus: [],
      nation: []
    };
  },
  created() {
    let info = this.passBaseInfo;
    // if(info.name !== '' && info.gender !== '' &&  info.politicsStatus !== '' && info.nation !== '') {
    //   this.isDisableButton = true
    // }
    this.imageUrl = info.picture;
    // console.log(info);
    this.politicsStatus = getPoliticsStatus()
    this.nation = getNation()
  },
  methods: {
    changeRadio(value) {
      this.$nextTick(function() {
        this.baseInfo.gender = value;
      });
    },
    // 取消
    close() {
      this.$emit("closeEdit", 0);
    },
    // 保存更新
    update() {
      this.$refs.baseInfo.validate(valid => {
        if (valid) {
          this.$emit("updateBase", this.baseInfo);
          this.$emit("closeEdit", 0);
        }
      });
    },
    //  图片上传，先执行 beforeAvatarUpload -- 在执行 action 提交 -- 执行  handleAvatarSuccess  or  uploadError
    //  上传成功
    handleAvatarSuccess(res, file) {
      if (res.code == 2000) {

        this.$store.commit('SET_AVATAR',file.response.data)

        this.imageUrl = file.response.data;
        this.$message({
          type: "success",
          message: "图片上传成功"
        });
      }
    },
    //  上传之前
    beforeAvatarUpload(file) {
      const JPG = file.type === "image/jpeg";
      const PNG = file.type === "image/png";
      const BMP = file.type === "image/bmp";
      const GIF = file.type === "image/gif";
      const Lt2M = file.size / 1024 / 1024 < 2;
      if (!JPG && !PNG && !BMP && !GIF) {
        this.$message.error("上传头像图片只能是 JPG / PNG / GIF / BMP 格式!");
      }
      if (!Lt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return (JPG || PNG || BMP || GIF) && Lt2M;
    }
  }
};
</script>

<style lang="scss" scoped>
.edit-profile-base {
  overflow: hidden;
  .img {
    float: right;
    margin-top: 30px;
    .avatar-uploader {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      display: inline-block;
      .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        .avatar-uploader-icon {
          font-size: 28px;
          color: #8c939d;
          width: 178px;
          height: 178px;
          line-height: 178px;
          text-align: center;
        }
        .avatar {
          width: 178px;
          height: 178px;
          display: block;
        }
      }
      .el-upload:hover {
        border-color: #409eff;
      }
    }
    label {
      display: block;
      text-align: center;
      line-height: 50px;
      font-size: 14px;
      color: #606266;
    }
  }
  #info {
    width: 70%;
    float: left;
    /deep/.el-input {
      width: 84%;
      margin-right: 14px;
    }
  }
}
</style>
