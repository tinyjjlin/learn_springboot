package com.brs.oa.comm.filehandler;

import com.brs.oa.comm.jwt.JwtSupport;
import com.brs.oa.comm.model.RestfulResult;
import com.brs.oa.staff.service.IStaffService;
import com.brs.oa.util.CommonUtil;
import com.brs.oa.util.FileUtil;


import com.brs.oa.workflow.service.IWorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tiny lin
 * @date 2019/1/7
 */
@CrossOrigin
@RestController
@Api
public class FileUpLoadController {
    @Value("${file-service.upload-header-dir}")
    private String headerImgDir;

    @Value("${file-service.upload-workflow-dir}")
    private String workflowDir;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IWorkflowService workflowService;


    @ApiOperation("上传用户头像")
    @PostMapping("/api/uploadImage")
    public RestfulResult uploadImage(@RequestHeader("Authorization") String token, @RequestParam("file")MultipartFile file) {
        int empNo = JwtSupport.getEmployeeNo(token);
        try {
            String serverFileName =  FileUtil.uploadFile(file, headerImgDir);
            staffService.updateStaffHeaderImg(empNo, serverFileName);
            return new RestfulResult(2000, "上传用户头像成功！", serverFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new RestfulResult(4004, "上传用户头像失败！", null);
        }
    }

    @ApiOperation("上传工作流图片")
    @PostMapping("/api/uploadWorkflowImg")
    public RestfulResult uploadWorkFlowMaterials(@RequestParam("file")MultipartFile file) {
        try {
            String serverFileName =  FileUtil.uploadFile(file, workflowDir);
            return new RestfulResult(2000, "上传工作流图片成功！", serverFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new RestfulResult(4004, "上传工作流图片像失败！", null);
        }
    }

    @ApiOperation(value = "上传多张工作流图片")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "serialNumber", value = "工作流", dataType = "int", required = true)})
        @PostMapping("api/uploadFiles")
        public RestfulResult uploadFiles(@RequestHeader("Authorization") String token,@RequestParam("serialNumber")Integer serialNumber,@RequestParam("files")MultipartFile[] files){
            int empNo = JwtSupport.getEmployeeNo(token);
            try {
                String serverFileNames = FileUtil.uploadMultFile(files, workflowDir);
                workflowService.updateApplicantMaterials(empNo, serialNumber, serverFileNames);
                return  new RestfulResult(2000,"上传工作流材料成功！",serverFileNames );
            } catch (Exception e) {
                e.printStackTrace();
                return  new RestfulResult(4004,"上传工作流材料失败！",null );
            }
        }
    }

