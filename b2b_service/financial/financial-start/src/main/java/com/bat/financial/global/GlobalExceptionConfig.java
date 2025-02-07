package com.bat.financial.global;

import static com.bat.financial.common.ErrorCode.*;

import javax.annotation.Resource;

import com.bat.financial.api.base.FinancialException;
import com.bat.financial.api.base.MessageUtils;
import com.bat.financial.common.ErrorCode;
import com.bat.financial.web.base.Response;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bat.financial.Tenant.Tenant;
import com.bat.financial.Tenant.TenantContext;
import com.bat.financial.db.service.executor.TenantDBCmd;
import static com.bat.financial.common.ErrorCode.P_NOTNULL;
import static com.bat.financial.common.ErrorCode.SYSTEM_EXCEPTION;

/**
 * @author lx
 */
@RestControllerAdvice
public class GlobalExceptionConfig {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionConfig.class);

    @Resource
    private TenantDBCmd tenantDBCmd;

    /**
     * 当请求参数不为空校验失败情况
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Response bindHandler(Exception e) {
        String msg = null;
        if (e instanceof MethodArgumentNotValidException) {
            msg = MessageUtils
                .get(((MethodArgumentNotValidException)e).getBindingResult().getFieldError().getDefaultMessage());
        } else if (e instanceof BindException) {
            msg = MessageUtils.get(((BindException)e).getBindingResult().getFieldError().getDefaultMessage());
        } else {
            return Response.buildFailure(ErrorCode.SYSTEM_EXCEPTION, MessageUtils.get(ErrorCode.SYSTEM_EXCEPTION));
        }
        logger.error(msg);
        if (StringUtils.isNotBlank(msg)) {
            return Response.buildFailure(P_NOTNULL, msg);
        } else {
            return Response.buildFailure(P_NOTNULL, P_NOTNULL);
        }
    }

    /**
     * 商品服务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({FinancialException.class})
    public Response baseHandler(FinancialException e) {
        String errorCode = e.getCode();
        logger.error(errorCode);
        if (StringUtils.isBlank(errorCode)) {
            errorCode = SYSTEM_EXCEPTION;
            return Response.buildFailure(errorCode, MessageUtils.get(errorCode));
        }
        String msg = e.getMsg();
        if (StringUtils.isNotBlank(msg)) {
            return Response.buildFailure(errorCode, msg);
        }
        msg = MessageUtils.get(errorCode);
        if (StringUtils.isNotBlank(msg)) {
            return Response.buildFailure(errorCode, MessageUtils.get(errorCode));
        } else {
            return Response.buildFailure(errorCode, errorCode);
        }
    }

    /**
     * 未捕获的全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof MyBatisSystemException) {
            Tenant tenant = TenantContext.tenantInfoMap.get(TenantContext.getTenantNo());
            if (tenant != null) {
                tenantDBCmd.tenantDBDelete(tenant.getTenantNo(), tenant.getConfigMap().get("dbName"));
            }
            return Response.buildFailure(SYSTEM_DATA_ERROR, MessageUtils.get(SYSTEM_DATA_ERROR));
        }
        return Response.buildFailure(SYSTEM_EXCEPTION, MessageUtils.get(SYSTEM_EXCEPTION));
    }
}
