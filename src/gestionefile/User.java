package gestionefile;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class User implements Serializable{
    private int id;
    private String name;
    private String surname;
    private String role;

    public User(int id, String name, String surname, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

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

    public void esporta(String nomeFile){
        try (ObjectOutputStream scrittore = new ObjectOutputStream(new FileOutputStream(nomeFile))) {
            scrittore.writeObject(this);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.err.println("Errore in scrittura!");
        }
    }

    @Override
    public String toString() {
        return "{" + "id:" + id + ", " + "name:" + name + ", " + "surname:" + surname + ", " + "role:" + role + '}';
    }

}
