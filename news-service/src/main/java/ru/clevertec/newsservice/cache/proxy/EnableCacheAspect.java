package ru.clevertec.newsservice.cache.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.clevertec.newsservice.cache.Cache;

@Aspect
@Component
public class EnableCacheAspect<T> {

    private Cache<Integer, T> cache;

    @Around("@annotation(GetEnableCache)")
    public T getEnableCache(ProceedingJoinPoint joinPoint) throws Throwable {

        Integer id = (Integer) joinPoint.getArgs()[0];
        T dataFromCache = cache.get(id);

        if (dataFromCache != null) {
            return dataFromCache;
        }

        T dataFromRepo = (T) joinPoint.proceed();
        cache.put(id, dataFromRepo);

        return dataFromRepo;

    }

    @Around("@annotation(PostEnableCache)")
    public T postEnableCache(ProceedingJoinPoint joinPoint) throws Throwable {

        T dataFromRepo = (T) joinPoint.proceed();
        Integer id = (Integer) joinPoint.getArgs()[0];
        cache.put(id, dataFromRepo);

        return cache.put(id, dataFromRepo);
    }

    @Around("@annotation(DeleteEnableCache)")
    public T deleteEnableCache(ProceedingJoinPoint joinPoint) throws Throwable {

        T dataFromRepo = (T) joinPoint.proceed();
        Integer id = (Integer) joinPoint.getArgs()[0];

        return cache.remove(id);
    }
}
