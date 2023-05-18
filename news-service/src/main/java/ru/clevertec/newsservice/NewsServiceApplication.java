package ru.clevertec.newsservice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.clevertec.newsservice.configuration.Indexer;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class NewsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsServiceApplication.class, args);
    }

    @Bean
    public ApplicationRunner buildIndex(Indexer indexer) {
        return (ApplicationArguments args) -> {
            indexer.indexPersistedData("ru.clevertec.newsservice.configuration.Indexer");
        };
    }

}
