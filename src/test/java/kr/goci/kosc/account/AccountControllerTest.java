package kr.goci.kosc.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kiost on 2017-05-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class AccountControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void createAccount() throws Exception {
        AccountDto.Create createAccount = new AccountDto.Create();
        createAccount.setUsername("createAccountTest2");
        createAccount.setPassword("12334");

        ResultActions result = mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createAccount)));
        result.andDo(print());
        result.andExpect(status().isCreated());
//        result.andExpect(jsonPath("$.username", is("eight")));
    }

    @Test
    public void createAccount_BadRequest() throws Exception {
        AccountDto.Create badAccount = new AccountDto.Create();
        badAccount.setPassword("1");
        badAccount.setUsername("hell");

        ResultActions result = mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(badAccount)));
        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void createAccount_Duplicated() throws Exception {
        AccountDto.Create duplicatedAccount = new AccountDto.Create();
        duplicatedAccount.setUsername("forTest");
        duplicatedAccount.setPassword("12345");

        ResultActions result = mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(duplicatedAccount)));
        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void selectAccount() throws Exception {
        ResultActions result = mockMvc.perform(get("/accounts/3"));
        result.andDo(print());
        result.andExpect(status().isOk());
    }

    @Test
    public void selectAccount_BadRequest() throws Exception {
        ResultActions result = mockMvc.perform(get("/accounts/1"));
        result.andDo(print());
        result.andExpect(status().is4xxClientError());
    }

    @Test
    public void updateAccount() throws Exception {
        AccountDto.Update updateAccount = new AccountDto.Update();
        updateAccount.setUsername("updateTesting");
        updateAccount.setPassword("updateTest");

        ResultActions result = mockMvc.perform(put("/accounts/6").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateAccount)));
        result.andDo(print());
        result.andExpect(status().isOk());
    }

    @Test
    public void deleteAccount() throws Exception {
        ResultActions result = mockMvc.perform(delete("/accounts/6"));

        result.andDo(print());
        result.andExpect(status().isNoContent());
    }
}
