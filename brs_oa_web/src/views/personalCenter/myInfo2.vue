<template>
  <div class="profile-page">
    <!-- 个人基本信息-->
    <div class="profile-header">
      <!-- <span>个人信息</span> -->
      <div class="profile-edit-btn">
        <!--<el-button   @click="showEditBtn" type="primary" icon="el-icon-edit" circle></el-button> -->
        <el-button
          @click="changeBtn"
          type="primary"
          plain
          icon="el-icon-edit-outline"
        >{{ btnObj.text }}</el-button>
      </div>
    </div>

    <div class="profile-base-wrapper">
      <div class="profile-base">
        <!--<p class="info-title">基本信息</p> -->
        <div class="profile-base" v-if="baseInfoStatus==0">
          <span class="profile-edit" v-if="btnObj.isShowEdit" @click="changeBaseInfoStatus(1)">
            <i class="el-icon-edit"></i>修改头像
          </span>
        </div>
        <img
          class="person-head"
          :src="'http://172.18.19.3:8066/'+baseInfo.picture"
        >
        <!-- eidt-->
        <!-- <base-info-edit-panel
          @updateBase="updateBaseInfo"
          @closeEdit="changeBaseInfoStatus"
          :pass-base-info="baseInfo"
          v-if="baseInfoStatus==1"
        ></base-info-edit-panel>-->
        <div v-if="baseInfoStatus==1" class="img">
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
      </div>
      <div class="person-base">
        <p>
          <span>{{ baseInfo.empNo }}</span>
          <span>{{ baseInfo.name }}</span>
          <span>{{ baseInfo.positionName }}</span>
          <span>{{ baseInfo.gender }}</span>
          <span>{{ baseInfo.politicsStatus }}</span>
          <span>{{ baseInfo.nation }}</span>
        </p>
        <p>
          <span>部门：{{ baseInfo.deptName == '' ? 'it部门' : baseInfo.deptName }}</span>
          <span>手机：{{ baseInfo.mobile == '' ? '12365478963' : baseInfo.mobile }}</span>
          <span>邮箱：{{ baseInfo.email == '' ? '5446462@aliyun.com' : baseInfo.email }}</span>
        </p>
      </div>
    </div>
    <!--个人基本信息  end -->
    <!--个人重要信息 -->
    <div class="profile-info-wrapper">
      <div class="profile-info">
        <!-- <p class="info-title">基本信息</p> -->
        <div class="box-info" v-show="importantInfoStatus===0">
          <span
            class="profile-edit"
            v-if="editable && btnObj.isShowEdit"
            id="edit"
            @click="changeImportantInfoStatus(1)"
          >
           <!--  -->
            <i class="el-icon-edit"></i>编辑
          </span>
          <ul class="per-info-ul">
            <li>
              <span>出生日期：{{ importantInfo.birthday }}</span>
            </li>
            <li>
              <span>入职日期：{{ baseInfo.hireDate === '' ? '2019-1-25' : baseInfo.hireDate }}</span>
            </li>
            <li>
              <span>最高学历：{{importantInfo.highestDegree}}</span>
            </li>
            <li>
              <span>婚姻状况：{{importantInfo.maritalStatus}}</span>
            </li>
            <li>
              <span>现在居住地址：{{importantInfo.currentAddress}}</span>
            </li>
            <li>
              <span>身份证号：{{importantInfo.nationIdcard}}</span>
            </li>
            <li>
              <span>户籍：{{importantInfo.address}}</span>
            </li>
            <li>
              <span>社保账号：{{importantInfo.socialInsuranceAccount}}</span>
            </li>
            <li>
              <span>银行名称：{{ importantInfo.bankName }}</span>
            </li>
            <li>
              <span>银行账号：{{importantInfo.bankCard}}</span>
            </li>
            <li>
              <span>本地紧急联系人：{{importantInfo.emergencyContact}}</span>
            </li>
            <li>
              <span>本地紧急联系人电话：{{importantInfo.emergencyContactPhone}}</span>
            </li>
          </ul>
        </div>
        <!-- edit important info -->
        <!-- <important-info-edit-panel
          @updateImportant="updateImportInfo"
          @closeImportantEdit="changeImportantInfoStatus"
          :pass-import-info="importantInfo"
          v-if="importantInfoStatus==1"
        ></important-info-edit-panel>-->
      </div>
    </div>
    <!-- 教育经历 -->
    <div class="profile-info-wrapper">
      <div class="profile-info">
        <p class="info-title">教育经历</p>
        <span
          class="profile-edit"
          v-if="btnObj.isShowEdit"
          @click="showEduEditPanel({isNationalUnified:1},0)"
        >
          <i class="el-icon-plus"></i>添加
        </span>
        <!-- 教育编辑面板 -->
        <edu-edit-panel
          :pass-edu-record="eduObj.item"
          :pass-open-view-type="eduObj.openViewType"
          v-if="eduObj.isShowEditView"
          @updateEduRecord="updateEduRecord"
          @closeEduEdit="closeEduEditPanel"
        ></edu-edit-panel>
        <!-- 教育编辑面板  end -->
        <div class="box-info">
          <ul class="per-info-ul">
            <li :key="index" v-for="(edu_exp,index) in eduRecordList">
              <!-- v-show="!eduObj.isShowEditView" 
              v-if="eduObj.openViewType === edu_exp.educationRecordId ? !eduObj.isShowEditView : eduObj.isShowEditView"-->
              <!-- 教育经历  obj-->
              <div class="per-info-obj-wrapper">
                <div v-if="!(hideEduItemIndex === index)">
                  <div class="info-operation" v-if="btnObj.isShowEdit">
                    <span
                      class="per-edit-item"
                      @click="showEduEditPanel(edu_exp,edu_exp.educationRecordId,index)"
                    >
                      <i class="el-icon-edit"></i>编辑
                    </span>
                    <span
                      class="per-delete-item"
                      @click="deleteEduRecord(edu_exp.educationRecordId)"
                    >
                      <i class="el-icon-delete"></i>删除
                    </span>
                  </div>

                  <div class="per-info-obj">
                    <span>学校名称：{{edu_exp.schoolName}}</span>
                    <span>就读时间：{{edu_exp.educationStartEndTime.split(',').join(' ')}}</span>
                    <span>所学专业：{{edu_exp.major}}</span>
                    <span>是否统招：{{edu_exp.isNationalUnified == 1 ? '统招' : '非统招'}}</span>
                    <span>学历/学位：{{edu_exp.diplomas}}</span>
                  </div>
                </div>
                <!-- 教育经历  obj  end-->
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <!-- 工作经历 -->
    <div class="profile-info-wrapper">
      <div class="profile-info">
        <p class="info-title">工作经历</p>
        <span class="profile-edit" @click="showWorkEditView({},0)" v-if="btnObj.isShowEdit">
          <i class="el-icon-plus"></i>添加
        </span>
        <!-- workRecord eidt -->
        <work-edit-panel
          v-if="workObj.isShowEditView"
          :pass-open-view-type="workObj.openViewType"
          :pass-work-record="workObj.item"
          @updateWorkRecord="updateWorkRecord"
          @closeWorkEditView="closeWorkEditView"
        ></work-edit-panel>
        <!-- workReocrd edit end -->
        <div class="box-info">
          <ul class="per-info-ul">
            <li :key="index" v-for="(work_exp,index) in personalDetailInfo.workExperienceList">
              <!-- workRecord obj -->
              <div class="per-info-obj-wrapper">
                <div v-if="!(hideWorkItemIndex == index)">
                  <div class="info-operation" v-if="btnObj.isShowEdit">
                    <span
                      class="per-edit-item"
                      @click="showWorkEditView(work_exp,work_exp.workExperienceId,index)"
                    >
                      <i class="el-icon-edit"></i>编辑
                    </span>
                    <span
                      class="per-delete-item"
                      @click="deleteWorkRecord(work_exp.workExperienceId)"
                    >
                      <i class="el-icon-delete"></i>删除
                    </span>
                  </div>
                  <div class="per-info-obj">
                    <span>工作单位：{{ work_exp.companyName }}</span>
                    <span>起止时间：{{ work_exp.workStartEndTime.split(',').join(' ') }}</span>
                    <span>职位：{{ work_exp.oldPosition}}</span>
                    <span>离职原因：{{ work_exp.reasonToLeave }}</span>
                    <span>证明人：{{ work_exp.certifier }}</span>
                    <span>证明人电话：{{ work_exp.certifierMobile }}</span>
                  </div>
                </div>
              </div>
              <!-- workRecord obj end -->
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- 培训经历-->
    <div class="profile-info-wrapper">
      <div class="profile-info">
        <p class="info-title">培训经历</p>
        <span class="profile-edit" @click="showTrainEdidtView({},0)" v-if="btnObj.isShowEdit">
          <i class="el-icon-plus"></i>添加
        </span>

        <!-- trainEdit  view-->
        <train-edit-view
          v-if="trainObj.isShowEditView"
          :pass-train-obj="trainObj.item"
          :pass-view-type="trainObj.openViewType"
          @updateTrainRecord="updateTrainRecord"
          @closeTrainEditView="closeTrainEditView"
        ></train-edit-view>
        <!--trainEdit view end-->
        <div class="box-info">
          <ul class="per-info-ul">
            <li :key="index" v-for="(train_exp,index) in personalDetailInfo.trainRecordList">
              <div class="per-info-obj-wrapper">
                <div v-if="!(hideTrainItemIndex == index)">
                  <div class="info-operation" v-if="btnObj.isShowEdit">
                    <span
                      class="per-edit-item"
                      @click="showTrainEdidtView(train_exp,train_exp.trainRecordId,index)"
                    >
                      <i class="el-icon-edit"></i>编辑
                    </span>
                    <span
                      class="per-delete-item"
                      @click="deleteTrainRecord(train_exp.trainRecordId)"
                    >
                      <i class="el-icon-delete"></i>删除
                    </span>
                  </div>

                  <div class="per-info-obj">
                    <span>培训时间：{{train_exp.trainStartEndTime.split(',').join(' ')}}</span>
                    <span>培训机构：{{train_exp.trainAgency}}</span>
                    <span>课程：{{train_exp.trainCourse}}</span>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <el-dialog title="个人信息" :visible.sync="dialogFormVisible">
      <fill-personal-info @tip="tip" :info="info"></fill-personal-info>
    </el-dialog>
  </div>
</template>
<script>
import {
  getPersonalDetailInfo,
  updatePersonalBaseInfo,
  updatePersonalImportantInfo,
  addEduRecord,
  updateEduRecord,
  deleteEduRecord,
  addWorkRecord,
  updateWorkRecord,
  deleteWorkRecord,
  addTrainRecord,
  updateTrainRecord,
  deleteTrainRecord
} from "@/api/staff";
import baseInfoEditView from "./components/baseInfoEdit.vue";
import impInfoEditView from "./components/importantInfoEdit.vue";
import eduEditView from "./components/eduEdit.vue";
import workEditView from "./components/workEdit.vue";
import trainEditView from "./components/trainEdit.vue";
import fillPersonalInfo from "@/views/system/fillPersonalInfo";

export default {
  name: "myInfo",
  components: {
    "base-info-edit-panel": baseInfoEditView,
    "important-info-edit-panel": impInfoEditView,
    "edu-edit-panel": eduEditView,
    "work-edit-panel": workEditView,
    "train-edit-view": trainEditView,
    fillPersonalInfo
  },
  data() {
    /*
      let workObj ={
            isShowEditView:false,//是否显示 工作经历编辑面板
            opneViewType:0,//打开编辑页面类型（添加，编辑）
            editRecord:{}//编辑页面初始化显示的数据
      }
      */
    return {
      uploadUrl: "http://172.18.19.3:8082/api/uploadImage",
      imageUrl: "",
      header: {
        Authorization: this.$store.state.user.token
      },
      btnObj: {
        isShowEdit: false, //编辑用户信息
        text: "编辑"
      },
      personalDetailInfo: {}, //全部信息对象

      baseInfo: {}, //基本信息对象
      importantInfo: {}, //重要信息对象
      eduRecordList: [], //教育记录数字

      baseInfoStatus: 0, // 0 显示view 1显示 edit
      importantInfoStatus: 0,
      eduObj: {
        isShowEditView: false,
        openViewType: 0,
        item: {}
      },
      hideEduItemIndex: -1,
      hideWorkItemIndex: -1,
      hideTrainItemIndex: -1,
      workObj: {
        isShowEditView: false,
        openViewType: 0,
        item: {}
      },
      trainObj: {
        isShowEditView: false,
        openViewType: 0,
        item: {}
      },
      editable: "",
      dialogFormVisible: false,
      info: ""
    };
  },
  created() {
    this.showPresonalInfo();
  },
  methods: {
    showPresonalInfo() {
      getPersonalDetailInfo().then(response => {
        this.personalDetailInfo = response.data.data;
        this.baseInfo = {
          name: this.personalDetailInfo.name,
          gender: this.personalDetailInfo.gender,
          politicsStatus: this.personalDetailInfo.politicsStatus,
          nation: this.personalDetailInfo.nation,
          empNo: this.personalDetailInfo.empNo,
          deptName: this.personalDetailInfo.deptName,
          hireDate: this.personalDetailInfo.hireDate,
          mobile: this.personalDetailInfo.mobile,
          email: this.personalDetailInfo.email,
          picture: this.personalDetailInfo.picture,
          positionName: this.personalDetailInfo.positionName
        };
        this.importantInfo = {
          empNo: this.personalDetailInfo.empNo,
          highestDegree: this.personalDetailInfo.highestDegree,
          maritalStatus: this.personalDetailInfo.maritalStatus,
          birthday: this.personalDetailInfo.birthday,
          currentAddress: this.personalDetailInfo.currentAddress,
          nationIdcard: this.personalDetailInfo.nationIdcard,
          address: this.personalDetailInfo.address,
          socialInsuranceAccount: this.personalDetailInfo
            .socialInsuranceAccount,
          bankName: this.personalDetailInfo.bankName,
          bankCard: this.personalDetailInfo.bankCard,
          emergencyContact: this.personalDetailInfo.emergencyContact,
          emergencyContactPhone: this.personalDetailInfo.emergencyContactPhone
        };
        this.eduRecordList = this.personalDetailInfo.educationRecordList;
        this.editable = this.personalDetailInfo.editable;
      });
    },
    //重要信息查看/编辑状态切换
    changeImportantInfoStatus(status) {
      this.dialogFormVisible = true;
      // this.info = this.baseInfo.name
    },
    // changeImportantInfoStatus(status) {
    //   this.importantInfoStatus = status;
    // },
    //  弹出框
    tip: function(data, infoData) {
      console.log(infoData)
      this.showPresonalInfo();
      this.dialogFormVisible = data;
      // this.$refs.info.resetFields();
    },
    //  图片上传，先执行 beforeAvatarUpload -- 在执行 action 提交 -- 执行  handleAvatarSuccess  or  uploadError
    //  上传成功
    handleAvatarSuccess(res, file) {
      if (res.code == 2000) {
        this.$store.commit("SET_AVATAR", file.response.data);
        this.imageUrl = file.response.data;
        this.baseInfo.picture = file.response.data;
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
    },
    //编辑基本信息
    updateBaseInfo(editBaseInfo) {
      console.log(
        "更新用户基本信息====================================================>" +
          editBaseInfo.mobile
      );
      updatePersonalBaseInfo(editBaseInfo).then(response => {
        console.log("更新基本信息操作结果：" + response.data.data);
        this.$message({
          message: "编辑用户基本信息操作成功！" + response.data.data,
          type: "success"
        });
        //刷新页面数据
        this.showPresonalInfo();
      });
    },
    //编辑重要信息
    // updateImportInfo(editImportantInfo) {
    //   updatePersonalImportantInfo(editImportantInfo).then(response => {
    //     this.$message({
    //       message: "编辑用户重要信息操作成功！" + response.data.data,
    //       type: "success"
    //     });
    //     //刷新页面数据
    //     this.showPresonalInfo();
    //   });
    // },
    //编辑教育经历（添加，更新）
    updateEduRecord(editEduRecord, viewType) {
      editEduRecord.staffEmpNo = this.personalDetailInfo.empNo;
      console.log(
        "updateEduRecord==================================》" +
          editEduRecord.staffEmpNo
      );
      //添加
      if (viewType === 0) {
        console.log("添加新的教育经历==================================》");
        addEduRecord(editEduRecord).then(response => {
          this.$message({
            message: "添加用户教育信息操作成功！" + response.data.data,
            type: "success"
          });
          //页面添加数据
          //  this.eduRecordList.splice(0,0,editEduRecord);
          this.showPresonalInfo();
          this.closeEduEditPanel();
        });
      } else {
        //更新
        console.log("更新教育经历=======================================》");
        updateEduRecord(editEduRecord).then(response => {
          this.$message({
            message: "更新用户教育信息操作成功！" + response.data.data,
            type: "success"
          });
          this.showPresonalInfo();
          this.closeEduEditPanel();
        });
      }
      //添加，更新操作合并，涉及表id生成的问题 TODO
      /*
         insertOrUpdateEduRecord(editEduRecord).then(response =>{
              this.$message({
                message: '添加或更新教育经历信息操作成功！'+response.data.data,
                type: 'success'
           });
         });
      */
    },
    //删除教育经历
    deleteEduRecord(eduRecordId) {
      this.$confirm("此操作将永久删除该条教育经历", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteEduRecord(eduRecordId).then(response => {
            console.log(response);
            this.$message({
              message: response.data.msg,
              type: "success"
            });
            this.showPresonalInfo();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除该条教育经历"
          });
        });
    },

    //添加或更新工作经历
    updateWorkRecord(editWorkRecord, viewType) {
      editWorkRecord.staffEmpNo = this.personalDetailInfo.empNo;
      if (viewType === 0) {
        addWorkRecord(editWorkRecord).then(response => {
          this.$message({
            message: response.data.msg,
            type: "success"
          });
          //关闭编辑视图，刷新数据，
          this.closeWorkEditView();
          this.showPresonalInfo();
        });
      } else {
        updateWorkRecord(editWorkRecord).then(response => {
          this.$message({
            message: response.data.msg + response.data.data,
            type: "success"
          });
          //关闭编辑视图，刷新数据，
          this.closeWorkEditView();
          this.showPresonalInfo();
        });
      }
    },
    //删除工作经历
    deleteWorkRecord(workRecordId) {
      console.log("delete work record id============>" + workRecordId);
      this.$confirm("此操作将永久删除该条工作经历", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        deleteWorkRecord(workRecordId)
          .then(response => {
            this.$message({
              message: response.data.msg,
              type: "success"
            });
            //刷新数据
            this.showPresonalInfo();
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除该条工作经历"
            });
          });
      });
    },

    updateTrainRecord(editTrainRecord, viewType) {
      editTrainRecord.staffEmpNo = this.personalDetailInfo.empNo;
      if (viewType === 0) {
        addTrainRecord(editTrainRecord).then(response => {
          this.$message({
            message: response.data.msg,
            type: "success"
          });
          //关闭编辑视图，刷新数据，
          this.closeTrainEditView();
          this.showPresonalInfo();
        });
      } else {
        updateTrainRecord(editTrainRecord).then(response => {
          this.$message({
            message: response.data.msg + response.data.data,
            type: "success"
          });
          //关闭编辑视图，刷新数据，
          this.closeTrainEditView();
          this.showPresonalInfo();
        });
      }
    },
    //  删除培训经历
    deleteTrainRecord(trainRecordId) {
      this.$confirm("此操作将永久删除该条教育经历", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteTrainRecord(trainRecordId).then(response => {
            this.$message({
              message: response.data.msg + response.data.data,
              type: "success"
            });
            //刷新数据
            this.showPresonalInfo();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除该条培训经历"
          });
        });
    },
    // 编辑按钮   8line
    changeBtn() {
      if (this.btnObj.isShowEdit) {
        this.btnObj.isShowEdit = false;
        this.baseInfoStatus = 0;
        this.importantInfoStatus = 0;
        this.eduObj.isShowEditView = false;
        this.workObj.isShowEditView = false;
        this.trainObj.isShowEditView = false;
        this.btnObj.text = "编辑";
      } else {
        this.btnObj.isShowEdit = true;
        this.btnObj.text = "取消";
      }
    },
    //基本信息查看/编辑状态切换
    changeBaseInfoStatus(status) {
      this.baseInfoStatus = status;
      this.imageUrl = this.baseInfo.picture;
    },
    //显示教育经历编辑页面，打开添加，编辑页面
    showEduEditPanel(eduRecord, openViewType, index) {
      let obj = this.eduObj;
      obj.isShowEditView = true;
      obj.openViewType = openViewType;
      obj.item = eduRecord;
      this.hideEduItemIndex = index;
    },
    //关闭教育经历编辑页面
    closeEduEditPanel() {
      this.eduObj.isShowEditView = false;
      this.hideEduItemIndex = -1;
    },
    //  添加、编辑 工作经历
    showWorkEditView(workRecord, openViewType, index) {
      let obj = this.workObj;
      obj.isShowEditView = true;
      obj.openViewType = openViewType;
      obj.item = workRecord;
      this.hideWorkItemIndex = index;
    },
    closeWorkEditView() {
      this.workObj.isShowEditView = false;
      this.hideWorkItemIndex = -1;
    },

    //显示培训编辑视图
    showTrainEdidtView(trainRecord, openViewType, index) {
      let obj = this.trainObj;
      obj.isShowEditView = true;
      obj.openViewType = openViewType;
      obj.item = trainRecord;
      this.hideTrainItemIndex = index;
    },
    //关闭培训编辑视图
    closeTrainEditView() {
      this.trainObj.isShowEditView = false;
      this.hideTrainItemIndex = -1;
    }
  }
};
</script>
<style scoped>
ul,
li {
  list-style: none;
}
ul {
  display: block;
  margin: 0;
  border: 0;
  padding: 0;
}
.profile-header {
  position: relative;
  width: 700px;
  height: 40px;
  margin-top: 25px;
  font-size: 26px;
  color: #333;
  letter-spacing: 1.21px;
  padding: 0 0 0 25px;
}
.profile-header .profile-edit-btn {
  position: fixed;
  top: 20%;
  right: 7%;
  z-index: 9999;
}
.profile-edit-btn .el-button {
  border-color: #3d9c8f;
  border-radius: 36px;
  padding: 11px;
}
.el-button /deep/ .el-icon-edit-outline {
  font-size: 18px;
  display: block;
}
.profile-page {
  padding: 0 80px 0 0;
}
.per-info-ul {
  word-wrap: break-word;
}
.box-info:first-child .per-info-ul li {
  position: relative;
  display: inline-block;
  padding: 10px 0;
  width: 405px;
}
.per-info-ul li {
  position: relative;
  display: block;
  padding: 10px 0;
  /* width: 405px; */
}
.per-info-ul li {
  width: 100%;
  display: inline-block;
}
.profile-base-wrapper {
  padding: 10px 25px 42px;
  background: #fff;
  /* border-bottom: 1px solid #eaedf1; */
  /*  new  */
  height: 340px;
}
.profile-base {
  border: 0.5px dashed #cccccc;
  border-radius: 50%;
}
.profile-base,
edit-profile-base {
  /* 原宽度为  800px */
  width: 120px;
  position: relative;
  color: #333;
  /* new */
  height: 120px;
  margin: 0 auto;
  text-align: center;
}
.profile-info-wrapper {
  padding: 5px 25px 42px;
  background: #fff;
  border-bottom: 1px solid #eaedf1;
}
.profile-info {
  width: 800px;
  position: relative;
  color: #333;
  margin: 0 auto;
}

.profile-edit {
  cursor: pointer;
  font-size: 18px;
  letter-spacing: 1.21px;
  position: absolute;
  top: 16px;
  right: -236px;
  color: #9191a5;
}
#edit {
  top: -180px;
  right: -236px;
}
.info-operation {
  position: absolute;
  top: 11px;
  right: -145px;
}
.per-edit-item,
.per-delete-item {
  cursor: pointer;
  font-size: 17px;
  letter-spacing: 1.21px;
  color: #9191a5;
}

.person-head {
  position: absolute;
  width: 120px;
  height: 120px;
  top: 0;
  left: 0;
  border: 1px solid #eaedf1;
  border-radius: 50%;
  -webkit-border-radius: 50%;
  -moz-border-radius: 50%;
}

.person-base {
  text-align: center;
  margin-top: 75px;
}
.person-base p {
  padding-top: 15px;
}
.person-base span {
  padding: 0 10px;
}

.info-title {
  margin-bottom: 29px;
  font-size: 24px;
  color: #333;
  letter-spacing: 1.21px;
  text-align: center;
}

.per-info-obj-wrapper {
  position: relative;
}
/* .per-info-obj {
  display: block;
} */
.per-info-obj-wrapper .per-info-obj span {
  display: inline-block;
  padding: 14px 20px;
}
/* new */
.box-info {
  width: 950px;
}
.box-info:first-child {
  width: 950px;
  padding: 0 0 0 126px;
}
.img {
  /* margin-top: 30px; */
  position: relative;
  top: 6px;
  right: -374px;
}
.img label {
  display: block;
  text-align: center;
  line-height: 50px;
  font-size: 14px;
  color: #606266;
}
.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  display: inline-block;
}
.el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
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
.el-upload:hover {
  border-color: #409eff;
}
</style>

