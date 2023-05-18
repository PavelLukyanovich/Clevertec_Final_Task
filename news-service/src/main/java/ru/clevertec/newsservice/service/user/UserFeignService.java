package ru.clevertec.newsservice.service.user;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.clevertec.newsservice.configuration.FeignConfiguration;
import ru.clevertec.newsservice.configuration.LoadBalancerConfiguration;
import ru.clevertec.newsservice.model.entity.User;
import ru.clevertec.newsservice.model.feign.UserResponseFeignClientApi;
import ru.clevertec.newsservice.model.request.authentification.RegisterUserRequest;

import java.util.UUID;

@FeignClient(name = "user-service", configuration = FeignConfiguration.class)
@LoadBalancerClient(name = "user-service", configuration = LoadBalancerConfiguration.class)
public interface UserFeignService {

    @GetMapping("/api/v1/users/name/{name}")
    UserResponseFeignClientApi getUserByName(@PathVariable String name);

    @GetMapping("/api/v1/users/{id}")
    UserResponseFeignClientApi getUserById(@PathVariable UUID id);

    @PostMapping("/api/v1/users")
    UserResponseFeignClientApi createUser(@RequestBody RegisterUserRequest request);
}
