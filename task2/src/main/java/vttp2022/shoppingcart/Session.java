package vttp2022.shoppingcart;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Session {
    
    // properties
    private Repository sessionRepo;
    private Cart sessionCart; // declare a Cart Class sessionCart ** remember to initialised **
    private Socket sessionSock;

    // constructors
    public Session (Repository repo, Socket sock) { // constructor for repo to be pass into session in main
        this.sessionRepo = repo;
        this.sessionSock = sock;
    }

    // you will only need to do this if you need to construct a something
    // public Session (Cart cart) { 
    //     this.sessionCart = cart;
    // }
 
    // methods
    public void start () throws IOException {
        sessionRepo.open(); // using the repo class methods to open the directory
        System.out.println();

        InputStream is = sessionSock.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        String userName = dis.readUTF(); // read in userName from client

        sessionCart = new Cart(userName); // ** remember to initialise sessionCart **
        sessionRepo.loadFile(userName); // check the 2nd element of array = username, using the repo class method to create a new file with the directory + username 
        sessionCart.existingCart(sessionRepo.readFile("./" + sessionRepo.getDirectory() + "/" + userName + ".txt"));
        OutputStream os = sessionSock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(userName + " shopping cart loaded");
        dos.flush();

        System.out.println();

        while (true) {
            os = sessionSock.getOutputStream();
            dos = new DataOutputStream(os);
            dos.writeUTF(">>>>> Please enter a command | commands: \"list\" , \"add\" , \"delete\" , \"save\" , \"exit\" <<<<<\nEnter command: ");
            dos.flush();

            System.out.println();

            is = sessionSock.getInputStream();
            dis = new DataInputStream(is);
            String clientRequest = dis.readUTF(); // read in userName from client
            String[] clientRequestArray = clientRequest.split(" ");

            if (clientRequestArray[0].equalsIgnoreCase("LIST")) {
                sessionCart.listCart();
                os = sessionSock.getOutputStream();
                dos = new DataOutputStream(os);
                dos.writeUTF(sessionCart.feedback);
                dos.flush();
                sessionCart.feedback = "";

            }
            else if (clientRequestArray[0].equalsIgnoreCase("ADD")) {
                for (int i = 1; i < clientRequestArray.length; i++) {
                    if (sessionCart.ifExist(clientRequestArray[i])) {
                        System.out.printf("%s exist in the cart\n" , clientRequestArray[i]);
                        os = sessionSock.getOutputStream();
                        dos = new DataOutputStream(os);
                        dos.writeUTF(clientRequestArray[i] + " exist in the cart");
                        dos.flush();
                    }
                    else
                    {
                        sessionCart.addToCart(clientRequestArray[i]);
                        System.out.printf("%s added the cart\n" , clientRequestArray[i]);
                        os = sessionSock.getOutputStream();
                        dos = new DataOutputStream(os);
                        dos.writeUTF(clientRequestArray[i] + " added to the cart");
                        dos.flush();
                    }
                }
            }
            else if (clientRequestArray[0].equalsIgnoreCase("DELETE")) {
                sessionCart.deleteFromCart(Integer.parseInt(clientRequestArray[1]));
                System.out.printf("%s deleted from the cart\n" , clientRequestArray[1]);
                os = sessionSock.getOutputStream();
                dos = new DataOutputStream(os);
                dos.writeUTF(clientRequestArray[1] + " deleted from the cart");
                dos.flush();
            }
            else if (clientRequestArray[0].equalsIgnoreCase("SAVE")) {
                sessionCart.saveCart(sessionRepo.getDirectory());
                System.out.printf("Cart contents saved to %s\n" , userName);
                os = sessionSock.getOutputStream();
                dos = new DataOutputStream(os);
                dos.writeUTF("Cart contents saved to " + userName);
                dos.flush();

            }
            else if (clientRequestArray[0].equalsIgnoreCase("EXIT")) {
                System.out.println("Thank you for shopping with us!");
                os = sessionSock.getOutputStream();
                dos = new DataOutputStream(os);
                dos.writeUTF("Thank you for shopping with us");
                dos.flush();
                break;
            }
            else 
            {
                System.out.println("Please enter a valid command!");
                os = sessionSock.getOutputStream();
                dos = new DataOutputStream(os);
                dos.writeUTF("Please enter a valid command!");
                dos.flush();
                continue;
            }

            System.out.println();

        }


    }


}
