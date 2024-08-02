package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class TcpServer extends Thread {
    private boolean isWork = true;
    private final ServerSocket serverSocket;
    private final List<ClientHandler> clientConnections = new LinkedList<>();
    private final ClientExecutor executor;
    public TcpServer (int port, ClientExecutor executor) throws IOException {
        serverSocket = new ServerSocket(port);
        this.executor = executor;
        executor.setListOfConnections(clientConnections);
    }

    @Override
    public void run() {
        long idCounter = 0;
        while (isWork) {
            try {
                Socket newConnection = serverSocket.accept();
                ClientHandler newClientHandler = new ClientHandler(idCounter, newConnection, executor, this);
                synchronized (clientConnections) {
                    clientConnections.add(newClientHandler);
                }
                idCounter += 1;
                newClientHandler.start();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            serverSocket.close();
            for (var client : clientConnections) {
                client.disconnect();
                client.join();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Server stopped!");
    }


    public void removeDeadConnections() {
        synchronized (clientConnections) {
            clientConnections.removeIf(connection -> !connection.isRunning());
        }
    }

    public void shutdown() {
        isWork = false;
    }
}
