package gestionefile;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

/**
 *
 * @author Matteo Mazzieri
 * @version 19/01/24
 */

public class Scrittore implements Runnable{

    String nomeFile;
    String username;
    String password;
    
    public Scrittore(String nomeFile, String username, String password){
        this.nomeFile = nomeFile;
        this.username = username;
        this.password = password;
    }
    
    @Override
    public void run() {
        scrivi();
    }
    /**
     * Scrive un file di testo usando la classe BufferedWriter
     */
    public void scrivi(){
        
        try(DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeFile)))){

            //2) scrivo nel buffer
            dos.writeUTF(username);
            dos.writeUTF(";");
            dos.writeUTF(password);
            
        } catch (IOException ex) {
            Logger.getLogger(Scrittore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
