
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class Client {

    public static void main(String[] args)  {
        Socket socket;
        PrintWriter out;
        BufferedReader in,clavier;


        try {
            socket=new Socket(InetAddress.getLocalHost(),5000);
            out = new PrintWriter(socket.getOutputStream(),true);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clavier=new BufferedReader(new InputStreamReader(System.in));


            System.out.println(in.readLine()); // message de confirmation de connexion

            Thread envoi= new Thread(()-> {
                while(true) {
                    try {

                        String message = clavier.readLine();
                        out.println(message);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            Thread reception =new Thread(()-> {
                while(true) {
                    try {
                        String message=in.readLine();
                        System.out.println(" reponse du serveur :" + message);

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }



            });

            reception.setPriority(Thread.MAX_PRIORITY);

            reception.start();
            envoi.start();









        }
        catch (Exception e) {
            System.out.println("erreur :"+e.getMessage());
        }


    }
}
