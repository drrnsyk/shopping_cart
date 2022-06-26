package vttp2022.shoppingcart;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Cart {
    
    // properties
    List<String> cartList = new LinkedList<>();
    String userName = "";

    // constructors
    public Cart (String userName) {
        this.userName = userName;
    }

    // methods
    public void listCart() {
        if (cartList.isEmpty()) {
            System.out.println("The cart is empty");
        }
        else
        {
            for (int i = 0; i < cartList.size(); i++) {
                System.out.printf("%d. %s\n" , i+1 , cartList.get(i));
            }
        }
    }

    public void addToCart(String item) {
        cartList.add(item);
    }

    public void deleteFromCart(int num) {
        cartList.remove(num-1);
    }

    public void saveCart(String directory) throws IOException {
        FileWriter w = new FileWriter(directory + "/" + userName + ".txt");
        BufferedWriter bw = new BufferedWriter(w);
        for (int i = 0; i < cartList.size(); i++) {
            String line = String.valueOf(i+1) + "." + " " + cartList.get(i);
            bw.write(line);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public void existingCart(List<String> existingContent) {
        if (!existingContent.equals(null)) {
            for (int i = 0; i < existingContent.size(); i++) {
                cartList.add(existingContent.get(i));
            }
        }
    }

    public boolean ifExist(String item) {
        for (int i = 0; i < cartList.size(); i++) {
            if (item.equalsIgnoreCase(cartList.get(i))) 
                return true;
        }
        return false;
    }
}
