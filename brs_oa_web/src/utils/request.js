/* eslint-disable indent */
/* eslint-disable keyword-spacing */
import axios from "axios";
import { Message, MessageBox } from "element-ui";
import store from "@/store";
import { getToken } from "@/utils/auth";
import router from "@/router";

// create an axios instance
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 5000 // request timeout
});

// request interceptor
service.interceptors.request.use(
  config => {
    // Do something before request is sent
    if (store.getters.token) {
      // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
      config.headers["Authorization"] = getToken();
    }
    return config;
  },
  error => {
    // Do something with request error
    console.log(error); // for debug
    Promise.reject(error);
  }
);



// response interceptor
service.interceptors.response.use(
  // response => {
  //   const result = response.data;

  //   console.log(result)

  //   if (result.code === 200) {
  //     return response;
  //   } else {
  //     //   store.dispatch('FedLogOut').then(() => {
  //     //       location.reload() // 为了重新实例化vue-router对象 避免bug
  //     //  })
  //     Message({
  //       message: result.msg,
  //       type: "error",
  //       duration: 5 * 1000
  //     });
  //     // 4004 token 过期
  //     if(result.code === 4004) {
  //       // MessageBox.confirm(result.msg, '提示', {
  //       //   confirmButtonText: '重新登录',
  //       //   cancelButtonText: '取消',
  //       //   type: 'warning'
  //       // }).then(() => {
  //         store.dispatch('LogOut').then(() => {
  //           location.reload() // 为了重新实例化vue-router对象 避免bug
  //         })
  //       // })
  //     }
  //     return Promise.reject('error')
  //   }
  // },
  /**
   * 下面的注释为通过在response里，自定义code来标示请求状态
   * 当code返回如下情况则说明权限有问题，登出并返回到登录页
   * 如想通过 xmlhttprequest 来状态码标识 逻辑可写在下面error中
   * 以下代码均为样例，请结合自生需求加以修改，若不需要，则可删除
   */
  response => {
    const res = response.data;
    if (res.code !== 200) {

      // 4004 token 过期
      if (res.code === 4004) {
        // 请自行在引入 MessageBox
        // import { Message, MessageBox } from 'element-ui'
        MessageBox.confirm(
          res.msg,
          "提示",
          {
            confirmButtonText: "重新登录",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          store.dispatch('LogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        }).catch(error => {
          console.log(error)
        });
      }
      return response;
    } else {
      return response;
    }
  },
  error => {
    console.log("err" + error); // for debug
    Message({
      message: error.message,
      type: "error",
      duration: 5 * 1000
    });
    return Promise.reject(error);
  }
);

export default service;
