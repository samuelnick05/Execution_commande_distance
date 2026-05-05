import java.io.*;
import java.net.Socket;
import java.lang.ProcessBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class GestionnaireClient extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private List<String> commandes =List.of("dir","ipconfig","ping","systeminfo","hostname","whoami","date","time","netstat","tracert","nslookup","arp","tasklist","cd");


    public GestionnaireClient(Socket socket) {
        this.socket = socket;

    }
    public String execute(String command) {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
        pb.redirectErrorStream(true);
        String result="";
        try {
            Process p = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            result = reader.lines().collect(Collectors.joining("\n"));

            p.waitFor();

            return result.isEmpty() ? "Commande exécuté avec succès mais aucune sortie" : result;


        } catch (Exception e) {
            System.out.println("erreur :" + e.getMessage());

        }
        return result;


    }

    public void run() {
        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            out.println("Vous avez vous connecte");// message de confirmation de connexion
            out.println("Entrez une commande (ex :ls, dir, ipconfig, ping");

            String message;

            while ((message = in.readLine()) != null) {

                String premier_mot = message.split(" ")[0];
                if(commandes.contains(premier_mot )) {
                    String reponse = execute(message);
                    out.println(reponse);

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
