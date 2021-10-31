package com.fsp.arabian.service;

import com.fsp.arabian.exceptions.NotEnoughBalanceException;
import com.fsp.arabian.model.Account;
import com.fsp.arabian.persistence.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@Scope("singleton")
public class AccountServiceImp implements AccountService{
    private static final Logger log =
            LoggerFactory.getLogger (AccountService.class);
    @Autowired
    AccountRepository accountRepository;


    public Integer transfer(Account source ,
                            Account destination ,
                            Double amount) {
        Integer result = -1;
        try {
            Optional<Account> sourceOpt =
                    accountRepository.findById (source.getAccountNum ());
            if (sourceOpt.isPresent ()) {
                Optional<Account> destOpt =
                        accountRepository.findById (destination.getAccountNum ());
                if (destOpt.isPresent ()) {
                    source = sourceOpt.get ();
                    destination = destOpt.get ();
                    source.decreaseBalance (amount);
                    destination.addBalance (amount);
                    result = 1;
                    accountRepository.save (source);
                    accountRepository.save (destination);
                }
            }
        } catch (NotEnoughBalanceException e) {
            result = 0;
        } catch (Exception e) {
            log.error (e.getMessage () , e);
            result = -1;
        }
        return result;
    }


    public Optional<Account> getAccount(Account account){
        log.info ("getAccount method called");
        Optional<Account> accountOpt = accountRepository.findById (account.getAccountNum ());
        return accountOpt;
    }
}
