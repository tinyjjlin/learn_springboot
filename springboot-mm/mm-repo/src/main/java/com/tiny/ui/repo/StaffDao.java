package com.tiny.ui.repo;

import com.tiny.entity.StaffEntity;

/**
 * @author tiny lin
 * @date 2019/2/18
 */
public interface StaffDao {

    public StaffEntity findByName(String name);
}
