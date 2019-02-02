<template>
    <div class="workflow-preview">
        <div class="main">
            <div class="cate-title">
                申请信息
            </div>
            <ul>
                <li>
                    <div class="name">申请人：</div>
                    <div class="content">{{ data.applicantName || '无'}}</div>
                </li>
                <li>
                    <div class="name">申请名称：</div>
                    <div class="content">{{ data.workName || '无'}}</div>
                </li>
                <li>
                    <div class="name">请假类型：</div>
                    <div class="content">
                        <!-- {{data.flowType}} -->
                        {{  data.flowType ?  applyTypes[data.flowType-1]? applyTypes[data.flowType-1].label : "未知"  : "未知"  }}
                    </div>
                </li>
                <li>
                    <div class="name">开始时间：</div>
                    <div class="content">{{ data.startTime || '无'}}</div>
                </li>
                <li>
                    <div class="name">结束时间：</div>
                    <div class="content">{{ data.endTime || '无'}}</div>
                </li>
                <li>
                    <div class="name">请假时长：</div>
                    <div class="content">{{ data.duration || '无'}}</div>
                </li>
                <li>
                    <div class="name">图片：</div>
                    <div class="content">
                        <ul class="image-box" id="images" v-if="data.proveImg">
                            <li v-for="(v,k) in data.proveImg" :key="k" ><img :src="'http://172.18.19.3:8066/'+v" alt="" width="120" @click="preview"></li>
                        </ul>
                        <span v-else>无</span>
                    </div>
                </li>
                <li>
                    <div class="name">请假说明：</div>
                    <div class="content">{{ data.content || '无'}}</div>
                </li>
                <li>
                    <div class="name">审批状态：</div>
                    <div class="content">{{data.approvalStatus==2 ? "未通过" : data.approvalStatus==1? "已通过" : "等待审批" }}</div>
                </li>
                <li>
                    <div class="name">审批人：</div>
                    <div class="content">{{ data.approverName || '无'}}</div>
                </li>
                <li>
                    <div class="name">申请时间：</div>
                    <div class="content">{{ data.applicationTime  || '无'}}</div>
                </li>
                <li>
                    <div class="name">审批时间：</div>
                    <div class="content">{{ data.approvalTime || '无' }}</div>
                </li>
            </ul>
            
            <div class="buchong" v-if="data.buchong">
                <div class="cate-title cate-border">
                    补充材料
                </div>
                <ul>
                    <li>
                        <div class="name">name：</div>
                        <div class="content">value</div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>
<script>
import { getStaffIdinfo } from "@/api/staff";
import { getAttendanceApplyTypes } from "@/api/workProcess";
import Viewer from 'viewerjs';
import 'viewerjs/dist/viewer.css'

export default {
  name: "workflowPreview",
  data() {
    return {
        data:{},
        applyTypes:[],
    };
  },
  props: ["serialNumber","workflowInfo"],
  created() {

    // 获取请假类型
    getAttendanceApplyTypes()
    .then(res=>{
        if(res.data.code==200){
            this.applyTypes = res.data.data.map(o=>{return{value:o.code, label:o.name}});
        }else{
            this.$message.error(this.data.msg)
        }
    })
    .catch(error=>{
        this.$message.error(error)
    })

    this.data = Object.assign({},this.workflowInfo)
 
    if(this.data.proveImg){   
        this.data.proveImg = this.data.proveImg.split("|");
    }else{
        this.data.proveImg = "";
    }

  },
  methods: {
    showWorkflowInfo() {
    
    //   getWorkflowInfo(this.serialNumber).then(response => {
    //     this.workflowInfo = response.data.data;
    //   });
    },
    preview(){
        new Viewer(document.getElementById('images'));
    }
  },
  computed:{
      
  },
  watch:{
    workflowInfo(){
        this.data = Object.assign({},this.workflowInfo)

        if(this.data.proveImg){    
            this.data.proveImg = this.data.proveImg.split("|");
        }else{
            this.data.proveImg = "";
        }
    }
  }
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.workflow-preview {
    padding:10px;
    .main {
        padding:5px 20px;
        .cate-title {
            margin-bottom: 29px;
            font-size: 24px;
            color: #333;
            letter-spacing: 1.21px;
        }
        .cate-border {
            border-top:2px solid #ccc;
            padding-top:22px;
        }
        ul {
            list-style: none;
            padding:0px;
            margin-bottom:38px;
            li {
                display:flex;
                margin-bottom:20px;
                .name {
                    width:100px;
                }
                .content {
                    flex:1;
                }
            }
        }
        .image-box li{
            float:left;
            width: 120px;
            height: 120px;
            margin-right:50px;
            cursor: pointer;
        }
    }
}

</style>

