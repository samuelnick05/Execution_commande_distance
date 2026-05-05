import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder;
import java.time.LocalDateTime;
import java.util.List;

public class GestionnaireClient extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private List<String> commandes =List.of("dir","ipconfig","ping","systeminfo","hostname","whoami","date","time","netstat","tracert","nslookup","arp","tasklist","cd");


    public GestionnaireClient(Socket socket) {
        this.socket = socket;

    }
    public void execute(String command) {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
        pb.redirectErrorStream(true);

        try {
            Process p = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), "CP850"));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                out.println(ligne);
            }
            p.waitFor();
            out.println("--FIN--");





        } catch (Exception e) {
            System.out.println("erreur :" + e.getMessage());

        }



    }

    public void run() {
        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            out.println("Vous avez vous connecte"); // message de confirmation de connexion
            out.println("Entrez une commande (ex :ls, dir, ipconfig, ping");

            String message;

            while ((message = in.readLine()) != null) {

                String log = LocalDateTime.now() + " | " +
                        socket.getInetAddress() + " | " + message;
                System.out.println(log);

                String premier_mot = message.split(" ")[0];
                if(commandes.contains(premier_mot )) {
                   execute(message);


                }
                else {out.println("Commande non autorisée:"+premier_mot);
                }





            }




        }

        catch (Exception e){
            System.out.println("erreur :"+e.getMessage());
        }
        finally {
            try {
                socket.close();
            }
            catch (Exception e) {
                System.out.println("erreur :"+e.getMessage());
            }
        }



    }






}
