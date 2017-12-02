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
public class Data_Table_Barang {
    private String kode_barang, Nama_barang, jenis, jumlah, deskripsi;

    public Data_Table_Barang(String kode_barang, String Nama_barang, String jenis, String jumlah, String deskripsi) {
        this.kode_barang = kode_barang;
        this.Nama_barang = Nama_barang;
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.deskripsi = deskripsi;
    }

    /**
     * @return the kode_barang
     */
    public String getKode_barang() {
        return kode_barang;
    }

    /**
     * @param kode_barang the kode_barang to set
     */
    public void setKode_barang(String kode_barang) {
        this.kode_barang = kode_barang;
    }

    /**
     * @return the Nama_barang
     */
    public String getNama_barang() {
        return Nama_barang;
    }

    /**
     * @param Nama_barang the Nama_barang to set
     */
    public void setNama_barang(String Nama_barang) {
        this.Nama_barang = Nama_barang;
    }

    /**
     * @return the jenis
     */
    public String getJenis() {
        return jenis;
    }

    /**
     * @param jenis the jenis to set
     */
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    /**
     * @return the jumlah
     */
    public String getJumlah() {
        return jumlah;
    }

    /**
     * @param jumlah the jumlah to set
     */
    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    /**
     * @return the deskripsi
     */
    public String getDeskripsi() {
        return deskripsi;
    }

    /**
     * @param deskripsi the deskripsi to set
     */
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
}
