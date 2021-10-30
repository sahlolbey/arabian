package com.fsp.arabian;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.fsp.arabian.model.Account;
import com.fsp.arabian.service.AccountService;
import org.h2.jdbc.JdbcSQLTransactionRollbackException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
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
    public void loadData(){
        log.info ("running test");
        Account account = new Account (1234L);
        if(accountService == null)
            log.info ("accountservice is null");
        Optional<Account> accountOpt =
                accountService.getAccount (account);
        if(accountOpt.isPresent ()){
            System.out.println ("Balance = " +accountOpt.get ().getBalance ());

        }
    }
    @Test
    public void testTransfer(){

        Account a1234 = new Account (1234L);
        Account a1235 = new Account (1235L);
        Account a1236 = new Account (1236L);
        Account a1237 = new Account (1237L);
        Account a1238 = new Account (1238L);
        Account a1239 = new Account (1239L);

        TransferTest test1 = new TransferTest (a1234,a1235,1000.00);
        TransferTest test2 = new TransferTest (a1237,a1235,1000.00);
        TransferTest test3 = new TransferTest (a1235,a1234,1000.00);

        Thread t1 = new Thread (test1);
        Thread t2 = new Thread (test2);
        Thread t3 = new Thread (test3);
        t1.start ();
        t2.start ();
        t3.start ();
        while (t1.isAlive () && t2.isAlive () &&  t3.isAlive ()){
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
        assertEquals (12000.75,a1235.getBalance ());

        try {
            Thread.sleep (3000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }


     //   t3.start ();
    //    t2.start ();

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
                     int result=accountService.transfer ("1234" , source ,
                            destination , amount);
                     if(result == 1) done = true;
                } catch (Exception e) {
                    log.warn (e.getClass ().toString ());
                    log.warn ("failed "+(count++));

                }
            }
        }
    }
}
