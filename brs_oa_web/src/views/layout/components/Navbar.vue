<template>
  <div class="navbar">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>

    <breadcrumb class="breadcrumb-container"/>

    <div class="right-menu right-box">
      <!-- 当设备为手机时 不显示 -->
      <template v-if="device!=='mobile'">
        <error-log class="errLog-container right-menu-item"/>
        <!-- 是否全屏-->
        <el-tooltip :content="$t('navbar.screenfull')" effect="dark" placement="bottom">
          <screenfull class="screenfull right-menu-item"/>
        </el-tooltip>
        <!-- 文字大小  -->
        <!-- <el-tooltip :content="$t('navbar.size')" effect="dark" placement="bottom">
          <size-select class="international right-menu-item"/>
        </el-tooltip> -->
        <!-- 选择语言 -->
        <!-- <lang-select class="international right-menu-item"/> -->
        <!-- 主题 -->
        <!-- <el-tooltip :content="$t('navbar.theme')" effect="dark" placement="bottom">
          <theme-picker class="theme-switch right-menu-item"/>
        </el-tooltip> -->
      </template>
      <!-- 签到 -->
      <el-button class="theme-switch right-menu-item sign-btn" size="mini" type="warning" @click="saveClockRecord('打卡时间')"><svg-icon icon-class="sign" />  打卡</el-button>

      <div class="user-info">
        <span class="username">{{name}}</span>
        <span class="expNo">{{empNo}}</span>
      </div>

      
      <!-- <el-tooltip content="签退" placement="bottom"> -->
        <!-- <el-button class="theme-switch right-menu-item" type="danger" circle @click="saveClockRecord('签退')"><svg-icon icon-class="sign out" /></el-button> -->
      <!-- </el-tooltip> -->
      <!-- 用户头像 -->
      <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="avatar-wrapper">
          <img v-if="isAvatar" :src="'http://172.18.19.3:8066/'+avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
          <img v-if="!isAvatar" :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown">
          <!-- 首页 -->
          <router-link to="/">
            <el-dropdown-item>
              {{ $t('navbar.dashboard') }}
            </el-dropdown-item>
          </router-link>
          <!-- 退出 -->
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">{{ $t('navbar.logOut') }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import LangSelect from '@/components/LangSelect'
import ThemePicker from '@/components/ThemePicker'
import {saveClockRecord} from "@/api/clockRecord"
import {formatTimeToStr} from "@/utils/tools"
import axios from 'axios'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    SizeSelect,
    LangSelect,
    ThemePicker
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'name',
      'avatar',
      'empNo',
      'device',
      'clcokIsUpdate'
    ])
  },
  data() {
    return {
      isAvatar: true
    }
  },
  created() {
    if(this.avatar === 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'){
      this.isAvatar = false
    }
  },
  methods: {
    // 保存签到
    saveClockRecord(type){
      let dateObj = new Date();

      let clockDay = formatTimeToStr(dateObj,"yyyy-MM-dd");
      // let clockTime = formatTimeToStr(dateObj,"hh:mm:ss");
      let data = {clockDay:clockDay,clockTime:formatTimeToStr(dateObj,"yyyy-MM-ddThh:mm:ssZ"),clockType:"系统打卡"};
      // let data = {clockDay:"2019-01-17",clockTime:"2019-01-15T08:15:00Z",clockType:"系统打卡"};
      // let data = {clockDay:"2019-01-17",clockTime:"2019-01-15T18:30:00Z",clockType:"系统打卡"};
      
      this.$confirm(formatTimeToStr(dateObj, "yyyy-MM-dd   hh:mm:ss"), type, {
        confirmButtonText: '确定',
        cancelButtonText: '取消'})
      .then(()=>{
        saveClockRecord(data)
          .then(response=>{
            
            if(response.data.code==200){
              this.$message({
                type: 'success',
                message: response.data.msg
              });
              this.$store.commit('CHANGE_CLOCK_UPDATE_STATUS')
              console.log(this.clockRecord)
            }else{
              this.$message.error(response.data.msg)
            }
          })
          .catch((error)=>{
            this.$message.error(error)
          })       
      })
      .catch(()=>{
        this.$message({
          type:"info",
          message:"已取消"+type
        })
      })
        
    },
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        localStorage.removeItem("lock")
        location.reload()// In order to re-instantiate the vue-router object to avoid bugs
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 57px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 65px;
    height: 57px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container{
    float: left;
    line-height: 57px;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .right-box {
    display:flex;
    justify-content: flex-start;
    align-items: center;
  }
  .right-menu {
    float: right;
    height: 100%;
    .sign-btn {
      font-size:14px;
      margin:0 !important;
      padding:7px 10px !important;
    }
    .user-info {
      min-width:70px;
      display: flex;
      flex-flow: column;
      text-align:center;
      .username {
        font-size:14px;
        padding:2px;
      }
    }
    &:focus{
     outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international{
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 57px;
      margin-right: 30px;
      .avatar-wrapper {
        margin-top: 5px;
        position: relative;
        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
