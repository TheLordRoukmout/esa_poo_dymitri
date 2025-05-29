package main.obj;

public class Client {


    private int idClient;
    private String nom;
    private String prenom;
    private int age;
    private String numPermis;
    private String mail;
    private String password;
    private int idRole;

    // Constructeur
    public Client(int idClient, String nom, String prenom, int age, String numPermis, String mail, String password, int idRole) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.numPermis = numPermis;
        this.mail = mail;
        this.password = password;
        this.idRole = idRole;
    }

    // Getters et Setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumPermis() {
        return numPermis;
    }

    public void setNumPermis(String numPermis) {
        this.numPermis = numPermis;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
