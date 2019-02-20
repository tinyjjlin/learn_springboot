package com.brs.idm.service;

import com.brs.idm.api.IdmIdentityService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tiny lin
 * @date 2019/2/20
 */
@Data
public class AbstractIdmService {
    @Autowired
    protected IdmIdentityService identityService;
}
