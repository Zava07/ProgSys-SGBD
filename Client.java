import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1234)) {
            System.out.println("Connecté au serveur!");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

            String message;
            while (true) {
                System.out.print(">");
                message = clavier.readLine();
                out.println(message);
            
                // Lire la réponse complète jusqu'à "END_RESPONSE"
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while (!(line = in.readLine()).equals("END_RESPONSE")) {
                    responseBuilder.append(line).append("\n");
                }
            
                // Afficher la réponse complète
                System.out.println(responseBuilder.toString());
            
                // Quitter si nécessaire
                if (message.equalsIgnoreCase("quit")) break;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
