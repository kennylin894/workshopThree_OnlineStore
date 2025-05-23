package com.ps;

import javax.script.ScriptContext;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner mainMenuScanner = new Scanner(System.in);
    static Scanner stringScanner = new Scanner(System.in);
    static Scanner intScanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Online-Store");
        System.out.println("===========================");
        ArrayList<Product> cart = new ArrayList<>();
        ArrayList<Product> inv = HelperMethods.getInventory();
        int mainMenuCommand;
        int addCart;
        do {
            System.out.println("Would you like to:");
            System.out.println("==================");
            System.out.println("[1] Display Products");
            System.out.println("[2] Search For Products");
            System.out.println("[3] Display Cart");
            System.out.println("[0] Exit");
            mainMenuCommand = mainMenuScanner.nextInt();
            switch (mainMenuCommand) {
                //display the products
                case 1:
                    System.out.println("SKU|Product Name|Price|Department");
                    for (Product product : inv) {
                        System.out.println(product.getSku() + "|" + product.getName() + "|$" + product.getPrice() + "|" + product.getDepartment());
                    }
                    System.out.println();
                    System.out.println("Would you like to add something to the cart?");
                    System.out.println("[1] Yes");
                    System.out.println("[2] No - Exit");
                    addCart = intScanner.nextInt();
                    if (addCart == 1) {
                        System.out.println("Please enter the sku of the item you would like to add.");
                        String item = stringScanner.nextLine();
                        System.out.println("How many would you like to buy?");
                        System.out.println("Please enter the amount:");
                        int itemCount = intScanner.nextInt();
                        boolean found = false;
                        for (Product product : inv) {
                            if (item.toLowerCase().equals(product.getSku().toLowerCase())) {
                                cart.add(product);
                                product.setCount(itemCount);
                                System.out.println(product.getName() + " - " + product.getCount() + "x" + " has been successfully added to cart");
                                found = true;
                            }
                        }
                        if (found == false) {
                            System.out.println("Item doesnt exist, not added to cart.");
                            System.out.println();
                        }
                    }
                    break;
                //display products based on a filter
                case 2:
                    System.out.println("What category you would like to search by?");
                    System.out.println("SKU,Name,Price,Department");
                    String userFilterCategory = stringScanner.nextLine();
                    double priceFilter = 0.00;
                    if (userFilterCategory.toLowerCase().equals("department")) {
                        ArrayList<String> departmentCategories = new ArrayList<>();
                        System.out.println("These are the following departments.");
                        for (Product product : inv) {
                            String department = product.getDepartment();
                            if (!departmentCategories.contains(department)) {
                                departmentCategories.add(department);
                                System.out.println("- " + department);
                            }
                        }
                    }
                    double tempPriceGL = 0;
                    if (userFilterCategory.toLowerCase().equals("price")) {
                        System.out.println("Less than or greater than a certain price?");
                        System.out.println("[1] Less Than");
                        System.out.println("[2] Greater Than");
                        priceFilter = intScanner.nextDouble();
                        tempPriceGL = priceFilter;
                        if (priceFilter > 2) {
                            System.out.println("Please enter a valid number.");
                            System.out.println();
                        }
                        System.out.println("Please enter the price:");
                        String userFilterValue = stringScanner.nextLine();
                        //less
                        if (tempPriceGL == 1) {
                            System.out.println("These are the products under $" + userFilterValue + ".");
                            System.out.println("====================================");
                            ArrayList<Product> filteredProducts = HelperMethods.filteredDisplayProducts(userFilterCategory, userFilterValue, priceFilter);
                            for (Product product : filteredProducts) {
                                System.out.println(product.getSku() + "|" + product.getName() + "|$" + product.getPrice() + "|" + product.getDepartment());
                            }
                            System.out.println();
                        }
                        //greater than
                        else if (tempPriceGL == 2) {
                            System.out.println("These are the products over $" + userFilterValue + ".");
                            System.out.println("====================================");
                            ArrayList<Product> filteredProducts = HelperMethods.filteredDisplayProducts(userFilterCategory, userFilterValue, priceFilter);
                            for (Product product : filteredProducts) {
                                System.out.println(product.getSku() + "|" + product.getName() + "|$" + product.getPrice() + "|" + product.getDepartment());
                            }
                            System.out.println();
                        }
                        System.out.println("Would you like to add something to the cart?");
                        System.out.println("[1] Yes");
                        System.out.println("[2] No - Exit");
                        addCart = intScanner.nextInt();
                        if (addCart == 1) {
                            System.out.println("Please enter the sku of the item you would like to add.");
                            String item = stringScanner.nextLine();
                            System.out.println("How many would you like to buy?");
                            System.out.println("Please enter the amount:");
                            int itemCount = intScanner.nextInt();
                            boolean found = false;
                            for (Product product : HelperMethods.filteredDisplayProducts(userFilterCategory, userFilterValue, priceFilter)) {
                                if (item.toLowerCase().equals(product.getSku().toLowerCase())) {
                                    cart.add(product);
                                    product.setCount(itemCount);
                                    System.out.println(product.getName() + " - " + product.getCount() + "x" + " has been successfully added to cart");
                                    found = true;
                                }

                            }
                            if (found == false) {
                                System.out.println("Item doesnt exist, not added to cart.");
                                System.out.println();
                            }
                        }
                        break;
                    } else {
                        System.out.println("Now please enter the sku,name, price or department you want to search by:");
                        String userFilterValue = stringScanner.nextLine();
                        System.out.println("These are the following products in the " + userFilterValue + " category.");
                        System.out.println("=========================================================================");
                        //gets all the filtered products
                        ArrayList<Product> filteredProducts = HelperMethods.filteredDisplayProducts(userFilterCategory, userFilterValue, priceFilter);
                        for (Product product : filteredProducts) {
                            System.out.println(product.getSku() + "|" + product.getName() + "|$" + product.getPrice() + "|" + product.getDepartment());
                        }
                        System.out.println();
                        if (filteredProducts.isEmpty()) {
                            System.out.println("Invalid Category, please try again.");
                            break;
                        } else {
                            System.out.println("Would you like to add something to the cart?");
                            System.out.println("[1] Yes");
                            System.out.println("[2] No - Exit");
                            addCart = intScanner.nextInt();
                            if (addCart == 1) {
                                System.out.println("Please enter the sku of the item you would like to add.");
                                String item = stringScanner.nextLine();
                                System.out.println("How many would you like to buy?");
                                System.out.println("Please enter the amount:");
                                int itemCount = intScanner.nextInt();
                                boolean found = false;
                                for (Product product : filteredProducts) {
                                    if (item.toLowerCase().equals(product.getSku().toLowerCase())) {
                                        cart.add(product);
                                        product.setCount(itemCount);
                                        System.out.println(product.getName() + " - " + product.getCount() + "x" + " has been successfully added to cart");
                                        found = true;
                                    }

                                }
                                if (found == false) {
                                    System.out.println("Item doesnt exist, not added to cart.");
                                    System.out.println();
                                }
                            }

                            break;
                        }
                    }
                    //displays your cart
                case 3:
                    System.out.println("Would you like to");
                    System.out.println("[1] Check Out: ");
                    System.out.println("[2] View Cart");
                    System.out.println("[3] Remove Item From Cart");
                    int cartInput = intScanner.nextInt();
                    //user wants to check out
                    if (cartInput == 1) {
                        if (cart.isEmpty()) {
                            System.out.println("Your cart is empty.");
                            System.out.println();
                            break;
                        }
                        System.out.println("Welcome to the checkout.");
                        double cartTotal = HelperMethods.getTotal(cart);
                        System.out.println("Your total will be $" + cartTotal);
                        System.out.println("Please enter your payment amount.");
                        double payment = intScanner.nextDouble();
                        //payment was successful
                        HelperMethods.verifyPayment(payment, cartTotal, cart);
                    } else if (cartInput == 2) {
                        //checks if the users cart is empty
                        if (cart.isEmpty()) {
                            System.out.println("Your cart is empty.");
                            System.out.println();
                        } else {
                            System.out.println("These are the items in your cart.");
                            System.out.println("=================================");
                            for (Product product : cart) {
                                System.out.println(product.getSku() + "|" + product.getName() + "|$" + product.getPrice() + "|" + product.getDepartment() + "| x - " + product.getCount());
                            }
                            //prints the carts total value
                            System.out.println("Your total is: $" + HelperMethods.getTotal(cart));
                            System.out.println();
                        }
                    } else if (cartInput == 3) {
                        if (cart.isEmpty()) {
                            System.out.println("Your cart is empty.");
                            System.out.println();
                            break;
                        } else {
                            System.out.println("What item would you like to remove from your cart?");
                            for (Product product : cart) {
                                System.out.println(product.getSku() + "|" + product.getName() + " - $" + product.getPrice() + "|" + product.getDepartment() + "| x - " + product.getCount());
                            }
                            System.out.println("Please enter the sku of the item you want to remove.");
                            String sku = stringScanner.nextLine();
                            HelperMethods.removeFromCart(sku, cart);
                        }
                    }
            }
        } while (mainMenuCommand != 0);
        System.out.println("             Thank you for shopping with us!         ");
        System.out.println("=====================================================");
        System.out.println("    BBBB    Y   Y   EEEE     BBBB    Y   Y   EEEE    ");
        System.out.println("    B   B    Y Y    E        B   B    Y Y    E       ");
        System.out.println("    BBBB      Y     EEEE     BBBB      Y     EEEE    ");
        System.out.println("    B   B     Y     E        B   B     Y     E       ");
        System.out.println("    BBBB      Y     EEEE     BBBB      Y     EEEE    ");
        System.out.println("=====================================================");
        System.out.println("                 See you next time!                  ");

    }
}