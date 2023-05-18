package ru.clevertec.newsservice.model.request.authentification;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Component
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64Decoder {

}
