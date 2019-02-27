package com.tiny.ui.repo.impl;

import com.tiny.entity.StaffEntity;
import com.tiny.ui.repo.StaffDao;
import org.springframework.stereotype.Repository;

/**
 * @author tiny lin
 * @date 2019/2/18
 */

@Repository
public class StaffDaoImpl implements StaffDao {
    @Override
    public StaffEntity findByName(String name) {
        return new StaffEntity("100057","jjlin" );
    }
}
