package com.example.java;


import java.io.File;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("*** Welcome to Burrito Brothers ***" + "\n");

        //int customersWaiting = 0;
        //final int CHAIRS = 15;
        //int freeSeats = CHAIRS;
        int custId;
        int numBurritos;
        boolean notServed = true;


        //List<Customer> customersWaiting = new ArrayList<Customer>(15);
        List<Customer> customersWaiting = new LinkedList<Customer>();//try this one and see what happens!!



        Semaphore customers = new Semaphore(1);//Added a 1 to try using the two semaphores to control list of customers
        Semaphore servers = new Semaphore(0);

        Semaphore register = new Semaphore(1);
        Semaphore counters = new Semaphore(3);
        Semaphore waitingArea = new Semaphore(15);

        final String[] SERVERS = {"Anne", "Joe", "Bob"};


        //Obtaining customers from text file
        Scanner fileToRead = new Scanner(new File("customers.txt"));

        while (fileToRead.hasNext()) {

            String[] line = fileToRead.nextLine().split(" ");
            custId = Integer.parseInt(line[0]);
            numBurritos = Integer.parseInt(line[1]);

                if ((numBurritos > 0) && (customersWaiting.size() < 15)) {


                    Customer customer = new Customer(customersWaiting, custId, numBurritos);

                    //if((numBurritos > 0) && (waitingArea.availablePermits() > 0)) {
                    //customers.acquire();//just added at 9:50 pm

                    customersWaiting.add(customer);


                    //System.out.println("Customers in list " + customersWaiting);


                    //Sorting waiting customers

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


                    //customers.release();
                    // }//end of if statement
                    //System.out.println("Cust in list after sort " + customersWaiting);
                    //System.out.println("Customer " + custId + " with an order of " + numBurritos + " burritos just sat down." + "\n");

                    //servers.release();//just added at 9"51 pm
                    new Thread(customer).start();
                }


        }


        //Creation of server threads
        for (int i = 0; i < SERVERS.length; i++) {
            BurritoServer server = new BurritoServer(customersWaiting, SERVERS[i]);
            new Thread(server).start();

        }

    }
}
