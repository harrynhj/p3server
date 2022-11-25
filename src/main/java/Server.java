import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class Server {
    int count = 0;
    ArrayList<TheServer.ClientThread> clients = new ArrayList<TheServer.ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;
    int port;

    Server(Consumer<Serializable> call, int port) {
        callback = call;
        this.port = port;
        server = new TheServer();
        server.start();
    }

    public class TheServer extends Thread {
        public void run() {
            try (ServerSocket mysocket = new ServerSocket(port)) {
                callback.accept("Server Started");
                while (true) {
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("Client#" + count + "connected");
                    clients.add(c);
                    c.start();
                    count++;
                }
            } catch (Exception e) {
                callback.accept("Invalid port number");
            }
        }


        class ClientThread extends Thread {
            Socket connection;
            int count;
            ObjectInputStream in;
            ObjectOutputStream out;

            ClientThread(Socket s, int count) {
            }
        }

    }

}
