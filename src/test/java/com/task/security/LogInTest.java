package com.task.security;

import com.task.TestApplication;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Dimon on 01.07.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = TestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogInTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String TESTED_ENDPOINT = "/books";
    public static final String LOGOUT_ENDPOINT = "/logout";

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void successUserLogInTest() throws Exception {
        mockMvc.perform(get(TESTED_ENDPOINT))
                .andExpect(status().isOk());
        logOut();
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void successAdminLogInTest() throws Exception {
        mockMvc.perform(get(TESTED_ENDPOINT))
                .andExpect(status().isOk());
        logOut();
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "wrongpass", roles = "ADMIN")
    public void wrongPassTest() throws Exception {
        logOut();
        mockMvc.perform(get(TESTED_ENDPOINT))
                .andExpect(status().isUnauthorized());
    }

    private void logOut() throws Exception {
        mockMvc.perform(get(LOGOUT_ENDPOINT))
                .andExpect(status().isNoContent());
    }
}
