package org.example.Interfaces;

import org.example.BankAccount;
import org.example.exceptions.NoMoneyException;

import java.math.BigDecimal;

public interface TransferBetweenAccounts {
    boolean transfer(BankAccount accountFrom, BankAccount accountTo, BigDecimal amount) throws NoMoneyException;
}
