package com.tiny.ui.service;

import com.tiny.entity.StaffEntity;

/**
 * @author tiny lin
 * @date 2019/2/18
 */
public interface StaffService {
    StaffEntity findByName(String name);
}
