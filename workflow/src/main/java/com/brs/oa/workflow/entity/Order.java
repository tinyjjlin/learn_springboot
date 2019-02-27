package com.brs.oa.workflow.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author tiny lin
 * @date 2019/2/14
 */
@Entity(name = "brs_order")
@Data
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
