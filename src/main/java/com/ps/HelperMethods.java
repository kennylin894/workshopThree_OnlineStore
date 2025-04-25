package com.ps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

    public static ArrayList<Product> filteredDisplayProducts(String filterBy, String searchedValue,double priceFilter) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        if (filterBy.toLowerCase().equals("sku")) {
            for (Product product : getInventory()) {
                if (product.getSku().toLowerCase().equals(searchedValue)) {
                    filteredProducts.add(product);
                }
            }
        } else if (filterBy.toLowerCase().equals("name")) {
            for (Product product : getInventory()) {
                if (product.getName().toLowerCase().equals(searchedValue)) {
                    filteredProducts.add(product);
                }
            }
        //less than
        } else if (filterBy.toLowerCase().equals("price") && priceFilter == 1) {
            Double searchedValueDouble = Double.parseDouble(searchedValue);
            for(Product product: getInventory())
            {
                if(product.getPrice() <= searchedValueDouble)
                {
                    filteredProducts.add(product);
                }
            }
        //greater than
        } else if (filterBy.toLowerCase().equals("price") && priceFilter == 2)
        {
            Double searchedValueDouble = Double.parseDouble(searchedValue);
            for(Product product: getInventory())
            {
                if(product.getPrice() >= searchedValueDouble)
                {
                    filteredProducts.add(product);
                }
            }
        }
        else if (filterBy.toLowerCase().equals("department"))
        {
            for(Product product: getInventory()) {
                if(product.getDepartment().toLowerCase().equals(searchedValue)) {
                    filteredProducts.add(product);
                }
            }
        }
        return filteredProducts;
    }

    public static double getTotal(ArrayList<Product> cart) {
        double total = 0;
        for(Product product: cart)
        {
            total += product.getPrice();
        }
        return total;
    }

    public static void removeFromCart(String name,ArrayList<Product> cart) {
        boolean found = false;
        for(Product product:cart)
        {
            if(name.toLowerCase().equals(product.getName().toLowerCase()))
            {
                cart.remove(product.getName());
                found = true;
            }
        }
        if(found == true)
        {
            System.out.println(name + "has been successfully removed from your cart.");
        }
        else
        {
            System.out.println("Item doesn't exist in your cart.");
        }
    }
}



