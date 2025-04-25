package com.ps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class HelperMethods {

    public static ArrayList<Product> getInventory() {
        ArrayList<Product> products = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("products.csv"));
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String sku = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                String department = parts[3];
                products.add(new Product(sku, name, price, department));
            }
        } catch (Exception e) {
            System.out.println("File not found.");
        }
        return products;
    }

    public static ArrayList<Product> checkCart(ArrayList<Product> cart) {
        return cart;
    }

    public static ArrayList<Product> filteredDisplayProducts(String filterBy, String searchedValue, double priceFilter) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        if (filterBy.toLowerCase().equals("sku")) {
            for (Product product : getInventory()) {
                if (product.getSku().toLowerCase().equals(searchedValue.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }
        } else if (filterBy.toLowerCase().equals("name")) {
            for (Product product : getInventory()) {
                if (product.getName().toLowerCase().equals(searchedValue.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }
            //less than
        } else if (filterBy.toLowerCase().equals("price") && priceFilter == 1) {
            Double searchedValueDouble = Double.parseDouble(searchedValue);
            for (Product product : getInventory()) {
                if (product.getPrice() <= searchedValueDouble) {
                    filteredProducts.add(product);
                }
            }
            //greater than
        } else if (filterBy.toLowerCase().equals("price") && priceFilter == 2) {
            Double searchedValueDouble = Double.parseDouble(searchedValue.toLowerCase());
            for (Product product : getInventory()) {
                if (product.getPrice() >= searchedValueDouble) {
                    filteredProducts.add(product);
                }
            }
        } else if (filterBy.toLowerCase().equals("department")) {
            for (Product product : getInventory()) {
                if (product.getDepartment().toLowerCase().equals(searchedValue.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }
        }
        return filteredProducts;
    }

    public static double getTotal(ArrayList<Product> cart) {
        double total = 0;
        for (Product product : cart) {
            total += product.getPrice() * product.getCount();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public static void removeFromCart(String name, ArrayList<Product> cart) {
        boolean found = false;
        int removeCount = 1;
        for (Product product : cart) {
            if (name.toLowerCase().equals(product.getSku().toLowerCase())) {
                if(product.getCount() == 1)
                {
                    cart.remove(product);
                    found = true;
                    break;
                }
                else if(product.getCount() > 1)
                {
                    System.out.println("How many of this item would you like to remove?");
                    Scanner scanner = new Scanner(System.in);
                    int count = scanner.nextInt();
                    removeCount = count;
                    if(product.getCount() == count)
                    {
                        cart.remove(product);
                        found = true;
                        break;
                    }
                    else
                    {
                        int newCount = product.getCount() - count;
                        product.setCount(newCount);
                        found = true;
                        break;
                    }
                }
            }
        }
        if (found == true) {
            System.out.println(removeCount + "x of " + name + " has been successfully removed from your cart.");
        } else {
            System.out.println("Item doesn't exist in your cart.");
        }
    }

    public static int verifyPayment(double userTotalPayment,double cartTotal,ArrayList<Product> cart)
    {
        Scanner scanner = new Scanner(System.in);
        double change;
        //user paid exactly enough
        if(userTotalPayment == cartTotal)
        {
            System.out.println("Thank you for shopping with us.");
            System.out.println("Your payment was successful.");
            cart.clear();
            return 1;
        }
        //user paid more than total
        else if(userTotalPayment > cartTotal)
        {
            change = userTotalPayment - cartTotal;
            System.out.println("Thank you for shopping with us.");
            System.out.println("Here is your change of $" + change);
            cart.clear();
            return 1;
        }
        //user paid less than total
        else {
            while (userTotalPayment < cartTotal) {
                double missing = cartTotal - userTotalPayment;
                System.out.println("You are missing $" + missing);
                System.out.println("Do you have enough?");
                System.out.println("1) Yes");
                System.out.println("2) No");
                int enough = scanner.nextInt();
                if(enough == 1)
                {
                    System.out.print("Please enter the rest of the payment: ");
                    double additionalPayment = scanner.nextDouble();
                    userTotalPayment += additionalPayment;
                }
                else if(enough ==2 )
                {
                    System.out.println("Damn you broke.");
                    return -1;
                }
                else
                {
                    System.out.println("Invalid input, try again.");
                }
            }
            //if user overpaided while under paying
            change = userTotalPayment - cartTotal;
            if (change > 0) {
                System.out.println("Here is your change of $" + change);
            }
            System.out.println("Your payment was successful.");
            cart.clear();
            return 1;
        }
    }
}



