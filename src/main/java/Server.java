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
            CFourInfo dataPack = new CFourInfo();
            try (ServerSocket mysocket = new ServerSocket(port)) {
                dataPack.errorMessages = "Server Started";
                callback.accept(dataPack);
                while (true) {
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("Client# " + count + " connected");
                    clients.add(c);
                    c.start();
                    count++;
                }
            } catch (Exception e) {
                dataPack.errorMessages = "Invalid port number";
                callback.accept(dataPack);
            }
        }


        class ClientThread extends Thread {
            Socket connection;
            int count;
            ObjectInputStream in;
            ObjectOutputStream out;

            ClientThread(Socket s, int count) {
                connection = s;
                this.count = count;
            }

            public void updateClients(CFourInfo dataPack) {

            }

            public void run() {
                try {
                    in = new ObjectInputStream(connection.getInputStream());
                    out = new ObjectOutputStream(connection.getOutputStream());
                    connection.setTcpNoDelay(true);
                }catch (Exception e) {
                    System.out.println("Streams not open");
                }

                while(true) {
                    try {
                        String data = in.readObject().toString();
                        callback.accept("client: " + count + " sent: " + data);
                        updateClients(new CFourInfo());

                    } catch(Exception e) {
                        callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                        updateClients(new CFourInfo());
                        clients.remove(this);
                        break;
                    }
                }


            }
        }

    }

}
