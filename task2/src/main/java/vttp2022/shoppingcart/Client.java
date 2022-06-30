package vttp2022.shoppingcart;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    
    public static void main(String[] args) throws UnknownHostException, IOException {

        String[] parameter = args[0].split("[@:]");
        String userName = parameter[0];
        String host = parameter[1];
        int port = Integer.parseInt(parameter[2]);

        Socket clientSock = new Socket(host, port);

        OutputStream os = clientSock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(userName); // send the userName request to server
        dos.flush();

        System.out.println();

        InputStream is = clientSock.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        String serverResponse = dis.readUTF(); // receive response on loaded userName cart
        System.out.println(serverResponse);

        System.out.println();

        while (true) {

            is = clientSock.getInputStream();
            dis = new DataInputStream(is);
            serverResponse = dis.readUTF(); // receive response for command input
            System.out.println(serverResponse);

            Console con = System.console();
            String input = con.readLine(">");
            os = clientSock.getOutputStream();
            dos = new DataOutputStream(os);
            dos.writeUTF(input); // send the command request to server
            dos.flush();

            System.out.println();

            is = clientSock.getInputStream();
            dis = new DataInputStream(is);
            serverResponse = dis.readUTF(); // receive response for command input
            System.out.println(serverResponse);
            if (input.equalsIgnoreCase("EXIT")) 
                break;

            System.out.println();
        }

    }
}
