package kr.goci.kosc.account;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kiost on 2017-05-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@SpringBootTest
@WebAppConfiguration
@Transactional
public class AccountServiceTest {
    @Autowired
    private AccountService service;

    @Test
    public void jpaQueryTest() {
        AccountDto.Response account = service.testing("forTest", "12334");
        log.info("result --> {}", account.toString());
    }
}