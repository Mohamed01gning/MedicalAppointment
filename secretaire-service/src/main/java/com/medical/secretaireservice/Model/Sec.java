package com.medical.secretaireservice.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Sec
{
    private String matricule;

    @Pattern(regexp = "^[A-ZÀ-Ÿa-zà-ÿ]+([ '-][A-ZÀ-Ÿa-zà-ÿ]+)*$", message = "Le nom n'est pas valide. Seules les lettres, les espaces, les apostrophes et les traits d’union sont autorisés.",
            groups = ValidationGroup.group4.class)
    @Size(min=2,message = "Ce champ ne doit contenir au moins 02 caracteres", groups = ValidationGroup.group2.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String nom;

    @Pattern(regexp = "^[A-ZÀ-Ÿa-zà-ÿ]+([ '-][A-ZÀ-Ÿa-zà-ÿ]+)*$", message = "Le nom n'est pas valide. Seules les lettres, les espaces, les apostrophes et les traits d’union sont autorisés.",
            groups = ValidationGroup.group4.class)
    @Size(min=2,message = "Ce champ ne doit contenir au moins 02 caracteres", groups = ValidationGroup.group2.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String prenom;

    @Email(message = "Ce mail n'est pas valide",groups = ValidationGroup.group3.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String email;

    @Pattern(regexp = "^(77|76|78)[0-9]{7}$", message = "Le numéro doit contenir 9 chiffres et commencer par 77, 76 ou 78",
            groups = ValidationGroup.group4.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String tel;
    @Pattern(regexp = "^(SEC)$",message = "La valeur dd ce CHAMP NE PEUT ETRE que 'SEC'", groups = ValidationGroup.group4.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String role;
    private String pwd;

    public Sec() {
    }

    public Sec(String matricule, String nom, String prenom, String email, String tel, String role) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.role = role;
    }

    public Sec(String matricule, String nom, String prenom, String email, String tel, String role, String pwd) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.role = role;
        this.pwd = pwd;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
