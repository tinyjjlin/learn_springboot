<template>
  <div>
    <el-date-picker
      v-model="year"
      value-format="yyyy"
      type="year"
      placeholder="年"
      @change="changeYear"
    ></el-date-picker>
    <el-select v-model="month" placeholder="月" @change="changeMonth">
      <el-option
        v-for="item in monthList"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      ></el-option>
    </el-select>
    <el-select v-model="day" placeholder="日" @change="changeDay">
      <el-option v-for="item in dayList" :key="item.value" :label="item.label" :value="item.value"></el-option>
    </el-select>
  </div>
</template>

<script>
import { getMonthList } from "@/utils/shareData";
export default {
  data() {
    return {
      year: "",
      month: "",
      day: "",
      monthList: [],
      dayList: []
    };
  },
  computed: {
    chooseDay() {
      var days;
      switch (parseFloat(this.month)) {
        case 2: {
          var bfalg = parseFloat(this.year) % (parseFloat(this.year) % 100 ? 4 : 400) ? false : true;
          if (bfalg) {
            days = 29;
          } else {
            days = 28;
          }
          break;
        }
        case 4:
        case 6:
        case 9:
        case 11:
          days = 30;
          break;
        default:
          days = 31;
          break;
      }
      return days
    }
  },
  methods: {
    changeYear(data) {
      this.month = '';
      this.day = '';
      this.monthList = [];
      this.dayList = [];
      this.monthList = getMonthList();
    },
    changeMonth(data) {
      this.day = '';
      this.dayList = [];
      const newDay = this.chooseDay;
      for(let i = 1;i <= newDay;i++) {
        this.dayList.push({
          value: i,
          label: i
        })
      }
    },
    changeDay(data) {
      const month1 = '';
      const day1 = '';
      if(this.month < 10)
      {
        this.month1 = '0'+this.month
      }
      if(this.day < 10) 
      {
        this.day1 = "0"+this.day
      }
      console.log(this.year+'-'+this.month1+'-'+this.day1)
    }
  }
};
</script>
