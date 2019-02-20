package com.tiny.service.com.tiny.service.impl;

import com.tiny.entity.StaffEntity;
import com.tiny.service.StaffDao;

/**
 * @author tiny lin
 * @date 2019/2/18
 */
public class StaffDaoImpl implements StaffDao {
    @Override
    public StaffEntity findByName() {
        return new StaffEntity("100057","jjlin" );
    }
}
