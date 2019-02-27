package com.brs.oa.workflow.repository;

import com.brs.oa.workflow.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author tiny lin
 * @date 2019/2/14
 */
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findOrderById(Long id);

}
