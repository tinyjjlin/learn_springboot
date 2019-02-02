import request from '@/utils/request';

export function loginByUsername(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}

export function getUserInfo() {
  return request({
    url: '/login/userInfo',
    method: 'get'
    //  params: { token }
  });
}

export function loginValidation(data){
  return request({
    url: '/loginValidation',
    method: 'post',
    data
  })
}
export function verifyLoginPwd(data) {
  
  return request({
    url: '/verifyLoginPwd',
    method: 'post',
    data
  })
}

