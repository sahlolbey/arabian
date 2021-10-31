package com.fsp.arabian.model;

import com.fsp.arabian.exceptions.NotEnoughBalanceException;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity

public @Data
class Account {
    @Id
    private Long accountNum;
    @Column(name="BALANCE", nullable=false)
    private Double balance;

    public Account() {
    }

    public Account(Long accountNum) {
        this.accountNum = accountNum;
    }

    public void addBalance(Double amount){
        balance = balance + amount;
    }
    public void decreaseBalance(Double amount) throws NotEnoughBalanceException{
        if(amount>balance){
            throw new NotEnoughBalanceException ();
        }
        balance = balance - amount;
    }
}
