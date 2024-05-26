package org.example;

import lombok.Data;
import org.example.Interfaces.AccountTransactions;
import org.example.exceptions.NoMoneyException;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class BankAccount implements AccountTransactions {
    private UUID id;
    private BigDecimal balance;
    private final Lock lock;

    public BankAccount(BigDecimal balance) {
        id = UUID.randomUUID();
        this.balance = balance;
        lock = new ReentrantLock();
    }

    @Override
    public boolean deposit(BankAccount account, BigDecimal amount) {
        this.lock.lock();
        try {
            account.setBalance(account.getBalance().add(amount));
            return true;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean withdraw(BankAccount account, BigDecimal amount) throws NoMoneyException{
        this.lock.lock();
        try {
            BigDecimal newBalance = account.getBalance().subtract(amount);
            if (newBalance.longValue() <= 0){
                throw new NoMoneyException("Недостаточно средств");
            }
            account.setBalance(newBalance);
            return true;
        }
        finally {
            this.lock.unlock();
        }
    }
}
