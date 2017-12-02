/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author ChaVaZaSRL
 */
public class Data_Table_Boking {
    private String kode_boking,nama_user,nama_barang,jumlah,tgl_boking,konfirmasi,catatan;

    public Data_Table_Boking(String kode_boking, String nama_user, String nama_barang, String jumlah, String tgl_boking, String konfirmasi, String catatan) {
        this.kode_boking = kode_boking;
        this.nama_user = nama_user;
        this.nama_barang = nama_barang;
        this.jumlah = jumlah;
        this.tgl_boking = tgl_boking;
        this.konfirmasi = konfirmasi;
        this.catatan = catatan;
    }

    /**
     * @return the kode_boking
     */
    public String getKode_boking() {
        return kode_boking;
    }

    /**
     * @param kode_boking the kode_boking to set
     */
    public void setKode_boking(String kode_boking) {
        this.kode_boking = kode_boking;
    }

    /**
     * @return the nama_user
     */
    public String getNama_user() {
        return nama_user;
    }

    /**
     * @param nama_user the nama_user to set
     */
    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    /**
     * @return the nama_barang
     */
    public String getNama_barang() {
        return nama_barang;
    }

    /**
     * @param nama_barang the nama_barang to set
     */
    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
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
     * @return the tgl_boking
     */
    public String getTgl_boking() {
        return tgl_boking;
    }

    /**
     * @param tgl_boking the tgl_boking to set
     */
    public void setTgl_boking(String tgl_boking) {
        this.tgl_boking = tgl_boking;
    }

    /**
     * @return the konfirmasi
     */
    public String getKonfirmasi() {
        return konfirmasi;
    }

    /**
     * @param konfirmasi the konfirmasi to set
     */
    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    /**
     * @return the catatan
     */
    public String getCatatan() {
        return catatan;
    }

    /**
     * @param catatan the catatan to set
     */
    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
