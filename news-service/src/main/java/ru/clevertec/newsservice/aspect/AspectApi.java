package ru.clevertec.newsservice.aspect;


import io.micrometer.observation.transport.ResponseContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.apache.http.protocol.ResponseContent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.clevertec.newsservice.model.response.ResponseApi;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
public class AspectApi {

    Logger logger = LoggerFactory.getLogger(AspectApi.class);

    @Pointcut("execution(* ru.clevertec.newsservice.controller.AuthenticationController.*(..))")
    public void authentication(){}

    @Pointcut("execution(* ru.clevertec.newsservice.controller.NewsController.*(..))")
    public void news() {}
    @Pointcut("execution(* ru.clevertec.newsservice.controller.CommentController.*(..))")
    public void comment() {}

    @Before("authentication() || news() || comment()")
    public void beforeControllerMethod(JoinPoint joinPoint) {

        logger.info("Invoked :: " + before(joinPoint));
    }

    @After(value = "authentication() || news() || comment()")
    public void afterControllerMethod(JoinPoint joinPoint) {

        logger.info("Return :: " + joinPoint);
    }


    private String before(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass()
                + "#" + joinPoint.getSignature().getName()
                + "\n\targs:" + Arrays.asList(joinPoint.getArgs());
    }
}
