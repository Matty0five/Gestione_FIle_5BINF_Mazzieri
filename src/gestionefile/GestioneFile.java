package gestionefile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Matteo Mazzieri
 * @version 19/01/24
 */
public class GestioneFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //1)LETTURA
        Lettore lettore = new Lettore(".\\user.json");
        lettore.start();
        try{
            lettore.join();
        } catch (InterruptedException ex) {
            System.err.println("Errore in lettura" + ex.getMessage());
        }
        //2)ELABORAZIONE

        String username = null;
        String password = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Inserisci l'username: ");
            username = br.readLine().toUpperCase();
            System.out.print("Inserisci la password: ");
                password = br.readLine().toUpperCase();
        } catch (IOException e) {
            System.err.println("Errore durante la lettura dell'input: " + e.getMessage());
        }

        Cifratore cifratore=new Cifratore("VERME");
        
        //3) SCRITTURA
        Scrittore scrittore = new Scrittore(".\\output.csv", username, cifratore.cifra(password));
        Thread threadScrittore = new Thread(scrittore);
        threadScrittore.start();
        try{
            threadScrittore.join();
        } catch (InterruptedException ex) {
            System.err.println("Errore in lettura" + ex.getMessage());
        }
        
        //4) COPIA
        lettore = new Lettore(".\\output.csv");
        String output = lettore.leggi(false);
        String[] output_diviso = output.split(";"); // Dato che sappiamo la struttura del csv, possiamo "scomporlo"...
        String username_copiato = output_diviso[0];
        String password_copiata = output_diviso[1];
        Scrittore copiatore = new Scrittore(".\\copia.csv", username_copiato, password_copiata); // ...e ricomporlo
        
        Thread threadCopiatore = new Thread(copiatore);
        threadCopiatore.start();
        try{
            threadCopiatore.join();
        } catch (InterruptedException ex) {
            System.err.println("Errore in lettura" + ex.getMessage());
        }
    }
    
}
