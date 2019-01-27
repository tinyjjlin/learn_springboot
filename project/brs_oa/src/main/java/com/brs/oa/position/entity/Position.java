package com.brs.oa.position.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 职位
 * </p>
 *
 * @author tiny lin
 * @since 2019-01-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    /**
     * 是否启用
     */
    private Integer enabled;

    private Date gmtCreate;

    private Date gmtModified;


}
