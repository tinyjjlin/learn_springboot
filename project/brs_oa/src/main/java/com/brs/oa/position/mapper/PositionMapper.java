package com.brs.oa.position.mapper;


import com.brs.oa.position.entity.Position;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 职位 Mapper 接口
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-24
 */
public interface PositionMapper  {

    /**
     * 获取职位列表
     * @return
     */
    List<Position> selectList();

    /**
     * 添加新的职位
     * @param position
     * @return
     */
    int insert(@Param("position") Position position);

    /**
     * 职位编辑
     * @param position
     * @return
     */
    int update(@Param("position")Position position);

    /**
     * 删除职位
     * @param positionId
     * @return
     */
    int delete(@Param("positionId")Integer positionId);

    /**
     * 更新职位是否启用状态
     * @return
     */
    int updateEnabledStauts(@Param("position")Position position);

}
