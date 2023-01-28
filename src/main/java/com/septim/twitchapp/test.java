package com.septim.twitchapp;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class test {
    Lock lock = new ReentrantLock();

    public void method1(){
        lock.lock();
        System.out.println("m1");
        method2();
        lock.unlock();
    }
    public void method2(){
        lock.lock();
        System.out.println("m2");
        lock.unlock();
    }

    public static void main(String[] args) {
        test obj = new test();
        obj.method1();
    }
}
