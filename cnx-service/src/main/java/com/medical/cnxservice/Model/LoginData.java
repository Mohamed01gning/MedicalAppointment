package com.medical.cnxservice.Model;

public class LoginData
{
    private String matricule;
    private String pwd;


    public LoginData() {
    }

    public LoginData(String matricule, String pwd) {
        this.matricule = matricule;
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public LoginData(String pwd) {
        this.matricule = pwd;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}
