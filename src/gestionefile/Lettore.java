package gestionefile;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;

/**
 *
 * @author Matteo Mazzieri
 * 
 */

public class Lettore extends Thread{
    String nomeFile;
    boolean binario = false;

    /**
     * 
     * Crea un oggetto lettore.
     * @param nomeFile Percorso del file da leggere.
     * 
     */
    
    public Lettore(String nomeFile){
        this.nomeFile = nomeFile;
    }
    
    /**
     * 
     * Legge un file a prescindere dal tipo.
     * @param stampa Determina se l'output della lettura del file deve essere mostrato su console.
     * @return Il contenuto del file.
     * 
     */

    public String leggi(boolean stampa){
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
    
    /**
     * 
     * Legge i dati dell'utente da un file scritto con DataOutputStream.
     * @param stampa Determina se l'output della lettura del file deve essere mostrato su console.
     * @return I dati dell'utente contenuti nel file.
     * 
     */
    
    public String leggiUtente(boolean stampa){

        try(DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeFile)))){

            String[] line;
            while(true){
                line = dis.readUTF().split(";");
                System.out.print(line[0]+": "+line[1]);
            }

        } catch (EOFException eof){
            // Raggiunta la fine del file //
        } catch (IOException ioe){
            System.err.println("Errore in lettura: " + ioe.getMessage());
        }
        return "";
    }

    /**
     * 
     * Imposta il tipo di lettura che deve essere effettuata.
     * @param type Definisce il tipo di lettura.
     * 
     */

    public void setType(boolean type){
        this.binario = type;
    }
    
    @Override
    public void run(){
        if(binario)
            leggiUtente(true);
        else
            leggi(true);
    }

}