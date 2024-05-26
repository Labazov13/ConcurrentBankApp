package org.example;

import org.example.exceptions.NoMoneyException;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ConcurrentBank bank = new ConcurrentBank();

        BankAccount account1 = bank.createAccount(new BigDecimal(1000));
        BankAccount account2 = bank.createAccount(new BigDecimal(500));

        Thread transferThread1 = new Thread(() -> {
            try {
                bank.transfer(account1, account2, new BigDecimal(200));
            } catch (NoMoneyException e) {
                throw new RuntimeException(e);
            }
        });

        Thread withdrawThread = new Thread(() -> {
            try {
                account1.withdraw(account1, new BigDecimal(569));
            } catch (NoMoneyException e) {
                throw new RuntimeException(e);
            }
        });

        Thread transferThread2 = new Thread(() -> {
            try {
                bank.transfer(account2, account1, new BigDecimal(100));
            } catch (NoMoneyException e) {
                throw new RuntimeException(e);
            }
        });



        transferThread1.start();
        transferThread2.start();
        withdrawThread.start();

        try {
            transferThread1.join();
            transferThread2.join();
            withdrawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Balance account1: " + account1.getBalance());
        System.out.println("Balance account2: " + account2.getBalance());
        System.out.println("Total balance: " + bank.getTotalBalance());
    }
}