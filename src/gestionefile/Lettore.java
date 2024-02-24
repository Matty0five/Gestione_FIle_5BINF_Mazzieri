package gestionefile;

import java.io.FileReader;
import java.io.IOException;
import java.io.EOFException;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 *
 * @author Matteo Mazzieri
 * @version 19/01/24
 */

public class Lettore extends Thread{
    String nomeFile;
    
    public Lettore(String nomeFile){
        this.nomeFile = nomeFile;
    }
    
    /**
     * Legge il file senza tener conto del tipo di file
     * e lo mostra in output
     */
    public String leggiJSON(boolean stampa){
        String contenuto = "";
        int i; 
        try(FileReader fr = new FileReader(nomeFile)){

            //2) leggo carattere per carattere e lo stampo 
            while ((i=fr.read()) != -1)
                contenuto += (char) i;
            
            System.out.print("\n\r");
        } catch (IOException ex) {
            System.err.println("Errore in lettura!");
        }

        if(stampa){
            System.out.println(contenuto);
            return "";
        }else{
            return contenuto;
        }
    }
    
    public String leggiCSV(boolean stampa){
        String contenuto = "";
        
        try(DataInputStream lettore = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeFile)))){

            //2) leggo ogni riga fino alla fine del file
            while (true){
                contenuto += lettore.readUTF();
            }
            
        } catch (EOFException eof) {
            // - Raggiunta la fine del file -
        } catch (IOException ioe) {
            System.err.println("Errore in lettura!");
        }

        if(stampa){
            System.out.println(contenuto);
            return "";
        }else{
            return contenuto;
        }
    }
    

    public void run(){
        leggiJSON(true);
    }
}
