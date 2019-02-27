package com.tiny.entity;

import lombok.Data;

/**
 * @author tiny lin
 * @date 2019/2/18
 */
@Data
public class StaffEntity {
    private String id;
    private String name;

    public StaffEntity(String id,String name){this.id = id; this.name =  name;}

}
