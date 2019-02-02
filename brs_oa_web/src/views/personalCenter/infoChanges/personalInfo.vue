<template>
  <!-- 表单 -->
  <div id="info">
    <el-form-item
      label="姓名"
      prop="name"
    >
    <!-- :rules="[{ required: false, message: '请输入姓名', trigger: 'blur' },
      { type: regexp, message: '请输入正确的格式', trigger: 'blur' }]" -->
      <el-input v-model="personalInfo.name" key="personalInfo.name"></el-input>
    </el-form-item>

    <el-form-item label="性别" prop="gender">
      <el-radio-group v-model="personalInfo.gender" @click="changeRadio">
        <el-radio :label="0">男</el-radio>
        <el-radio :label="1">女</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="政治面貌" prop="politicsStatus">
      <el-select v-model="personalInfo.politicsStatus" placeholder="请选择政治面貌">
        <el-option
          v-for="item in politicsStatus"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="民族" prop="nation">
      <el-select v-model="personalInfo.nation" placeholder="请选择民族">
        <el-option v-for="item in nation" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
    </el-form-item>
    
  </div>
</template>

<script>
export default {
  name: "personalInfo",
  props: ["personalInfo"],
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
    const checkMobile = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("手机号不能为空！"));
      } else if (!/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(value)) {
        callback(new Error("输入的手机号格式不正确！"));
      } else {
        callback();
      }
    };
    return {
      info: {
        name: "",
        gender: "",
        politicsStatus: "",
        nation: "",
        mobile: ""
      },
      nation: [
        {
          value: "1",
          label: "汉族"
        },
        {
          value: "2",
          label: "蒙古族"
        },
        {
          value: "3",
          label: "回族"
        },
        {
          value: "4",
          label: "藏族"
        },
        {
          value: "5",
          label: "维吾尔族"
        },
        {
          value: "6",
          label: "苗族"
        },
        {
          value: "7",
          label: "彝族"
        },
        {
          value: "8",
          label: "壮族"
        },
        {
          value: "9",
          label: "布依族"
        },
        {
          value: "10",
          label: "朝鲜族"
        },
        {
          value: "11",
          label: "满族"
        },
        {
          value: "12",
          label: "侗族"
        },
        {
          value: "13",
          label: "瑶族"
        },
        {
          value: "14",
          label: "白族"
        },
        {
          value: "15",
          label: "土家族"
        },
        {
          value: "16",
          label: "哈尼族"
        },
        {
          value: "17",
          label: "哈萨克族"
        },
        {
          value: "18",
          label: "傣族"
        },
        {
          value: "19",
          label: "黎族"
        },
        {
          value: "20",
          label: "傈僳族"
        },
        {
          value: "21",
          label: "佤族"
        },
        {
          value: "22",
          label: "畲族"
        },
        {
          value: "23",
          label: "高山族"
        },
        {
          value: "24",
          label: "拉祜族"
        },
        {
          value: "25",
          label: "水族"
        },
        {
          value: "26",
          label: "东乡族"
        },
        {
          value: "27",
          label: "纳西族"
        },
        {
          value: "28",
          label: "景颇族"
        },
        {
          value: "29",
          label: "柯尔克孜族"
        },
        {
          value: "30",
          label: "土族"
        },
        {
          value: "31",
          label: "达斡尔族"
        },
        {
          value: "32",
          label: "仫佬族"
        },
        {
          value: "33",
          label: "羌族"
        },
        {
          value: "34",
          label: "布朗族"
        },
        {
          value: "35",
          label: "撒拉族"
        },
        {
          value: "36",
          label: "毛南族"
        },
        {
          value: "37",
          label: "仡佬族"
        },
        {
          value: "38",
          label: "锡伯族"
        },
        {
          value: "39",
          label: "阿昌族"
        },
        {
          value: "40",
          label: "普米族"
        },
        {
          value: "41",
          label: "塔吉克族"
        },
        {
          value: "42",
          label: "怒族"
        },
        {
          value: "43",
          label: "乌孜别克族"
        },
        {
          value: "44",
          label: "俄罗斯族"
        },
        {
          value: "45",
          label: "鄂温克族"
        },
        {
          value: "46",
          label: "德昂族"
        },
        {
          value: "47",
          label: "保安族"
        },
        {
          value: "48",
          label: "裕固族"
        },
        {
          value: "49",
          label: "京族"
        },
        {
          value: "50",
          label: "塔塔尔族"
        },
        {
          value: "51",
          label: "独龙族"
        },
        {
          value: "52",
          label: "鄂伦春族"
        },
        {
          value: "53",
          label: "赫哲族"
        },
        {
          value: "54",
          label: "门巴族"
        },
        {
          value: "55",
          label: "珞巴族"
        },
        {
          value: "56",
          label: "基诺族"
        }
      ],
      politicsStatus: [
        {
          value: "13",
          label: "群众(现称普通居民，与居民身份证相对应)"
        },
        {
          value: "01",
          label: "中共党员"
        },
        {
          value: "02",
          label: "中共预备党员"
        },
        {
          value: "03",
          label: "共青团员"
        },
        {
          value: "04",
          label: "民革党员"
        },
        {
          value: "05",
          label: "民盟盟员"
        },
        {
          value: "06",
          label: "民建会员"
        },
        {
          value: "07",
          label: "民进会员"
        },
        {
          value: "08",
          label: "农工党党员"
        },
        {
          value: "09",
          label: "致公党党员"
        },
        {
          value: "10",
          label: "九三学社社员"
        },
        {
          value: "11",
          label: "台盟盟员"
        },
        {
          value: "12",
          label: "无党派人士"
        }
      ]
    };
  },
  created() {
    this.info = this.personalInfo;
  },
  updated() {
    console.log(this.$refs)
  },
  methods: {
    changeRadio() {
      console.log();
    }
  }
};
</script>