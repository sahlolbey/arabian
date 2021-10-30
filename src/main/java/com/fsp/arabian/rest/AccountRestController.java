package com.fsp.arabian.rest;

import com.fsp.arabian.model.Account;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {
    @PostMapping("/transactions")
    public Integer transfer(Account source,
                            Account destination,Double amount){
        return null;
    }
}
