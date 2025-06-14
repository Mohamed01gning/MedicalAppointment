package com.medical.cnxservice.Model;

public class User
{
    private String matricule;
    private String pwd;

    public User() {
    }

    public User(String matricule, String pwd) {
        this.matricule = matricule;
        this.pwd = pwd;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
