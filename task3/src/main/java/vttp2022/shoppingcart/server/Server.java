package vttp2022.shoppingcart.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private Repository directory;
    private int port = 0;
    private Session session;
    
    public Server (Repository directory, int port) {
        this.directory = directory;
        this.port = port;
    }

    public void start () throws IOException {

        ExecutorService threadPool = Executors.newFixedThreadPool(3); // for multi threading

        ServerSocket server = new ServerSocket(port);
        System.out.printf("Server started, listening on port %d\n" , port);

        while (true) {
            Socket sock = server.accept();
            Session thr = new Session(directory, sock);
            threadPool.submit(thr);
            System.out.println("Client connection submitted to threadpool");
        }
    }
}
