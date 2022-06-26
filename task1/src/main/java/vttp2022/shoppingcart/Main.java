package vttp2022.shoppingcart;

import java.io.IOException;

public class Main {
    public static void main( String[] args ) throws IOException 
    {
        String directory = args[0]; // store the parameter into a named variable
        Repository repo = new Repository(directory); // instantiate the repo class
        Session shopping = new Session(repo); // instantiate the session class
        shopping.start(); // using the shopping class method to start the session
    }
}
