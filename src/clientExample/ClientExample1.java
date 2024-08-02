package clientExample;

import java.io.IOException;
import java.util.Scanner;

public class ClientExample1 {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 8181);
        client.start();

        Scanner scanner = new Scanner(System.in);
        while(true) {
            String line = scanner.nextLine();
            client.sendMessage(line);
        }
    }
}
