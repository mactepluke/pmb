package com.paymybuddy.pmb;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class PmbApplicationTests {


    @Test
    void contextLoads() {
        log.info("Spring Boot Test: Context loads.");
    }

}
