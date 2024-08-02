package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private long id;
    private final Socket clientConnection;
    private final ClientExecutor executor;
    private PrintWriter out;
    private BufferedReader in;

    private final TcpServer serv;

    public ClientHandler(Long id, Socket clientConnection, ClientExecutor executor, TcpServer serv) {
        this.clientConnection = clientConnection;
        this.executor = executor;
        this.id = id;
        this.serv = serv;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientConnection.getInputStream()));
            out = new PrintWriter(clientConnection.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null && isRunning()) {
                executor.execute(id, inputLine);
            }
        }
        catch (IOException ex) {
            //ex.printStackTrace();
        }
        if (isRunning()) {
            disconnect();
        }
        System.out.println("Connection closed!");
        serv.removeDeadConnections();
    }

    public synchronized void send(String mes) {
        out.println(mes);
    }

    public boolean isRunning() {
        return id!=-1;
    }

    public long getClientId() {
        return id;
    }

    public void disconnect() {
        try {
            out.close();
            in.close();
            clientConnection.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Client " + id + " disconnected");
        id = -1;
    }
}
