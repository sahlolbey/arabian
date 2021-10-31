package com.fsp.arabian;

import com.fsp.arabian.model.Account;
import com.fsp.arabian.service.AccountService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql (executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"/data.sql"})
public class AccountServiceTest {
    private static final Logger log =
            LoggerFactory.getLogger (AccountServiceTest.class);
    @Autowired
    AccountService accountService;
    @Test
    public void contextLoads() {
    }


    @Test
    public void testGetAccount(){
        log.info ("running test");
        Account account = new Account (1234L);
        Optional<Account> accountOpt =
                accountService.getAccount (account);
        if(accountOpt.isPresent ()){
            assertEquals (10000.25,accountOpt.get().getBalance ());

        }
    }
    @Test
    public void testTransferInvalidAccounts(){
        Account source = new Account(654321L);
        Account dest = new Account (1234L);
        int result = accountService.transfer (source,dest,1000.25);
        assertEquals (-1,result);
    }
    @Test
    public void testTransferNotEnoughBalance(){
        Account a1234 = new Account (1234L);
        Account a1235 = new Account (1235L);
        int result=accountService.transfer (a1234 ,
                a1235 , 11000.00);
        assertEquals (0,result);
        a1234 = accountService.getAccount (a1234).get ();
        a1235 = accountService.getAccount (a1235).get ();
        assertEquals (10000.25,a1234.getBalance ());
        assertEquals (11000.75,a1235.getBalance ());
    }
    @Test
    public void testTransferMultiThread(){

        Account a1234 = new Account (1234L);
        Account a1235 = new Account (1235L);

        TransferTest test1 = new TransferTest (a1234,a1235,1000.00);

        TransferTest test2 = new TransferTest (a1235,a1234,1000.00);

        Thread t1 = new Thread (test1);
        Thread t2 = new Thread (test2);
        t1.start ();
        t2.start ();
        while (t1.isAlive () && t2.isAlive ()){
            System.out.println ("waiting for all to return");
            try {
                Thread.sleep (1000);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        a1234 = accountService.getAccount (a1234).get ();
        a1235 = accountService.getAccount (a1235).get ();
        System.out.println ("a1234 balance");
        assertEquals (10000.25,a1234.getBalance ());
        System.out.println ("a1235 balance");
        assertEquals (11000.75,a1235.getBalance ());

    }
    class TransferTest implements Runnable{
        private Account source;
        private Account destination;
        private Double amount;

        public TransferTest(Account source , Account destination , Double amount) {
            this.source = source;
            this.destination = destination;
            this.amount = amount;
        }

        @Override
        public void run() {
            int failCount = 15;
            int count=0;
            boolean done=false;
            while (!done && count<=failCount) {
                try {
                     int result=accountService.transfer (source ,
                            destination , amount);
                     if(result == 1) done = true;
                } catch (Exception e) {
                    log.warn ("failed "+(count++));
                }
            }
        }
    }
}
