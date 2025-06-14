package com.medical.adminservice.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AdminPwd
{
    @Pattern(regexp = "^ADMIN-\\d{4}-\\d{3}$",message = "La matricule doit etre au format 'ADMIN-1234-567'",
            groups = ValidationGroup.group4.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String matricule;
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String pwd;
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String pwdConf;

    public AdminPwd() {
    }

    public AdminPwd(String matricule, String pwd, String pwdConf) {
        this.matricule = matricule;
        this.pwd = pwd;
        this.pwdConf = pwdConf;
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

    public String getPwdConf() {
        return pwdConf;
    }

    public void setPwdConf(String pwdConf) {
        this.pwdConf = pwdConf;
    }
}
