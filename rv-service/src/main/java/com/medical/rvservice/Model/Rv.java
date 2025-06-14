package com.medical.rvservice.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "rv")
public class Rv
{
    @Id
    private String num;
    private String idSec;
    private String idMed;
    private String nomPatient;
    private String prenomPatient;
    private String tel;
    private LocalDate date;
    private LocalTime time;
    private String done;

    public Rv() {

    }

    public Rv(String num, String idSec, String idMedt, String nomPatient, String prenomPatient, String tel, LocalDate date, LocalTime timee, String done) {
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
