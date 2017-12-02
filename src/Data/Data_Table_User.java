/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ChaVaZaSRL
 */
public class Data_Table_User {
    private String kode_user, Nama_user, umur_user, gender_user, username_user, password_user, jabatan_user;

    public Data_Table_User(String kode_user, String Nama_user, String umur_user, String gender_user, String username_user, String password_user, String jabatan_user) {
        this.kode_user = kode_user;
        this.Nama_user = Nama_user;
        this.umur_user = umur_user;
        this.gender_user = gender_user;
        this.username_user = username_user;
        this.password_user = password_user;
        this.jabatan_user = jabatan_user;
    }

    /**
     * @return the kode_user
     */
    public String getKode_user() {
        return kode_user;
    }

    /**
     * @param kode_user the kode_user to set
     */
    public void setKode_user(String kode_user) {
        this.kode_user = kode_user;
    }

    /**
     * @return the Nama_user
     */
    public String getNama_user() {
        return Nama_user;
    }

    /**
     * @param Nama_user the Nama_user to set
     */
    public void setNama_user(String Nama_user) {
        this.Nama_user = Nama_user;
    }

    /**
     * @return the umur_user
     */
    public String getUmur_user() {
        return umur_user;
    }

    /**
     * @param umur_user the umur_user to set
     */
    public void setUmur_user(String umur_user) {
        this.umur_user = umur_user;
    }

    /**
     * @return the gender_user
     */
    public String getGender_user() {
        return gender_user;
    }

    /**
     * @param gender_user the gender_user to set
     */
    public void setGender_user(String gender_user) {
        this.gender_user = gender_user;
    }

    /**
     * @return the username_user
     */
    public String getUsername_user() {
        return username_user;
    }

    /**
     * @param username_user the username_user to set
     */
    public void setUsername_user(String username_user) {
        this.username_user = username_user;
    }

    /**
     * @return the password_user
     */
    public String getPassword_user() {
        return password_user;
    }

    /**
     * @param password_user the password_user to set
     */
    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    /**
     * @return the jabatan_user
     */
    public String getJabatan_user() {
        return jabatan_user;
    }

    /**
     * @param jabatan_user the jabatan_user to set
     */
    public void setJabatan_user(String jabatan_user) {
        this.jabatan_user = jabatan_user;
    }


    
}
