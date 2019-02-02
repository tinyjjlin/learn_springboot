<template>
  <div class="profile-page">
    <!-- 个人基本信息-->
    <div class="profile-base-wrapper">
      <div class="profile-base">
        <!-- <p class="info-title">基本信息</p> -->
        <div class="profile-base">
          <img class="person-head" :src="'http://172.18.19.3:8066/' + personalDetailInfo.picture">
          <div class="person-base">
            <p>
              <span>{{ personalDetailInfo.name }}</span>
              <span>{{ personalDetailInfo.gender}}</span>
              <span>{{ personalDetailInfo.politicsStatus }}</span>
              <span>{{ personalDetailInfo.nation }}</span>
            </p>
            <p>
              <span>{{ personalDetailInfo.empNo }}</span>
              <span>{{ personalDetailInfo.deptName }}</span>
              <span>{{ personalDetailInfo.positionName }}</span>
              <span>{{ personalDetailInfo.hireDate }}</span>
            </p>
            <p>
              <span>{{ personalDetailInfo.mobile }}</span>
              <span>{{ personalDetailInfo.email }}</span>
            </p>
          </div>
        </div>
      </div>
    </div>
    <!--个人基本信息  end -->
    <!--个人重要信息 -->
    <div class="profile-info-wrapper">
      <div class="profile-info">
        <p class="info-title">重要信息</p>
        <div>
          <ul class="per-info-ul">
            <li>
              <span>最高学历：{{ personalDetailInfo.highestDegree }}</span>
            </li>
            <li>
              <span>婚姻状况：{{ personalDetailInfo.maritalStatus}}</span>
            </li>
            <li>
              <span>现在居住地址：{{ personalDetailInfo.currentAddress }}</span>
            </li>
            <li>
              <span>身份证号：{{ personalDetailInfo.nationIdcard }}</span>
            </li>
            <li>
              <span>户籍：{{ personalDetailInfo.address }}</span>
            </li>
            <li>
              <span>社保账号：{{ personalDetailInfo.socialInsuranceAccount }}</span>
            </li>
                <li>
              <span>银行名称：{{ personalDetailInfo.bankName }}</span>
            </li>
            <li>
              <span>银行账号：{{ personalDetailInfo.bankCard }}</span>
            </li>
            <li id="local">
              <span>本地紧急联系人：{{ personalDetailInfo.emergencyContact }}</span>
              <span id="localMobile">本地紧急联系人电话：{{ personalDetailInfo.emergencyContactPhone }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- 教育经历 -->
    <div class="profile-info-wrapper">
      <div class="profile-info">
        <p class="info-title">教育经历</p>
        <div>
          <ul class="per-info-ul">
            <li :key="index" v-for="(edu_exp,index) in personalDetailInfo.educationRecordList">
              <span>{{ edu_exp.schoolName }}</span>
              <span>{{ edu_exp.educationStartEndTime }}</span>
              <span>{{ edu_exp.major }}</span>
              <span>{{ edu_exp.diplomas }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <!-- 工作经历 -->
    <div class="profile-info-wrapper">
      <div class="profile-info">
        <p class="info-title">工作经历</p>
        <div>
          <ul class="per-info-ul">
            <li :key="index" v-for="(work_exp,index) in personalDetailInfo.workExperienceList">
              <span>{{ work_exp.workStartEndTime}}</span>
              <span>{{ work_exp.companyName }}</span>
              <span>{{ work_exp.oldPosition }}</span>
              <span>{{ work_exp.reasonToLeave }}</span>
              <span>{{ work_exp.certifier }}</span>
              <span>{{ work_exp.certifierMobile }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- 培训经历-->
    <div class="profile-info-wrapper">
      <div class="profile-info">
        <p class="info-title">培训经历</p>
        <div>
          <ul class="per-info-ul">
            <li :key="index" v-for="(train_exp,index) in personalDetailInfo.trainRecordList">
              <span>{{ train_exp.trainStartEndTime }}</span>
              <span>{{ train_exp.trainAgency }}</span>
              <span>{{ train_exp.trainCourse }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { getStaffIdinfo } from "@/api/staff";
export default {
  name: "myInfo",
  data() {
    return {
      personalDetailInfo: {},
      no: this.empNo
    };
  },
  props: ["empNo"],
  created() {
    this.showPresonalInfo(0);
  },
  methods: {
    showPresonalInfo(index) {
      if(index !== 0){
        this.no = index
      }
      getStaffIdinfo(this.no).then(response => {
        this.personalDetailInfo = response.data.data;
      });
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
.profile-page {
  padding: 0 40px;
}
.per-info-ul {
  word-wrap: break-word;
}
.per-info-ul li {
  display: flex;
  padding: 10px 0;
}
#local {
  display: block;
}
.per-info-ul span {
  margin-right: 20px;
}
#localMobile {
  display: block;
  margin-top: 16px;
}
.profile-base-wrapper {
  padding: 10px 25px 42px;
  background: #fff;
  border-bottom: 1px solid #eaedf1;
}
.profile-base {
  width: 800px;
  position: relative;
  color: #333;
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
  height: 150px;
  margin-left: 140px;
}

.info-title {
  margin-bottom: 29px;
  font-size: 24px;
  color: #333;
  letter-spacing: 1.21px;
}
</style>

