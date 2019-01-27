package com.brs.oa.position.service.impl;


import com.brs.oa.position.entity.Position;
import com.brs.oa.position.mapper.PositionMapper;
import com.brs.oa.position.service.IPositionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 职位 服务实现类
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-24
 */
@Service
public class PositionServiceImpl  implements IPositionService {

    @Autowired(required = false)
    private PositionMapper positionMapper;
    @Override
    public List<Position> selectList() {
        return positionMapper.selectList();
    }

    @Override
    public int insert(Position position) {
        return positionMapper.insert(position);
    }

    @Override
    public int update(Position position) {
        return positionMapper.update(position);
    }

    @Override
    public int delete(Integer positionId) {
        return positionMapper.delete(positionId);
    }

    @Override
    public int updateEnabledStauts(Position position) {
        return positionMapper.updateEnabledStauts(position);
    }
}
