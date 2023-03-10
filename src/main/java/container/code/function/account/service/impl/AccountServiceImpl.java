package container.code.function.account.service.impl;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Account;
import container.code.data.repository.AccountRepository;
import container.code.data.repository.BookingOrderRepository;
import container.code.function.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }
    @Override
    public Optional<Account> getAccountById(Integer Id) {
        return accountRepository.findById(Id);
    }

    @Override
    public List<Account> getAccountByRole(String role) { return accountRepository.findByRole(role);}

    @Override
    public Account getAccountByEmail(String email) { return accountRepository.findByEmail(email);}

    @Override
    public ResponseEntity<ResponseObject> banAccount(Integer id) {
        try {
            Optional<Account> account = accountRepository.findById(id);
            if (account.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Can not find account", null));
            else {
                accountRepository.banAccount(id);
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "The account has been locked", null));
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAccountsForOrder(LocalDateTime date) {
        try {
            List<Account> list = accountRepository.getAccountFromOrderWithDate(date);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), null, list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Something wrong occur!", null));
        }
    }
}