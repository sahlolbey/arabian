package com.fsp.arabian.service;

import com.fsp.arabian.exceptions.NotEnoughBalanceException;
import com.fsp.arabian.model.Account;
import com.fsp.arabian.persistence.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface AccountService {
    @Transactional(isolation = Isolation.SERIALIZABLE,propagation =
            Propagation.REQUIRES_NEW,timeout = 1000)
    public Integer transfer(String requesterId , Account source ,
                            Account destination ,
                            Double amount);

    public Optional<Account> getAccount(Account account);

}
