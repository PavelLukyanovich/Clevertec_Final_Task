package ru.clevertec.newsservice.service.user;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
public class UserClientIntegrationTest {

    @Autowired
    private WireMockServer mockUserService;

    @Autowired
    private UserFeignService userFeignServiceClient;

    @BeforeEach
    void setUp() throws IOException {
        UserMock.setupMockBooksResponse(mockUserService);
    }

    @Test
    public void whenGetUserByName_thenShouldReturnedUserWithSameName() {
        assertEquals("name", userFeignServiceClient.getUserByName("name").getData().getName());
    }

}
