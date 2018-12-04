package com.tiny.springbootvalidation.exception;

import com.tiny.springbootspringvalidation.bean.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

/**
 * @author tiny lin
 * @date 2018/12/2
 */
@RestControllerAdvice
public class BindExceptionHandler {
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException ex){
//        // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
//        FieldError fieldError = ex.getFieldError();
// StringBuilder sb = new StringBuilder();
//        sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]")
//                .append(fieldError.getDefaultMessage());
        StringBuilder sb = new StringBuilder();
        sb.append(ex.getMessage());
        // 生成返回结果
        System.out.println("execute..............bindExcepionHandler.......@@@......................");
        Result errorResult = new Result();
        errorResult.setCode(400);
        errorResult.setMessage(sb.toString());
        return errorResult;

    }
}
