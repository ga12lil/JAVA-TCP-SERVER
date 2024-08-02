package example;

import server.ClientExecutor;
import server.TcpServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ClientExecutor executor = new ClientExecutorImpl();
        TcpServer server;
        try {
            server = new TcpServer(8181, executor);
            server.start();
        }
        catch (IOException ex) {
            System.out.println("Cannot start the server!");
        }

    }
}