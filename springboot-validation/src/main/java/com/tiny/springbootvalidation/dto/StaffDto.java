package com.tiny.springbootvalidation.dto;

import com.tiny.springbootspringvalidation.validator.PhoneValidation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author tiny lin
 * @date 2018/12/2
 */
@Data
public class StaffDto {

    @Size(min = 2,max=30)
    private String name;

    @Email
    @NotEmpty(message="Email不能为空！")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @PhoneValidation
    private String phone;


    public StaffDto() {
    }
}
