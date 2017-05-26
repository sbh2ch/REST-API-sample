package kr.goci.kosc.account;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by kiost on 2017-05-24.
 */
@Service
@Transactional
@Slf4j
public class AccountService {
    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Account createAccount(AccountDto.Create dto) {
        Account account = modelMapper.map(dto, Account.class);
        String username = dto.getUsername();

        if (repository.findByUsername(username) != null) {
            log.error("user duplicated exception occured --->> {}", username);
            throw new UserDuplicatedException(username);
        }
        //TODO password encryption
        Date now = new Date();
        account.setJoined(now);
        account.setUpdated(now);

        return repository.save(account);
    }

    public Account updateAccount(Account accountUpdate) {
        Account account = repository.findOne(accountUpdate.getId());

        String newUsername = accountUpdate.getUsername();
        if (!StringUtils.isEmpty(newUsername))
            account.setUsername(newUsername);

        String newPassword = accountUpdate.getPassword();
        if (!StringUtils.isEmpty(newPassword))
            account.setPassword(newPassword);

        account.setUpdated(new Date());

        return repository.save(account);
    }
}
