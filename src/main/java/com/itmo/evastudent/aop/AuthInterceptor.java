package com.itmo.evastudent.aop;

import com.itmo.evastudent.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 权限校验 AOP
 */
@Aspect
@Component
@Slf4j
public class AuthInterceptor {

    @Resource
    private StudentService studentService;

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @return
     */
    @Around("execution(* com.itmo.evastudent.controller.*.*(..))")
    public Object doInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String name = signature.getName();

        // 跳过登录请求
        if (Objects.equals(name, "adminLogin")) {
            return joinPoint.proceed();
        }

//        // 当前登录用户
//        Admin loginUser = adminService.getLoginAdmin(request);
//        if (loginUser == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
//        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

