package com.tiny.springbootureport.config;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author tiny lin
 * @date 2019/1/10
 */
@Component
public class UreportDataSource implements BuildinDatasource {

    private static final String UREPORT_DB="mysql";
    @Autowired
    private DataSource dataSource;
    @Override
    public String name() {
        return UREPORT_DB;
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
