import java.io.Serializable;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server {
    int count = 1;
    ArrayList<TheServer.ClientThread> clients = new ArrayList<TheServer.ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;
    int port;

    Server(Consumer<Serializable> call, int port) {
        callback = call;
        server = new TheServer();
        server.start();
        this.port = port;
    }

    public class TheServer extends Thread {
        public void run() {
            try (ServerSocket mysocket = new ServerSocket(port)) {

            } catch (Exception e) {
                callback.accept("Invalid port number");
            }
        }


        class ClientThread extends Thread {

        }

    }

}
