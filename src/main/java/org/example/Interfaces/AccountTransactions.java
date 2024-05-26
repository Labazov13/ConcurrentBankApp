package org.example.Interfaces;

import org.example.BankAccount;
import org.example.exceptions.NoMoneyException;

import java.math.BigDecimal;

public interface AccountTransactions {


    boolean deposit(BankAccount account, BigDecimal amount);

    boolean withdraw(BankAccount account, BigDecimal amount) throws NoMoneyException;
}
