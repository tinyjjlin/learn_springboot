/* eslint-disable spaced-comment */
/* eslint-disable indent */
module.exports = {
  proxy: {
    "/api": {
      //将www.exaple.com印射为/apis
        // target: 'http://172.18.19.3:8082', // 接口域名
       target: "http://172.18.101.114:8080/", // 接口域名
      secure: false, // 如果是https接口，需要配置这个参数
      changeOrigin: true //是否跨域
      // pathRewrite: {
      //     '^/api': ''   //需要rewrite的,
      //  }
    }
  }
};


