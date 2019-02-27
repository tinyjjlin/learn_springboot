package com.tiny.ui.rest;

import com.tiny.entity.StaffEntity;
import com.tiny.ui.model.RestResult;
import com.tiny.ui.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tiny lin
 * @date 2019/2/18
 */
@RestController
@RequestMapping("/api")
public class StafffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/user")
    public RestResult StaffInfo(@RequestParam("userName")String name){
        StaffEntity staffEntity =  staffService.findByName(name);
        return new RestResult(200,"user info",staffEntity );
    }

}
