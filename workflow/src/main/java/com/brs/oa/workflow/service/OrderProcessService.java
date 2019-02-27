package com.brs.oa.workflow.service;

import com.brs.oa.workflow.entity.Order;
import com.brs.oa.workflow.model.TaskRepresentation;
import com.brs.oa.workflow.repository.OrderRepository;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tiny lin
 * @date 2019/2/14
 */
@Service
public class OrderProcessService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;


    /**
     * 获取用户任务
     * @param assignee
     * @return
     */
    public List <Task> getTaskList(String assignee){
        List<Task>tasks =  taskService.createTaskQuery().taskAssignee(assignee).list();
        return tasks;
    }

    /**
     * 获取用户任务
     * @param assignee
     * @return
     */
    public List <TaskRepresentation> getTasks(String assignee){
        List<Task>tasks =  taskService.createTaskQuery().taskAssignee(assignee).list();
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    /**
     * 客户，或总经理启动订单流程（通过新生成的内部订单号）
     */
    public String startProcess(Long orderId){
        Order order = orderRepository.findOrderById(orderId);
        Map<String,Object> variables = new HashMap <>(16);
        variables.put("order",order );
        variables.put("orderStatus", "新建");
        variables.put("manager", 1);
        //启动流程，添加流程变量
       ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("brsOrderProcess",variables);
       System.out.println("流程id-------------->"+processInstance.getId());
       return processInstance.getId();
    }


    /**
     * 确认订单
     * @param taskId
     */
    public void confirmOrder(String taskId,Integer editorialDirectorId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(task == null){
            throw new RuntimeException("流程不存在！");
        }
        //确认订单
        HashMap<String,Object> map = new HashMap <>(1);
        map.put("orderValidation",true );
        //修改流程变量的值
        taskService.setVariable(taskId,"orderStatus","确认"  );
        map.put("editorialDirector",editorialDirectorId );
        taskService.complete(taskId,map);
    }

    /**
     * 分发订单（编辑主管向编辑人员分发订单）
     * @param taskId
     * @param editorialStaffId
     */
    public void dispatchOrder(String taskId,Integer editorialStaffId){
        Task task = getTaskByTaskId(taskId);
        if(task == null){
            throw new RuntimeException("流程不存在！");
        }
        //修改流程变量的值
        taskService.setVariable(taskId,"orderStatus","编辑"  );
        //确认订单
        HashMap<String,Object> map = new HashMap <>(1);
        map.put("editorialStaff",editorialStaffId );
        taskService.complete(taskId,map);
    }

    /**
     * 编辑写草稿
     * @param taskId
     * @param laboratoryStaffId
     */
    public void writeDraft(String taskId,Integer laboratoryStaffId){
        Task task = getTaskByTaskId(taskId);
        if(task == null){
            throw new RuntimeException("流程不存在！");
        }
        //确认订单
        HashMap<String,Object> map = new HashMap <>(1);
        map.put("laboratoryStaff",laboratoryStaffId );
        map.put("editStatus", "草稿");
        taskService.complete(taskId,map);
    }

    /**
     * 编辑完成文章
     * @param taskId
     */
    public void completeArticle(String taskId){
        Task task = getTaskByTaskId(taskId);
        if(task == null){
            throw new RuntimeException("流程不存在！");
        }
        //确认订单
        HashMap<String,Object> map = new HashMap <>(1);
        map.put("editStatus", "撰写完");
        map.put("submitter", 5);
        taskService.complete(taskId,map);
    }

    /**
     * 实验人员处理数据
     * @param taskId
     */
    public void handleData(String taskId){
        Task task = getTaskByTaskId(taskId);
        if(task == null){
            throw new RuntimeException("流程不存在！");
        }
        taskService.complete(taskId);
    }

    /**
     * 根据用户任务id获取指导的任务
     * @param taskId
     * @return
     */
    public Task getTaskByTaskId(String taskId ){
       return    taskService.createTaskQuery().taskId(taskId).singleResult();
    }
}
