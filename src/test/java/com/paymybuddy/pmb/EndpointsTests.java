package com.paymybuddy.pmb;

import com.paymybuddy.pmb.controller.PmbUserController;
import com.paymybuddy.pmb.model.PmbUser;
import com.paymybuddy.pmb.service.IPmbUserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EndpointsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPmbUserService userService;

    private PmbUser testPmbUser;

    @BeforeAll
    void setUp() {


        testPmbUser = new PmbUser();

        testPmbUser.setEmail("john.doe@aol.com");
        testPmbUser.setPassword("toto95");

        log.info("*** STARTING ENDPOINTS TESTS ***");
    }

    @AfterAll
    void tearDown() {
        log.info("*** ENDPOINTS TESTS FINISHED ***");
    }

    @Test
    void contextLoads() {
        log.info("Spring Boot Test: Context loads.");
    }

    // Tests for endpoint: http://localhost:8080/pmbuser
    @Test
    void createPmbUser() throws Exception {

        when(userService.create(anyString(), anyString())).thenReturn(testPmbUser);

        mockMvc.perform(post("/pmbuser?email=" + testPmbUser.getEmail() + "&password=" + testPmbUser.getPassword()))
                .andExpect(status().isCreated());
        // TODO gérer les cas où la création est null, en vérifiant le type de retour de la requête save finale et en la répercutant
    }

}
