package ru.clevertec.newsservice.model.feign;

import lombok.Data;
import ru.clevertec.newsservice.model.entity.User;

@Data
public class UserResponseFeignClientApi {

    private User data;

}
