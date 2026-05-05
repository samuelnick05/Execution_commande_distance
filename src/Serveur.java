import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

public class Serveur {


    public  static void main(String[] args) {

        ServerSocket server;
        Socket socket;
        PrintWriter out;
        BufferedReader in;


        try {
            server =new ServerSocket(5000);
        System.out.println("Serveur etabli sur le port 5000");
            while(true){
                socket = server.accept();
                System.out.println("Client connecte :"+socket.getInetAddress().getHostAddress());
                GestionnaireClient client = new GestionnaireClient(socket);
                client.start();

            }
        }
        catch (IOException e) {
            System.out.println("Erreur de communication :"+e.getMessage());

        }

    }
}
