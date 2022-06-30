package vttp2022.shoppingcart;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Repository directory;
    private int port = 0;
    private Session session;
    
    public Server (Repository directory, int port) {
        this.directory = directory;
        this.port = port;
    }

    public void start () throws IOException {
        ServerSocket server = new ServerSocket(port);
        System.out.printf("Server started, listening on port %d\n" , port);

        while (true) {
            Socket sock = server.accept();
            session = new Session(directory, sock);
            session.start();
        }   
    }




}
