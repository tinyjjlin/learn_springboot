package com.tiny.ui.service.impl;

import com.tiny.entity.StaffEntity;
import com.tiny.ui.repo.StaffDao;
import com.tiny.ui.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tiny lin
 * @date 2019/2/18
 */
@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffDao staffDao;
    @Override
    public StaffEntity findByName(String name) {
        return staffDao.findByName(name);
    }
}
