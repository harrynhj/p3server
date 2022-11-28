import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
                dataPack.systemMessages = "Server Started";
                callback.accept(dataPack);
                while (true) {
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    dataPack.systemMessages = "Client# " + count + " connected";
                    callback.accept(dataPack);
                    clients.add(c);
                    c.start();
                    count++;
                }
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    dataPack.systemMessages = "More than two";
                    callback.accept(dataPack);
                } else {
                    dataPack.systemMessages = "Invalid port number";
                    callback.accept(dataPack);
                }

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
                for (ClientThread t : clients) {
                    try {
                        t.out.writeObject(dataPack);
                    } catch (Exception e) {
                        System.out.println("Error while sending message");
                    }
                }
            }

            public void sendInitializePackage(CFourInfo dataPack) {
                try {
                    this.out.writeObject(dataPack);
                }
                catch(Exception e) {
                    System.out.println("Error while sending message");
                }

            }

            public void run() {
                try {
                    in = new ObjectInputStream(connection.getInputStream());
                    out = new ObjectOutputStream(connection.getOutputStream());
                    connection.setTcpNoDelay(true);
                }catch (Exception e) {
                    System.out.println("Streams not open");
                }

                CFourInfo dataPack = new CFourInfo();
                dataPack.onlinePlayers = clients.size();
                if (clients.size() == 2) {
                    dataPack.have2players = true;
                    dataPack.systemMessages = "Game Started";
                    callback.accept(dataPack);
                    updateClients(dataPack);
                }
                sendInitializePackage(dataPack);
                while(true) {
                    try {
                        CFourInfo playerData = (CFourInfo) in.readObject();
                        updateClients(new CFourInfo());

                    } catch(Exception e) {
                        dataPack.systemMessages = "Client# " + count + " disconnected";
                        callback.accept(dataPack);
                        clients.remove(this);
                        updateClients(new CFourInfo());
                        break;
                    }
                }
            }
        }

    }

}
