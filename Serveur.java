import java.io.*;
import java.net.*;

public class Serveur {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Serveur en attente de connexion...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connecté!");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message reçu: " + message);
            
                RequestReader reader = new RequestReader(message);
                try {
                    // Générer la réponse complète
                    String response = reader.translate();
            
                    // Envoyer la réponse complète au client avec un marqueur de fin
                    out.println(response);
                    out.println("END_RESPONSE");
                } catch (Exception error) {
                    System.out.println("Erreur : " + error.getMessage());
                    out.println("Erreur : " + error.getMessage());
                    out.println("END_RESPONSE");
                }
            }
            

            System.out.println("Client déconnecté.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
