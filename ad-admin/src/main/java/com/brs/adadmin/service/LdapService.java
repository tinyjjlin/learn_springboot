package com.brs.adadmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.directory.DirContext;


/**
 * @author tiny lin
 * @date 2018/12/20
 */
@Service
public class LdapService {

    @Value("${ldap.domainName}")
    private String ldapDomainName;


    @Autowired
    private LdapTemplate ldapTemplate;



    /**
     * 登陆验证
     * @param username
     * @param password
     * @return
     */
    public Boolean ldapLoginVerify(String username, String password) {
        String userDn = username + ldapDomainName;
        System.out.println("ldapLoginVerify -->userDn:.........."+userDn);
        System.out.println("ldapLoginVerify -->password:.........."+password);
        DirContext dirContext = null;
        try {
            dirContext = ldapTemplate.getContextSource().getContext(userDn, password);
            return true;
        } catch (Exception e) {
             e.printStackTrace();
            return false;
        } finally {
            LdapUtils.closeContext(dirContext);
        }

    }
}
