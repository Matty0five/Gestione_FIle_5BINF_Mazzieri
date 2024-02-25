package gestionefile;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

/**
 *
 * @author Matteo Mazzieri
 * @version 19/01/24
 */
public class Scrittore implements Runnable{
    String nomeFile;
    String[] dati_da_scrivere;
    
    public Scrittore(String nomeFile, String[] dati_da_scrivere){
        this.nomeFile = nomeFile;
        this.dati_da_scrivere = dati_da_scrivere;
    }
    
    @Override
    public void run() {
        if(dati_da_scrivere.length == 2)
            scrivi();
        else if(dati_da_scrivere.length == 4)
            scriviDatiUtenteCSV();
    }
    /**
     * Scrive un file di testo usando la classe BufferedWriter
     */
    public void scrivi(){

        try(BufferedWriter br = new BufferedWriter(new FileWriter(nomeFile))){
            //2) scrivo nel buffer
            br.write(dati_da_scrivere[0]);
            br.write(";");
            br.write(dati_da_scrivere[1]);
            br.write("\n\r");
        } catch (IOException ex) {
            Logger.getLogger(Scrittore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void scriviDatiUtenteCSV(){
        String[] struttura = {
            "id",
            "name",
            "surname",
            "role"
        };

        try(DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeFile)))){

            int i = 0;
            String line = "";
            for(String dato : dati_da_scrivere){
                line = struttura[i] + ";" + dato + "\n";
                dos.writeUTF(line);
                i++;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Scrittore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}