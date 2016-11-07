package com.example.java;


import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("*** Welcome to Burrito Brothers ***" + "\n");

        int custId;
        int numBurritos;

        //Creating a linked list to hold waiting customers
        LinkedList<Customer> customersWaiting = new LinkedList<Customer>();

        //Obtaining customers from text file and storing their information in string array
        Scanner fileToRead = new Scanner(new File("customers.txt"));
        while (fileToRead.hasNext()) {
            String[] line = fileToRead.nextLine().split(" ");
            custId = Integer.parseInt(line[0]);
            numBurritos = Integer.parseInt(line[1]);

                if ((numBurritos > 0) && (customersWaiting.size() < 15)) {

                    //Creating new Customer object
                    Customer customer = new Customer(customersWaiting, custId, numBurritos);

                    //Adding customers to linked list
                    customersWaiting.add(customer);

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

                    new Thread(customer).start();
                }
        }

        final String[] SERVERS = {"Anne", "Joe", "Bob"};

        //Creation of server threads
        for (int i = 0; i < SERVERS.length; i++) {
            BurritoServer server = new BurritoServer(customersWaiting, SERVERS[i]);
            new Thread(server).start();

        }

    }
}
