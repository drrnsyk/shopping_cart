package vttp2022.shoppingcart;

import java.io.Console;
import java.io.IOException;


public class Session {
    
    // properties
    private Repository sessionRepo;
    private Cart sessionCart; // declare a Cart Class sessionCart ** remember to initialised **

    // constructors
    public Session (Repository repo) { // constructor for repo to be pass into session in main
        this.sessionRepo = repo;
    }

    // you will only need to do this if you need to construct a something
    // public Session (Cart cart) { 
    //     this.sessionCart = cart;
    // }
 
    // methods
    public void start () throws IOException {
        sessionRepo.open(); // using the repo class methods to open the directory
        System.out.println();
        Console cons = System.console();
        String userInput = cons.readLine(">>>>> Please input \"username\" | command = \"load\" <<<<<\nEnter command: "); // read in user input from console
        String[] inputArray = userInput.split(" "); // split the user input string into an string array

        sessionCart = new Cart(inputArray[1]); // ** remember to initialise sessionCart **

        if (inputArray[0].equalsIgnoreCase("LOAD")) { // check the 1st element of array = command input
            sessionRepo.loadFile(inputArray[1]); // check the 2nd element of array = username, using the repo class method to create a new file with the directory + username 
            sessionCart.existingCart(sessionRepo.readFile("./" + sessionRepo.getDirectory() + "/" + inputArray[1] + ".txt"));
        }

        System.out.println();

        

        while (true) {
            userInput = cons.readLine(">>>>> Please enter a command | commands: \"list\" , \"add\" , \"delete\" , \"save\" , \"exit\" <<<<<\nEnter command: "); // read in user input from console
            inputArray = userInput.split(" "); // split the user input string into an string array
            System.out.println();

            if (inputArray[0].equalsIgnoreCase("LIST")) {
                sessionCart.listCart();

            }
            else if (inputArray[0].equalsIgnoreCase("ADD")) {
                for (int i = 1; i < inputArray.length; i++) {
                    if (sessionCart.ifExist(inputArray[i])) {
                        System.out.printf("%s exist in the cart\n" , inputArray[i]);
                    }
                    else
                    {
                        sessionCart.addToCart(inputArray[i]);
                        System.out.printf("%s added the cart\n" , inputArray[i]);
                    }
                }
            }
            else if (inputArray[0].equalsIgnoreCase("DELETE")) {
                sessionCart.deleteFromCart(Integer.parseInt(inputArray[1]));
                System.out.printf("%s deleted from the cart\n" , inputArray[1]);
            }
            else if (inputArray[0].equalsIgnoreCase("SAVE")) {
                sessionCart.saveCart(sessionRepo.getDirectory());
                System.out.printf("Cart contents saved to %s\n" , sessionCart.userName);

            }
            else if (inputArray[0].equalsIgnoreCase("EXIT")) {
                System.out.println("Thank you for shopping with us!");
                break;
            }
            System.out.println();

        }


    }


}
