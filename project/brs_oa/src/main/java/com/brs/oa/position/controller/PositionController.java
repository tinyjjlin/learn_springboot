package com.brs.oa.position.controller;

import com.brs.oa.comm.model.RestfulResult;
import com.brs.oa.position.entity.Position;
import com.brs.oa.position.mapper.PositionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 职位 前端控制器
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-24
 */
@RestController
public class PositionController {
    @Autowired(required = false)
    private PositionMapper positionMapper;
    /**
     * 分页查看职位信息列表
     * @return
     */
    @ApiOperation(value = "分页查看职位信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "要查看的页数", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页显示的数据行数", dataType = "int", defaultValue = "10")})

    @ApiResponse(code = 200,message = "分页查看职位信息列表",response = List.class)
    @GetMapping("/api/position/pagination")
    public RestfulResult getDeptListByPage(@RequestParam(value = "page",required = false, defaultValue = "1") Integer page, @RequestParam(value = "limit",required = false, defaultValue = "10") Integer size){
        PageHelper.startPage(page,size );
        List<Position> positionList= positionMapper.selectList();
        PageInfo pageInfo = new PageInfo(positionList);

        return new RestfulResult(200,"分页查看职位信息列表操作成功！",pageInfo );
    }

    /**
     * 查看职位信息列表
     * @return
     */
    @ApiOperation(value = "查看职位信息列表")
    @ApiResponse(code = 200,message = "查看职位信息列表",response = List.class)
    @GetMapping("/api/position")
    public RestfulResult getDeptList(){
        List<Position> positionList= positionMapper.selectList();
        return new RestfulResult(200,"查看职位信息列表操作成功！",positionList );
    }


    /**
     * 添加新的职位
     * @param position
     * @return
     */
    @ApiOperation(value = "添加新的职位")
    @PostMapping("/api/position")
    public RestfulResult add(@RequestBody Position position) {
        //pre handler
        if(position == null){
            return new RestfulResult(451, "添加新的职位操作失败！") ;
        }
        try {
            int result  =  positionMapper.insert(position);
            return new RestfulResult(200, "添加新的职位操作成功！",result) ;
        }catch (Exception e){
           // e.printStackTrace();
            return new RestfulResult(451, "添加新的职位操作失败！",null) ;
        }


    }

    /**
     * 删除一个职位
     * @param positionId
     * @return
     */
    @ApiOperation(value = "删除一个职位")
    @DeleteMapping("/api/position/{positionId}")

    public RestfulResult delete( @PathVariable("positionId")Integer positionId){
        //pre handler
        if(positionId == null){
           return  new RestfulResult(451, "删除职位操作失败！",null);
        }
        int result = positionMapper.delete(positionId);
        return (result>0) ?new RestfulResult(200,"删除职位操作成功！",null ):new RestfulResult(451, "删除职位操作失败！",null);
    }

    /**
     * 修改职位信息
     * @param position
     * @return
     */
    @ApiOperation(value = "修改职位信息")
    @PatchMapping("/api/position")
    public RestfulResult edit(@RequestBody Position position){
        int result = positionMapper.update(position);
        return new RestfulResult(200,"修改职位信息操作成功！" ,result);
    }

    /**
     * 改变职位信息是否启用状态
     * @param position
     * @return
     */
    @ApiOperation(value = "改变职位信息是否启用状态")
    @PatchMapping("/api/position/enabled")
    public RestfulResult changeEnabledStatus(@RequestBody Position position){
        int result = positionMapper.updateEnabledStauts(position);
        return new RestfulResult(200,"改变职位信息启用状态操作成功！" ,result);
    }
}
