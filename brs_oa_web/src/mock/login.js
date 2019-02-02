import data from "./data"



export default {
  loginByUsername: config => {
    return data.loginByName
  },
  getUserInfo: config => {
    return data.userInfo
  },
  logout: () => 'success'
}
