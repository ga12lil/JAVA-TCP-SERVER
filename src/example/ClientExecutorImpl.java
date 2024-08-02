package example;

import server.ClientExecutor;
import server.ClientHandler;

import java.util.List;

public class ClientExecutorImpl implements ClientExecutor {
    private List<ClientHandler> clientConnections;
    @Override
    public void execute(long id, String mes) {
        clientConnections.forEach(
                client -> {
                    if (client.getClientId() != id) {
                        client.send(mes);
                    }
                    else {
                        client.send("mes sent!");
                    }
                });
    }

    @Override
    public void setListOfConnections(List<ClientHandler> list) {
        this.clientConnections = list;
    }
}
