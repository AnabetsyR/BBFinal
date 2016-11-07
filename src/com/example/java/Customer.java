package com.example.java;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * Created by AnabetsyRivero on 10/31/16.
 */
public class Customer implements Runnable {

    LinkedList<Customer> customersWaiting;

    public int custId;
    public int numBurritos;
    private boolean notServed = true;
    private int freeSeats = 15;

    private Semaphore waitingArea = new Semaphore(15);
    private Semaphore customers = new Semaphore(1);
    private Semaphore counters = new Semaphore(3);
    private Semaphore register = new Semaphore(1);


    public Customer(List<Customer> customersWaiting, int custId, int numBurritos) {
        this.customersWaiting = (LinkedList<Customer>) customersWaiting;
        this.custId = custId;
        this.numBurritos = numBurritos;
    }


    public int getCustId(){
        return custId;
    }


    public int getNumBurritos(){
        return numBurritos;
    }


    @Override
    public String toString(){
        return this.getCustId() + ": " + this.getNumBurritos() + " burritos" + "\n";
    }



    //this method simulates getting a burrito
    public void get_burrito(){
        try {
            sleep(1000);
        } catch (InterruptedException ex) {}
    }

    //this method simulates paying for burritos
    public void pay_burritos(){
        try {
            register.acquire();
            sleep(2000);//just added 6:17
            System.out.println("Customer " + this.getCustId() + " is paying for his order... " + "\n");
            register.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //this method simulates leaving the shop
    public void leave_shop(){
        System.out.println("Customer " + this.getCustId() + " is leaving the shop. " + "\n" );
    }




    @Override
    public void run() {
        while (notServed) {  // as long as the customer is not served

            //tries to get access to the chairs
            if (customersWaiting.size() < 15) {
                try {
                    waitingArea.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Customer " + this.getCustId() + " with an order of " + this.getNumBurritos() + " burritos just sat down." + "\n");

                freeSeats--;
                try {
                    customers.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  //just did at 11:33


                waitingArea.release();
               try {
                    counters.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    counters.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.get_burrito();

                customers.release();//just now
                counters.release();
                this.pay_burritos();
                this.leave_shop();


                if(numBurritos > 3){
                    numBurritos -= 3;
                    try {
                        waitingArea.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   // System.out.println("Customer lol " + this.getCustId() + " with an order of " + this.getNumBurritos() + " burritos just sat down." + "\n");

                    freeSeats--;

                    try {
                        customers.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            /*       Customer customer1 = new Customer(customersWaiting, custId, numBurritos);
                    customersWaiting.add(customer1);

                    Collections.sort(customersWaiting, new Comparator<Customer>() {

                        public int compare(Customer c1, Customer c2) {
                            if (c1.getNumBurritos() > c2.getNumBurritos()) {
                                return 1;
                            } else if (c1.getNumBurritos() < c2.getNumBurritos()) {
                                return -1;
                            }
                            return 0;
                        }

                    });  */

                    waitingArea.release();
                    customers.release();

                    try {
                        counters.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.get_burrito();
                    counters.release();

                    this.pay_burritos();
                    this.leave_shop();


                } else if((numBurritos > 0) && (numBurritos <= 3)) {
                    numBurritos -= 3;
                    if (numBurritos <= 0) {
                        notServed = false;
                    } else {
                        try {
                            waitingArea.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        freeSeats--;


                        try {
                            customers.acquire(); //just added at 2:55pm
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    /*    Customer customer2 = new Customer(customersWaiting, custId, numBurritos);
                        customersWaiting.add(customer2);

                        Collections.sort(customersWaiting, new Comparator<Customer>() {

                            public int compare(Customer c1, Customer c2) {
                                if (c1.getNumBurritos() > c2.getNumBurritos()) {
                                    return 1;
                                } else if (c1.getNumBurritos() < c2.getNumBurritos()) {
                                    return -1;
                                }
                                return 0;
                            }

                        });   */

                        waitingArea.release();
                        customers.release();

                        try {
                            counters.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        this.get_burrito();
                        counters.release();
                        this.pay_burritos();
                        this.leave_shop();
                    }
                }

            }
            else  {  // there are no free seats
                System.out.println("There are no free seats. Customer " + this.getCustId() + " has left Burrito Brothers." + "\n");
                notServed=true; // the customer will leave since there are no spots left in the seating area
            }
        }
    }

}
