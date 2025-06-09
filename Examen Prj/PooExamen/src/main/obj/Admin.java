package main.obj;

/**
 * Représente un administrateur dans le système.
 * Contient les informations personnelles et d'identification liées à l'administrateur.
 */
public class Admin {
    private int idAdmin;
    private String nom;
    private String prenom;
    private String mail;
    private String password;
    private int idRole;

    /**
     * Constructeur de la classe Admin.
     *
     * @param idAdmin   l'identifiant unique de l'administrateur
     * @param nom       le nom de l'administrateur
     * @param prenom    le prénom de l'administrateur
     * @param mail      l'adresse e-mail de l'administrateur
     * @param password  le mot de passe de l'administrateur
     * @param idRole    l'identifiant du rôle attribué à l'administrateur
     */
    public Admin(int idAdmin, String nom, String prenom, String mail, String password, int idRole) {
        this.idAdmin = idAdmin;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.idRole = idRole;
    }

    /**
     * @return l'identifiant de l'administrateur
     */
    public int getIdAdmin() {
        return idAdmin;
    }

    /**
     * Définit l'identifiant de l'administrateur.
     * @param idAdmin l'identifiant à définir
     */
    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    /**
     * @return le nom de l'administrateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'administrateur.
     * @param nom le nom à définir
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return le prénom de l'administrateur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom de l'administrateur.
     * @param prenom le prénom à définir
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return l'adresse e-mail de l'administrateur
     */
    public String getMail() {
        return mail;
    }

    /**
     * Définit l'adresse e-mail de l'administrateur.
     * @param mail l'adresse e-mail à définir
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return le mot de passe de l'administrateur
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'administrateur.
     * @param password le mot de passe à définir
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return l'identifiant du rôle de l'administrateur
     */
    public int getIdRole() {
        return idRole;
    }

    /**
     * Définit l'identifiant du rôle de l'administrateur.
     * @param idRole l'identifiant de rôle à définir
     */
    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }
}
