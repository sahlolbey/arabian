package com.fsp.arabian.rest;

import com.fsp.arabian.model.Account;
import com.fsp.arabian.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AccountRestController {
    @Autowired
    AccountService accountService;

    /**
     * Transfers the amount from source account to destination account
     * @param sourceAccount source account number
     * @param destinationAccount destination account number
     * @param amount amount to be transfered
     * @return
     */
    @PostMapping("/transfer")
    public Integer transfer(@RequestBody Long sourceAccount ,
                            @RequestBody Long destinationAccount ,
                            @RequestBody Double amount
    ) {
        Account source = new Account (sourceAccount);
        Account destination = new Account (destinationAccount);
        Integer result = accountService.transfer (source , destination ,amount);

        return result;
    }

    /**
     * get the accountNumber and returns the information about the account
     * including its balance.
     * @param accountNum :account number for the designated account.
     * @return the complete Account information
     */
    @GetMapping("/getAccount")
    public ResponseEntity<Account> getAccount(@RequestBody Long accountNum){

         Optional<Account> account =
                 accountService.getAccount (new Account (accountNum));
         if(account.isPresent ())
             return ResponseEntity.ok ().body (account.get ());
         else
             return ResponseEntity.status (201).body (null);

    }
}
