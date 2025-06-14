package com.medical.rvservice.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public class RvDTO
{

    private String num;
    @Pattern(regexp = "^SEC-\\d{4}-\\d{3}$",message = "La valeur de ce champ doit etre sous la forme de 'SEC-1234-567'",
            groups = ValidationGroup.group4.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String idSec;
    @Pattern(regexp = "^MED-\\d{4}-\\d{3}$",message = "La valeur de ce champ doit etre sous la forme de 'MED-1234-567'",
            groups = ValidationGroup.group4.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String idMed;
    @Pattern(regexp = "^[A-ZÀ-Ÿa-zà-ÿ]+([ '-][A-ZÀ-Ÿa-zà-ÿ]+)*$", message = "Le prenom n'est pas valide. Seules les lettres, les espaces, les apostrophes et les traits d’union sont autorisés.",
            groups = ValidationGroup.group4.class)
    @Size(min=2,message = "Ce champ ne doit contenir au moins 02 caracteres", groups = ValidationGroup.group2.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String nomPatient;
    @Pattern(regexp = "^[A-ZÀ-Ÿa-zà-ÿ]+([ '-][A-ZÀ-Ÿa-zà-ÿ]+)*$", message = "Le prenom n'est pas valide. Seules les lettres, les espaces, les apostrophes et les traits d’union sont autorisés.",
            groups = ValidationGroup.group4.class)
    @Size(min=2,message = "Ce champ ne doit contenir au moins 02 caracteres", groups = ValidationGroup.group2.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String prenomPatient;
    @Pattern(regexp = "^(77|76|78)[0-9]{7}$", message = "Le numéro doit contenir 9 chiffres et commencer par 77, 76 ou 78",
            groups = ValidationGroup.group4.class)
    @NotBlank(message = "*",groups = ValidationGroup.group1.class)
    private String tel;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String done;

    public RvDTO() {

    }

    public RvDTO(String num, String idSec, String idMedt, String nomPatient, String prenomPatient, String tel, LocalDate date, LocalTime timee, String done) {
        this.num = num;
        this.idSec = idSec;
        this.idMed = idMedt;
        this.nomPatient = nomPatient;
        this.prenomPatient = prenomPatient;
        this.tel = tel;
        this.date = date;
        this.time = timee;
        this.done = done;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIdSec() {
        return idSec;
    }

    public void setIdSec(String idSec) {
        this.idSec = idSec;
    }

    public String getIdMed() {
        return idMed;
    }

    public void setIdMed(String idMed) {
        this.idMed = idMed;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getPrenomPatient() {
        return prenomPatient;
    }

    public void setPrenomPatient(String prenomPatient) {
        this.prenomPatient = prenomPatient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }
}
