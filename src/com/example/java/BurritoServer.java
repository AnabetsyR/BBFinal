package com.example.java;

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
    List<Customer> customersWaiting;

    public BurritoServer(List<Customer> customersWaiting, String name) {
        this.customersWaiting = customersWaiting;
        this.name = name;
    }

    public String printName(){
        return name;
    }

    @Override
    public String toString(){
        return "Server name: " + name;
    }



    @Override
    public void run() {
        while(!customersWaiting.isEmpty()) {  // runs in an infinite loop
            //try {
            //counters.acquire(); //just commented out at 7:40
            //System.out.println("QUEUE " + customersWaiting);
            //while (!customersWaiting.isEmpty()) {
                try {
                    //servers.acquire();
                    customers.acquire();
                    System.out.println("QUEUE " + customersWaiting);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //customers.acquire(); //comm at 4pm

                //System.out.println("waiting cust " + customersWaiting);
               /* if(!customersWaiting.isEmpty()) {
                int burritos = customersWaiting.get(0).numBurritos;

                    if (burritos <= 0) {
                        customersWaiting.remove(0);
                    } else if (burritos - 3 > 0) {
                        //numBurritos -=3;
                        Customer customer = new Customer(customersWaiting, custId, numBurritos);
                        customersWaiting.add(customer);
                    }
                } */

                System.out.println("blah blah blah");

                //customers.acquire();
                this.makeBurrito();  //making burritos

                //System.out.println("WAITING " + customersWaiting);
                //customers.release();
                servers.release();
            System.out.println("LOL LOL LOL");


                //customers.release();comm at 4pm

                // counters.release(); //just commented out at 7:40

                //freeSeats++; // one chair gets free

                //} catch (InterruptedException ex) {}
            }

    }

    // this method will simulate making burritos

    public void makeBurrito(){// I need to find a way to make burritos only for orders > 0!!!!!
        System.out.println("WAITING " + customersWaiting);

            while (customersWaiting.size() > 0){

                System.out.println(" Server " + name + " is serving customer " + customersWaiting.remove(0));
                        //numBurritos -= 3;
                        //Customer customer = new Customer(customersWaiting, custId, numBurritos);
                        //customersWaiting.add(customer);
                    }
            //customers.release();



    }
            //else {
                //System.out.println("The are no customers waiting");
            //}


}



