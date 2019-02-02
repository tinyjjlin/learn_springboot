const bankName = [
  {
    label: '中国工商银行',
    value: '中国工商银行'
  },
  {
    label: '中国建设银行',
    value: '中国建设银行'
  },
  {
    label: '中国银行',
    value: '中国银行'
  },
  {
    label: '交通银行',
    value: '交通银行'
  },
  {
    label: '中国农业银行',
    value: '中国农业银行'
  },
  {
    label: '招商银行',
    value: '招商银行'
  },
  {
    label: '中国邮政储蓄银行',
    value: '中国邮政储蓄银行'
  },
  {
    label: '中国光大银行',
    value: '中国光大银行'
  },
  {
    label: '中国民生银行',
    value: '中国民生银行'
  },
  {
    label: '平安银行',
    value: '平安银行'
  },
  {
    label: '浦发银行',
    value: '浦发银行'
  },
  {
    label: '中信银行',
    value: '中信银行'
  },
  {
    label: '兴业银行',
    value: '兴业银行'
  },
  {
    label: '华夏银行',
    value: '华夏银行'
  },
  {
    label: '广发银行',
    value: '广发银行'
  }
]
//  返回银行名称
export function getBankName() {
  return bankName
}
const politicsStatus = [
  {
    value: '群众',
    label: '群众'
  },
  {
    value: '中共党员',
    label: '中共党员'
  },
  {
    value: '中共预备党员',
    label: '中共预备党员'
  },
  {
    value: '共青团员',
    label: '共青团员'
  },
  {
    value: '民革党员',
    label: '民革党员'
  },
  {
    value: '民盟盟员',
    label: '民盟盟员'
  },
  {
    value: '民建会员',
    label: '民建会员'
  },
  {
    value: '民进会员',
    label: '民进会员'
  },
  {
    value: '农工党党员',
    label: '农工党党员'
  },
  {
    value: '致公党党员',
    label: '致公党党员'
  },
  {
    value: '九三学社社员',
    label: '九三学社社员'
  },
  {
    value: '台盟盟员',
    label: '台盟盟员'
  },
  {
    value: '无党派人士',
    label: '无党派人士'
  }
]
//  政治面貌
export function getPoliticsStatus() {
  return politicsStatus
}
const nation = [
  {
    value: '汉族',
    label: '汉族'
  },
  {
    value: '蒙古族',
    label: '蒙古族'
  },
  {
    value: '回族',
    label: '回族'
  },
  {
    value: '藏族',
    label: '藏族'
  },
  {
    value: '维吾尔族',
    label: '维吾尔族'
  },
  {
    value: '苗族',
    label: '苗族'
  },
  {
    value: '彝族',
    label: '彝族'
  },
  {
    value: '壮族',
    label: '壮族'
  },
  {
    value: '布依族',
    label: '布依族'
  },
  {
    value: '朝鲜族',
    label: '朝鲜族'
  },
  {
    value: '满族',
    label: '满族'
  },
  {
    value: '侗族',
    label: '侗族'
  },
  {
    value: '瑶族',
    label: '瑶族'
  },
  {
    value: '白族',
    label: '白族'
  },
  {
    value: '土家族',
    label: '土家族'
  },
  {
    value: '哈尼族',
    label: '哈尼族'
  },
  {
    value: '哈萨克族',
    label: '哈萨克族'
  },
  {
    value: '傣族',
    label: '傣族'
  },
  {
    value: '黎族',
    label: '黎族'
  },
  {
    value: '傈僳族',
    label: '傈僳族'
  },
  {
    value: '佤族',
    label: '佤族'
  },
  {
    value: '畲族',
    label: '畲族'
  },
  {
    value: '高山族',
    label: '高山族'
  },
  {
    value: '拉祜族',
    label: '拉祜族'
  },
  {
    value: '水族',
    label: '水族'
  },
  {
    value: '东乡族',
    label: '东乡族'
  },
  {
    value: '纳西族',
    label: '纳西族'
  },
  {
    value: '景颇族',
    label: '景颇族'
  },
  {
    value: '柯尔克孜族',
    label: '柯尔克孜族'
  },
  {
    value: '土族',
    label: '土族'
  },
  {
    value: '达斡尔族',
    label: '达斡尔族'
  },
  {
    value: '仫佬族',
    label: '仫佬族'
  },
  {
    value: '羌族',
    label: '羌族'
  },
  {
    value: '布朗族',
    label: '布朗族'
  },
  {
    value: '撒拉族',
    label: '撒拉族'
  },
  {
    value: '毛南族',
    label: '毛南族'
  },
  {
    value: '仡佬族',
    label: '仡佬族'
  },
  {
    value: '锡伯族',
    label: '锡伯族'
  },
  {
    value: '阿昌族',
    label: '阿昌族'
  },
  {
    value: '普米族',
    label: '普米族'
  },
  {
    value: '塔吉克族',
    label: '塔吉克族'
  },
  {
    value: '怒族',
    label: '怒族'
  },
  {
    value: '乌孜别克族',
    label: '乌孜别克族'
  },
  {
    value: '俄罗斯族',
    label: '俄罗斯族'
  },
  {
    value: '鄂温克族',
    label: '鄂温克族'
  },
  {
    value: '德昂族',
    label: '德昂族'
  },
  {
    value: '保安族',
    label: '保安族'
  },
  {
    value: '裕固族',
    label: '裕固族'
  },
  {
    value: '京族',
    label: '京族'
  },
  {
    value: '塔塔尔族',
    label: '塔塔尔族'
  },
  {
    value: '独龙族',
    label: '独龙族'
  },
  {
    value: '鄂伦春族',
    label: '鄂伦春族'
  },
  {
    value: '赫哲族',
    label: '赫哲族'
  },
  {
    value: '门巴族',
    label: '门巴族'
  },
  {
    value: '珞巴族',
    label: '珞巴族'
  },
  {
    value: '基诺族',
    label: '基诺族'
  }
]
//   民族
export function getNation() {
  return nation
}
const monthList = [
  {
    value: '1',
    label: '1'
  },
  {
    value: '2',
    label: '2'
  },
  {
    value: '3',
    label: '3'
  },
  {
    value: '4',
    label: '4'
  },
  {
    value: '5',
    label: '5'
  },
  {
    value: '6',
    label: '6'
  },
  {
    value: '7',
    label: '7'
  },
  {
    value: '8',
    label: '8'
  },
  {
    value: '9',
    label: '9'
  },
  {
    value: '10',
    label: '10'
  },
  {
    value: '11',
    label: '11'
  },
  {
    value: '12',
    label: '12'
  }
]
export function getMonthList() {
  return monthList
}