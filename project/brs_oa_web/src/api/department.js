/* 部门 api */
import request from '@/utils/request'

// 添加部门
export function saveDepartment(data){
    return request({
        url: "/department",
        method:"post",
        data
    })
}


// 删除一个部门
export function deleteDepartmentById(id){
    return request({
        url: "/department/"+id,
        method: "delete"
    })
}
