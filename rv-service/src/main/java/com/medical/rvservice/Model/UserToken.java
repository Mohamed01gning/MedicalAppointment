package com.medical.rvservice.Model;

public class UserToken
{

    private String matricule;
    private String pwd;
    private String role;

    public UserToken() {
    }

    public UserToken(String matricule, String pwd, String role) {
        this.matricule = matricule;
        this.pwd = pwd;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
