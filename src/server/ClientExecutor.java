package server;

import java.util.List;

public interface ClientExecutor {
    void execute(long id, String mes);
    void setListOfConnections(List<ClientHandler> list);
}
