package org.example;

import lombok.Getter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedLock {
    @Getter
    private static final Lock lock = new ReentrantLock();
}
