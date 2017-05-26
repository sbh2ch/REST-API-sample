package kr.goci.kosc.account;

import kr.goci.kosc.common.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kiost on 2017-05-24.
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Account newAccount = service.createAccount(create);
        return new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity getAccounts() {
        List<AccountDto.Response> responses = repository.findAll().stream().map(a -> modelMapper.map(a, AccountDto.Response.class)).collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    public ResponseEntity getAccount(@PathVariable long id) {
        Account account = repository.findOne(id);
        if (account == null) {
            return new ResponseEntity<>(new ErrorResponse("account.not.found", "no user"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(modelMapper.map(account, AccountDto.Response.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity updateAccount(@PathVariable long id, @RequestBody @Valid AccountDto.Update request, BindingResult result) {
        Account account = repository.findOne(id);
        if (account == null) {
            return new ResponseEntity<>(new ErrorResponse("account.not.found", "no user"), HttpStatus.BAD_REQUEST);
        }
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ErrorResponse("account.update.error", "bad parameter"), HttpStatus.BAD_REQUEST);
        }

        Account accountUpdate = modelMapper.map(request, Account.class);
        accountUpdate.setId(id);
        Account updateAccount = service.updateAccount(accountUpdate);

        return new ResponseEntity<>(modelMapper.map(updateAccount, AccountDto.Update.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAccount(@PathVariable long id) {
        Account account = repository.findOne(id);
        if (account == null) {
            return new ResponseEntity<>(new ErrorResponse("account.not.found", "no user"), HttpStatus.BAD_REQUEST);
        }

        repository.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity handleUserDuplicatedException(UserDuplicatedException e) {
        return new ResponseEntity<>(new ErrorResponse("duplicated.username.exception", "[" + e.getUsername() + "] 유저이름중복"), HttpStatus.BAD_REQUEST);
    }
}
