package com.brs.oa.position.service;


import com.brs.oa.position.entity.Position;


import java.util.List;

/**
 * <p>
 * 职位 服务类
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-24
 */
public interface IPositionService {

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
    int insert( Position position);

    /**
     * 职位编辑
     * @param position
     * @return
     */
    int update(Position position);

    /**
     * 删除职位
     * @param positionId
     * @return
     */
    int delete(Integer positionId);

    /**
     * 更新职位是否启用状态
     * @return
     */
    int updateEnabledStauts(Position position);

}
