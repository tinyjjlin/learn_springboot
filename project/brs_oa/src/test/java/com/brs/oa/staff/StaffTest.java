//package com.brs.oa.staff;
//
//import com.brs.oa.staff.mapper.StaffMapper;
//import com.brs.oa.staffinfoworkflow.entity.StaffInfoWorkflow;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @author tiny lin
// * @date 2019/1/22
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class StaffTest {
//
//    @Autowired
//    private StaffMapper staffMapper;
//
//    @Test
//    public void testUpdate(){
//        StaffInfoWorkflow workflow = new StaffInfoWorkflow();
//        workflow.setApplicant(100057);
//        workflow.setFirstItem("marital_status");
//        workflow.setFirstItemNewValue("0");
//        staffMapper.updateStaffItems(workflow);
//    }
//}
