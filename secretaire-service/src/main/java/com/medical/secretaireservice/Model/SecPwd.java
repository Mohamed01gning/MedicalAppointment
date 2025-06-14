package com.medical.secretaireservice.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SecPwd
{

    @Pattern(regexp = "^SEC-\\d{4}-\\d{3}$",message = "La valeur de ce champ doit etre sous la forme de 'MED-1234-567'",
            groups = ValidationGroup.group4.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String matricule;
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String pwd;
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String pwdConf;

    public SecPwd() {
    }

    public SecPwd(String matricule, String pwd, String pwdConf) {
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
