package com.brs.oa.workflow.web;

import com.brs.oa.workflow.model.ResultBean;
import com.brs.oa.workflow.model.TaskRepresentation;
import com.brs.oa.workflow.service.OrderProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tiny lin
 * @date 2019/2/14
 */
@RestController
@Api
public class OrderProcessController {

    @Autowired
    private OrderProcessService orderProcessService;

    /**
     * 当客户创建新的订单后便开启了订单流程
     * @param orderId
     * @return
     */
    @ApiOperation("启动订单流程")
    @PostMapping("/api/process")
    public ResultBean startProcessInstance(Long orderId){
       String processInstanceId = orderProcessService.startProcess(orderId);
        return new ResultBean(200,"start process success!" ,processInstanceId);
    }

    @ApiOperation("获取订单列表")
    @GetMapping("/api/order")
    public ResultBean getTaskList(@RequestParam("assignee") String assignee){
        List<TaskRepresentation> tasks = orderProcessService.getTasks(assignee);
        return new ResultBean(200,"获取订单列表",tasks );
    }
    /**
     * 经理确认订单
     * @return
     */
    @ApiOperation("确认订单")
    @GetMapping("/api/confirmOrder")
    public ResultBean confirmOrder(@RequestParam("taskId")String taskId,@RequestParam("editorialDirectorId")Integer editorialDirectorId ){
         orderProcessService.confirmOrder(taskId,editorialDirectorId);
        return new ResultBean(200,"confirm order" );
    }



    /**
     * 编辑主管分派订单
     * @return
     */
    @ApiOperation("分派订单")
    @GetMapping("/api/dispatchOrder")
    public ResultBean dispatchOrder(@RequestParam("taskId")String taskId,@RequestParam("editorialStaffId")Integer editorialStaffId ){
            orderProcessService.dispatchOrder(taskId, editorialStaffId);
        return new ResultBean(200,"dispatch order" );
    }


    /**
     * 编辑出草稿
     * @return
     */
    @ApiOperation("编辑出草稿")
    @GetMapping("/api/writeDraft")
    public ResultBean writeDraft(@RequestParam("taskId")String taskId,@RequestParam("laboratoryStaffId")Integer laboratoryStaffId ){
        orderProcessService.writeDraft(taskId,laboratoryStaffId );
        return new ResultBean(200,"write a draft");
    }

    /**
     * 编辑完成文章
     * @return
     */
    @ApiOperation("编辑完成文章")
    @GetMapping("/api/completeArticle")
    public ResultBean completeArticle(@RequestParam("taskId")String taskId ){
        orderProcessService.completeArticle(taskId);
        return new ResultBean(200,"completeArticle");
    }

    /**
     * 实验人员处理数据
     * @param taskId
     * @return
     */
    @ApiOperation("实验人员处理数据")
    @GetMapping("/api/handleData")
    public ResultBean handleData(@RequestParam("taskId")String taskId){
        orderProcessService.handleData(taskId);
        return new ResultBean(200,"handle data");
    }
}
