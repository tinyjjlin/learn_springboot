<template>
  <div class="dashboard-editor-container">
    <div class=" clearfix">
      <pan-thumb :image="'http://172.18.19.3:8066/' + avatar" style="float: left"> Your roles:
        <span v-for="item in roles" :key="item" class="pan-info-roles">{{ item }}</span>
      </pan-thumb>
      <div class="info-container">
        <span class="display_name">{{ name }}</span>
        <span style="font-size:20px;padding-top:20px;display:inline-block;">南京贝若偲生物科技有限公司</span>
      </div>
    </div>
    <div>
      <img :src="emptyGif" class="emptyGif">
    </div>
    <el-button type="primary" @click="changeBtn">用户信息</el-button>

    <el-dialog title="个人信息" @close="closeDialog" :visible.sync="dialogFormVisible">
      <fill-personal-info @tip="tip" :info='info'></fill-personal-info>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanThumb from '@/components/PanThumb'
import fillPersonalInfo from '@/views/system/fillPersonalInfo'

export default {
  name: 'DashboardEditor',
  components: { PanThumb, fillPersonalInfo },
  data() {
    return {
      emptyGif: 'https://wpimg.wallstcn.com/0e03b7da-db9e-4819-ba10-9016ddfdaed3',
      dialogFormVisible: false,
      info: ''
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles'
    ])
  },
  methods: {
    changeBtn() {
      this.dialogFormVisible = true
    },
    tip: function(data) {
      this.dialogFormVisible = data
      this.$refs.info.resetFields()
    },
    closeDialog() {
      this.$refs.info.clearValidate()
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .emptyGif {
    display: block;
    width: 45%;
    margin: 0 auto;
  }

  .dashboard-editor-container {
    background-color: #e3e3e3;
    min-height: 100vh;
    padding: 50px 60px 0px;
    .pan-info-roles {
      font-size: 12px;
      font-weight: 700;
      color: #333;
      display: block;
    }
    .info-container {
      position: relative;
      margin-left: 190px;
      height: 150px;
      line-height: 200px;
      .display_name {
        font-size: 48px;
        line-height: 48px;
        color: #212121;
        position: absolute;
        top: 25px;
      }
    }
  }
</style>
