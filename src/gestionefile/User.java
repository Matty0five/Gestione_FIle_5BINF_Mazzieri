package gestionefile;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Matteo Mazzieri
 * 
 */

public class User implements Serializable{
    private int id;
    private String name;
    private String surname;
    private String role;

    /**
     * 
     * Crea un utente con i dati forniti.
     * @param id
     * @param name
     * @param surname
     * @param role
     * 
     */

    public User(int id, String name, String surname, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    /**
     * 
     * Crea un utente con i dati all'interno di un file.
     * @param nomeFile Il percorso del file dal quale verranno estrapolati i dati.
     * 
     */

    public User(String nomeFile){
       try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile))){
            User user = (User) ois.readObject();
            this.id = user.id;
            this.name = user.name;
            this.surname = user.surname;
            this.role = user.role;
       } catch (FileNotFoundException ex) {
            System.out.println("File non trovato!");
       } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Errore nella lettura del file: " + ex.getMessage());
        }
    }

    /**
     * 
     * Esporta i dati dell'utente in un file.
     * @param nomeFile Percorso del file sul quale verranno esportati i dati dell'utente.
     * 
     */

    public void esporta(String nomeFile){
        try (ObjectOutputStream scrittore = new ObjectOutputStream(new FileOutputStream(nomeFile))) {
            scrittore.writeObject(this);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Errore in scrittura!");
        }
    }
    
    /**
     * 
     * @return I dati di un'utente sottoforma di oggetto.
     * 
     */

    @Override
    public String toString() {
        return "{" + "id:" + id + ", " + "name:" + name + ", " + "surname:" + surname + ", " + "role:" + role + '}';
    }

}
