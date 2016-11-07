package com.example.java;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by AnabetsyRivero on 10/31/16.
 */
public class BurritoServer implements Runnable {
    private final String name;



    public int custId;
    public int numBurritos;
    //public static int customersWaiting = 0;
    public static final int CHAIRS = 15;
    public static int freeSeats = CHAIRS;


    Semaphore customers = new Semaphore(1);
    Semaphore servers = new Semaphore(0);
    Semaphore register = new Semaphore(1);
    Semaphore counters = new Semaphore(3);
    Semaphore waitingArea = new Semaphore(15);

    //protected BlockingQueue<Customer> blockingQueue;
    LinkedList<Customer> customersWaiting;

    public BurritoServer(List<Customer> customersWaiting, String name) {
        this.customersWaiting = (LinkedList<Customer>) customersWaiting;
        this.name = name;
        this.customersWaiting = (LinkedList<Customer>) customersWaiting;
    }

    //public String printName(){
        //return name;
    //}

    @Override
    public String toString(){
        return "Server name: " + name;
    }



    @Override
    public void run() {
       while(customersWaiting.size() > 0) {  // runs in an infinite loop

           System.out.println("blah blah blah");

           try {
               customers.acquire();
               this.makeBurrito();  //making burritos
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
                customers.release();
            System.out.println("LOL LOL LOL");


            }

    }

    // this method will simulate making burritos

    public void makeBurrito() throws InterruptedException {// I need to find a way to make burritos only for orders > 0!!!!!
        System.out.println("WAITING " + customersWaiting);

            //if (customersWaiting.size() > 0){
                System.out.println(" Server " + name + " is serving customer " + customersWaiting.pollFirst());
                    //}
           // else {
                //System.out.println("The are no customers waiting");
           // }




    }


}



