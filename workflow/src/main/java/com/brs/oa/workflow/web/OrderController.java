package com.brs.oa.workflow.web;

import com.brs.oa.workflow.entity.Order;
import com.brs.oa.workflow.model.RestResult;
import com.brs.oa.workflow.repository.OrderRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tiny lin
 * @date 2019/2/14
 */
@RestController
@Api
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @ApiOperation("创建新的订单")
    @PostMapping("/api/order")
    public RestResult createOrder(@RequestBody  Order order){
        orderRepository.save(order);

        return new RestResult(200,"create new order success!" );
    }
}
