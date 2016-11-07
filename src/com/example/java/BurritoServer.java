package com.example.java;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * Created by AnabetsyRivero on 10/31/16.
 */
public class BurritoServer implements Runnable {
    private final String name;
    Semaphore customers = new Semaphore(1);


    LinkedList<Customer> customersWaiting;

    public BurritoServer(List<Customer> customersWaiting, String name) {
        this.customersWaiting = (LinkedList<Customer>) customersWaiting;
        this.name = name;
        this.customersWaiting = (LinkedList<Customer>) customersWaiting;
    }

    @Override
    public String toString(){
        return "Server name: " + name;
    }


    @Override
    public void run() {
       while(customersWaiting.size() > 0) {
           try {
               customers.acquire();
               this.makeBurrito();  //making burritos...
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
                customers.release();
            }
    }

    // this method will simulate making burritos
    public void makeBurrito() throws InterruptedException {

        System.out.println(" Server " + name + " is serving customer " + customersWaiting.pollFirst());
        sleep(500);
    }

}



