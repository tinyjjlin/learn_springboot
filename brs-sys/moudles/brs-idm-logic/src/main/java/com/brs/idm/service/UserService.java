package com.brs.idm.service;

import com.brs.idm.model.UserInformation;

/**
 * @author tiny lin
 * @date 2019/2/20
 */
public interface UserService {
    UserInformation getUserInformation(String userId);
}
