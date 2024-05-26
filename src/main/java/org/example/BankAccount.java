package org.example;

import lombok.Data;
import org.example.Interfaces.AccountTransactions;
import org.example.exceptions.NoMoneyException;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

@Data
public class BankAccount implements AccountTransactions {
    private UUID id;
    private BigDecimal balance;
    private final Lock lock;

    public BankAccount(BigDecimal balance) {
        id = UUID.randomUUID();
        this.balance = balance;
        lock = SharedLock.getLock();
    }

    @Override
    public boolean deposit(BankAccount account, BigDecimal amount) {
        lock.lock();
        try {
            account.setBalance(account.getBalance().add(amount));
            return true;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public boolean withdraw(BankAccount account, BigDecimal amount) throws NoMoneyException{
        lock.lock();
        try {
            BigDecimal newBalance = account.getBalance().subtract(amount);
            if (newBalance.longValue() <= 0){
                throw new NoMoneyException("Недостаточно средств");
            }
            account.setBalance(newBalance);
            return true;
        }
        finally {
            lock.unlock();
        }
    }
}
