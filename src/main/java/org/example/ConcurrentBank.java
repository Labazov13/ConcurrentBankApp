package org.example;

import lombok.Data;
import org.example.Interfaces.TransferBetweenAccounts;
import org.example.exceptions.NoMoneyException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class ConcurrentBank implements TransferBetweenAccounts {
    private final Lock lock;
    private List<BankAccount> accounts = new ArrayList<>();

    public ConcurrentBank(){
        lock = new ReentrantLock();
    }

    @Override
    public boolean transfer(BankAccount accountFrom, BankAccount accountTo, BigDecimal amount)
            throws NoMoneyException {
        lock.lock();
        try {
            BigDecimal newBalance = accountFrom.getBalance().subtract(amount);
            if (newBalance.longValue() <= 0) {
                throw new NoMoneyException("Недостаточно средств");
            }
            accountFrom.setBalance(newBalance);
            accountTo.setBalance(accountTo.getBalance().add(amount));
            return true;
        } finally {
            lock.unlock();
        }
    }


    public BankAccount createAccount(BigDecimal bigDecimal) {
        BankAccount bankAccount = new BankAccount(bigDecimal);
        accounts.add(bankAccount);
        return bankAccount;
    }

    public BigDecimal getTotalBalance() {
        lock.lock();
        try {
            return accounts.stream()
                    .map(BankAccount::getBalance)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } finally {
            lock.unlock();
        }
    }
}
