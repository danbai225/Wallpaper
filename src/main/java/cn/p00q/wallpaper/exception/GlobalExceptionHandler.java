/**
 * @author DanBai
 * @create 2020-02-15 14:09
 * @desc 全局异常处理
 **/
package cn.p00q.wallpaper.exception;

import cn.p00q.wallpaper.entity.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BindException.class)
    public Response handleRException(BindException e) {
        StringBuilder msg = new StringBuilder();
        e.getAllErrors().forEach(x -> msg.append(x.getDefaultMessage()).append(","));
        return Response.Err(msg.toString()).setCode(Response.ERR_PARAMETER);
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.Err(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public Response handleValidationException(ValidationException e) {
        return Response.Err(e.getCause().getMessage()).setCode(Response.ERR_PARAMETER);
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleConstraintViolationException(ConstraintViolationException e) {
        return Response.Err(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Response handlerNoFoundException(Exception e) {
        return Response.Err("路径不存在，请检查路径是否正确").setCode(404);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Response handleDuplicateKeyException(DuplicateKeyException e) {
        return Response.Err("数据已存在，请检查后提交").setCode(Response.ERR_DUPLICATION);
    }


    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return Response.Err("系统繁忙,请稍后再试").setCode(500);
    }
}
