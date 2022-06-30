package vttp2022.shoppingcart.server;

import java.io.IOException;

public class Main {
    public static void main( String[] args ) throws IOException 
    {
        String directory = args[0]; // store the parameter into a named variable
        Repository repo = new Repository(directory); // instantiate the repo class
        Server server = new Server(repo , Integer.parseInt(args[1]));
        server.start();
    }
}
