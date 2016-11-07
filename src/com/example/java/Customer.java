package com.example.java;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * Created by AnabetsyRivero on 10/31/16.
 */
public class Customer implements Runnable {
    //protected BlockingQueue<Customer> blockingQueue;
    LinkedList<Customer> customersWaiting;


    //public static int customersWaiting = 0;
    public static final int CHAIRS = 15;
    private Semaphore waitingArea = new Semaphore(15);
    private Semaphore servers = new Semaphore(0);
    private Semaphore customers = new Semaphore(1);
    private Semaphore counters = new Semaphore(3);//just now



    public int custId;
    public int numBurritos;

    private boolean notServed = true;
    private int freeSeats = 15;

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
        Semaphore register = new Semaphore(1);
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

    //Customer currCustomer = new Customer(custId, numBurritos);


    @Override
    public void run() {
        while (notServed) {  // as long as the customer is not served

            //tries to get access to the chairs

            if (customersWaiting.size() < 15) {  //if there are any free seats

                try {
                    //waitingArea.tryAcquire();
                    waitingArea.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //System.out.println("The shop is full. Customer " + this.getCustId() + " will have to return at a later time");
                }
                freeSeats--;  //sitting down on a chair
                System.out.println("Customer " + this.getCustId() + " with an order of " + this.getNumBurritos() + " burritos just sat down." + "\n");
                System.out.println("in queue " + customersWaiting);

                //if(numBurritos > 0){
                waitingArea.release();

                try {
                    counters.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                try {
                    customers.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

              /*  Customer customer = new Customer(customersWaiting, custId, numBurritos);
                customersWaiting.add(customer);

                Collections.sort(customersWaiting, new Comparator<Customer>() {

                    public int compare(Customer c1, Customer c2) {
                        if (c1.getNumBurritos() > c2.getNumBurritos()) {
                            return 1;
                        } else if (c1.getNumBurritos() < c2.getNumBurritos()) {
                            return -1;
                        }
                        return 0;
                    }

                }); */

                //servers.release();
                customers.release();


                this.get_burrito();
                counters.release();
                this.pay_burritos();
                this.leave_shop();
               try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 //}//end of while cust size < 15
                //} //end of while not served


                if(numBurritos > 3){
                    numBurritos -= 3;
                    try {
                        waitingArea.acquire(); //just added at 2:49 pm
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        customers.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Customer customer1 = new Customer(customersWaiting, custId, numBurritos);
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

                    }); //Added this at 1:32 pm
                    // freeSeats--;

                    waitingArea.release();//just added at 2:49 pm

                    //servers.release();
                    customers.release();

                    //notServed = true;


                } else if((numBurritos > 0) && (numBurritos <= 3)) {
                    numBurritos -= 3;
                    if (numBurritos <= 0) {
                        notServed = false;
                    } else {
                        //notServed = true;
                        try {
                            customers.acquire(); //just added at 2:55pm
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Customer customer2 = new Customer(customersWaiting, custId, numBurritos);
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

                        });
                        customers.release();

                        waitingArea.release();

                        try {
                            counters.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        counters.release();
                        this.get_burrito();
                        //customers.release();
                        this.pay_burritos();
                        this.leave_shop();
                    }
                }

            } //End of if waiting area < 15
            else  {  // there are no free seats
                System.out.println("There are no free seats. Customer " + this.getCustId() + " has left Burrito Brothers." + "\n");
                notServed=true; // the customer will leave since there are no spots left in the seating area
            }
        }
    }

}
