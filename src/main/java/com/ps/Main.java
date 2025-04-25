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
        int mainMenuCommand;
        int addCart;
        do {
            System.out.println("Would you like to:");
            System.out.println("1) Display Products");
            System.out.println("2) Search For Products");
            System.out.println("3) Display Cart");
            System.out.println("0) Exit");
            mainMenuCommand = mainMenuScanner.nextInt();
            switch(mainMenuCommand)
            {
                //display the products
                case 1:
                    System.out.println("SKU|Product Name|Price|Department");
                    ArrayList<Product> inv = HelperMethods.getInventory();
                    for(Product product: inv)
                    {
                        System.out.println(product.getSku() + "|" + product.getName() + "|" + product.getPrice() + "|" + product.getDepartment());
                    }
                    System.out.println();
                    System.out.println("Would you like to add something to the cart?");
                    System.out.println("1) Yes");
                    System.out.println("2) No - Exit");
                    addCart = intScanner.nextInt();
                    if(addCart == 1)
                    {
                        System.out.println("Please enter the name of the item you would like to add.");
                        String item = stringScanner.nextLine();
                        boolean found = false;
                        for(Product product: inv)
                        {
                            if(item.toLowerCase().equals(product.getName().toLowerCase()))
                            {
                                cart.add(product);
                                found = true;
                            }
                        }
                        if(found == true)
                        {
                            System.out.println("Your Product has been successfully added to cart");
                            System.out.println();
                        }
                        else
                        {
                            System.out.println("Item doesnt exist, not added to cart");
                            System.out.println();
                        }
                    }
                    break;
                //display products based on a filter
                case 2:
                    System.out.println("Enter what you would like to search by?");
                    System.out.println("SKU,Name,Price,Department");
                    String userFilterCategory = stringScanner.nextLine();
                    double priceFilter = 0.00;
                    if(userFilterCategory.toLowerCase().equals("price"))
                    {
                        System.out.println("Less than or greater than a certain price?");
                        System.out.println("1) Less Than");
                        System.out.println("2) Greater Than");
                        priceFilter = intScanner.nextDouble();
                        if(priceFilter > 2)
                        {
                            System.out.println("Please enter a valid number.");
                            System.out.println();
                            break;
                        }
                    }
                    System.out.println("Now please enter the sku,name, price or department you want to search by:");
                    String userFilterValue = stringScanner.nextLine();
                    //gets all the filtered products
                    ArrayList<Product> filteredProducts = HelperMethods.filteredDisplayProducts(userFilterCategory,userFilterValue,priceFilter);
                    for(Product product: filteredProducts)
                    {
                        System.out.println(product.getSku() + "|" + product.getName() + "|" + product.getPrice() + "|" + product.getDepartment());
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println("Would you like to add something to the cart?");
                    System.out.println("1) Yes");
                    System.out.println("2) No - Exit");
                    addCart = intScanner.nextInt();
                    if(addCart == 1)
                    {
                        System.out.println("Please enter the name of the item you would like to add.");
                        String item = stringScanner.nextLine();
                        boolean found = false;
                        for(Product product: filteredProducts)
                        {
                            if(item.toLowerCase().equals(product.getName().toLowerCase()))
                            {
                                cart.add(product);
                                found = true;
                            }
                        }
                        if(found == true)
                        {
                            System.out.println("Your Product has been successfully added to cart");
                            System.out.println();
                        }
                        else
                        {
                            System.out.println("Item doesnt exist, not added to cart");
                            System.out.println();
                        }
                    }
                    break;
                //displays your cart
                case 3:
                    System.out.println("Would you like to");
                    System.out.println("1) Check Out: ");
                    System.out.println("2) View Cart");
                    System.out.println("3) Remove Item From Cart");
                    int cartInput = intScanner.nextInt();
                    if(cartInput == 1)
                    {

                    }
                    else if(cartInput == 2)
                    {
                        //checks if the users cart is empty
                        if(cart.isEmpty())
                        {
                            System.out.println("Your cart is empty.");
                            System.out.println();
                        }
                        else
                        {
                            for(Product product: cart)
                            {
                                System.out.println(product.getSku() + "|" + product.getName() + "|" + product.getPrice() + "|" + product.getDepartment());
                            }
                            //prints the carts total value
                            System.out.println("Your total is: $" + HelperMethods.getTotal(cart));
                            System.out.println();
                        }
                    } else if (cartInput == 3) {
                        if(cart.isEmpty())
                        {
                            System.out.println("Your cart is empty");
                            System.out.println();
                            break;
                        }
                        else
                        {
                            System.out.println("What item would you like to remove?");
                            System.out.println("Please enter the name of the item you want to remove");
                            String name = stringScanner.nextLine();
                            HelperMethods.removeFromCart(name,cart);
                        }
                    }
            }
       }while(mainMenuCommand != 0);
    }
}