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
 * @version 19/01/24
 */

public class Lettore extends Thread{
    String nomeFile;
    boolean binario = false;
    
    public Lettore(String nomeFile){
        this.nomeFile = nomeFile;
    }
    
    /**
     * Legge il file senza tener conto del tipo di file
     * e lo mostra in output
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
    

    public void run(){
        if(binario)
            leggiUtente(true);
        else
            leggi(true);
    }

    public void setType(boolean type){
        this.binario = type;
    }
}