package gestionefile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Matteo Mazzieri
 * 
 */

public class GestioneFile {

    /**
     * 
     * Programma suddiviso in fasi in base alle ISSUE.
     * @param args the command line arguments
     * 
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

        Cifratore cifratore = new Cifratore("VERME");
        
        //3) SCRITTURA

        String[] dati_di_accesso = new String[2];
        dati_di_accesso[0] = username;
        dati_di_accesso[1] = cifratore.cifra(password);
        Scrittore scrittore = new Scrittore(".\\output.csv", dati_di_accesso);
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
        Scrittore copiatore = new Scrittore(".\\copia.csv", output_diviso); // ...e ricomporlo
        
        Thread threadCopiatore = new Thread(copiatore);
        threadCopiatore.start();
        try{
            threadCopiatore.join();
        } catch (InterruptedException ex) {
            System.err.println("Errore in lettura" + ex.getMessage());
        }

        //5) SCRITTURA DI USER.CSV
        
        String[] dati_utente = new String[4];
        
        try {
            System.out.print("Inserisci l'id: ");
            dati_utente[0] = br.readLine();
            System.out.print("Inserisci il tuo nome: ");
            dati_utente[1] = br.readLine();
            System.out.print("Inserisci il tuo cognome: ");
            dati_utente[2] = br.readLine();
            System.out.print("Inserisci il tuo ruolo: ");
            dati_utente[3] = br.readLine();
        } catch (IOException e) {
            System.err.println("Errore durante la lettura dell'input: " + e.getMessage());
        }

        System.out.println("");
        
        Scrittore scrittore_utente = new Scrittore(".\\user.csv", dati_utente);
        Thread threadScrittoreDatiUtente = new Thread(scrittore_utente);
        threadScrittoreDatiUtente.start();
        try{
            threadScrittoreDatiUtente.join();
        } catch (InterruptedException ex) {
            System.err.println("Errore in lettura" + ex.getMessage());
        }

        //6) LEGGO I DATI DI USER.CSV
        
        System.out.println("Dati letti da user.csv:\n");

        lettore = new Lettore(".\\user.csv");
        lettore.setType(true); // setto la lettura alla lettura binaria piuttosto che quella per carattere
        lettore.start();
        try{
            lettore.join();
        } catch (InterruptedException ex) {
            System.err.println("Errore in lettura" + ex.getMessage());
        }

        //7) CREO L'OGGETTO USER E LO ESPORTO

        User utente1 = new User(Integer.parseInt(dati_utente[0]), dati_utente[1], dati_utente[2], dati_utente[3]);
        utente1.esporta(".\\user.txt");
        System.out.println("Dati dell'utente esportati");

        //8) IMPORTO L'UTENTE

        User utente2 = new User(".\\user.txt");
        System.out.println("Dati dell'utente importati: ");
        System.out.print(utente2.toString());
    }
    
}
