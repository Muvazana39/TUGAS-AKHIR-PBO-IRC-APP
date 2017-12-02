/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HalamanAdmin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ChaVaZaSRL
 */
public class User_admin_ListController implements Initializable {

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnListBarang;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private TableView<Data.Data_Table_Barang> TableList;
    private ObservableList<Data.Data_Table_Barang> data = FXCollections.observableArrayList();
    
    @FXML
    private TableColumn<?, ?> tbIdBarangList;
    
    @FXML
    private TableColumn<?, ?> tbNamaList;
    
    @FXML
    private TableColumn<?, ?> tbJenisList;
    
    @FXML
    private TableColumn<?, ?> tbJumlahList;
    
    @FXML
    private TableColumn<?, ?> tbDeskList;
    @FXML
    private JFXButton btnHapusAllList;
    
    @FXML
    private JFXComboBox VJenisList;
    ObservableList<String> jenis = FXCollections.observableArrayList("Obat Luar","Obat Dalam");
    
    @FXML
    private JFXTextField VNamaList;
    
    @FXML
    private JFXTextField VJumlahList;
    
    @FXML
    private JFXTextField VDeskirpsiList;
    
    @FXML
    private Label VIdList;
    
    @FXML
    private JFXButton btnHapusDataList;
    
    @FXML
    private JFXButton btnUpdateList;
    
    @FXML
    private JFXButton btnSimpanList;
    
    @FXML
    private JFXComboBox CariList;
    ObservableList<String> cari = FXCollections.observableArrayList("ALL","Obat Luar","Obat Dalam");
    
    @FXML
    private JFXButton btnCariList;
    
    @FXML
    private Label LabelUser;
    
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    @FXML
    private JFXButton btnExitList;
    @FXML
    private JFXButton btnHapusFList;
    
    private String namab,jenisb = null,desk,carij,jenisdb = null,namauserm;
    private int kodeb,jumlah,kodeuserm;
    @FXML
    private JFXButton btnDataBok;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = Control.C_Connection.Koneksi();
        VJenisList.setItems(jenis);
        CariList.setItems(cari);
        CariList.setValue("ALL");
        
        setKolomTable();
        AmbilDataBase();
        setCellValueFromTableToTextField();
        TableList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
    public void dataUser(String namauser,int kodeuser){
        namauserm = namauser;
        kodeuserm = kodeuser;
        LabelUser.setText(namauserm);
        
    }
    
    private void setCellValueFromTableToTextField(){
        TableList.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Data.Data_Table_Barang pl= (TableList.getItems().get(TableList.getSelectionModel().getSelectedIndex()));
                VIdList.setText(pl.getKode_barang());
                VNamaList.setText(pl.getNama_barang());
                VJenisList.setValue(pl.getJenis());
                VJumlahList.setText(pl.getJumlah());
                VDeskirpsiList.setText(pl.getDeskripsi());
            }
            
        });
    }

    private void setKolomTable(){
        tbIdBarangList.setCellValueFactory(new PropertyValueFactory<>("kode_barang"));
        tbNamaList.setCellValueFactory(new PropertyValueFactory<>("Nama_barang"));
        tbJenisList.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        tbJumlahList.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        tbDeskList.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
    }
    
    private void AmbilDataBase(){
        data.clear();
        String sql = "SELECT * FROM db_barang";
        try{
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                if(rs.getString(3).equalsIgnoreCase("Obat_Dalam")){
                    jenisdb = "Obat Dalam";
                }else if(rs.getString(3).equalsIgnoreCase("Obat_Luar")){
                    jenisdb = "Obat Luar";
                }
                data.add(new Data.Data_Table_Barang(rs.getString(1), rs.getString(2), jenisdb, rs.getString(4), rs.getString(5)));
            }
        }catch (SQLException ex) {
            Logger.getLogger(User_admin_HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TableList.setItems(data);
    }
    
    @FXML
    private void btnHomeProses(ActionEvent event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("User_admin_Home.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            
            HalamanAdmin.User_admin_HomeController Data = new HalamanAdmin.User_admin_HomeController();
            Data = fxmlLoader.getController();
            
            Data.dataUser(namauserm,kodeuserm);
                    
            
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            System.out.println("Failed to create new Window." + e);
        }
    }

    @FXML
    private void btnListBarangProses(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Anda Sudah di Pengaturan Barang");
    }

    @FXML
    private void btnLogoutProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ingin LogOut ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            try {
                ((Node)(event.getSource())).getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/View/FXMLMain.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                System.out.println("Failed to create new Window." + e);
            }
        }
    }

    @FXML
    private void btnHapusAllListProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Konfirmasi untuk Menghapus Semua Barang ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            String sql = "DELETE FROM db_barang";

            try{
                stmt = conn.prepareStatement(sql);

                int i = stmt.executeUpdate();
                if(i >= 1){
                    JOptionPane.showMessageDialog(null, "Delete Akun Member Succesfully");
                    AmbilDataBase();
                    VIdList.setText("");
                    VNamaList.setText("");
                    VJenisList.setValue("");
                    VJumlahList.setText("");
                    VDeskirpsiList.setText("");
                }else{
                    JOptionPane.showMessageDialog(null, "Tidak ada Data yang akan Dihapus");
                }
                stmt.close();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    @FXML
    private void btnHapusDataListProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Konfirmasi Untuk Menghapus Barang ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            kodeb = Integer.parseInt(VIdList.getText());
            String sql = "DELETE FROM `db_barang` WHERE code_barang = ?";
            try{
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, kodeb);

                int i = stmt.executeUpdate();
                if(i == 1){
                    JOptionPane.showMessageDialog(null, "Delete Akun Succesfully");
                    AmbilDataBase();
                    VIdList.setText("");
                    VNamaList.setText("");
                    VJenisList.setValue("");
                    VJumlahList.setText("");
                    VDeskirpsiList.setText("");
                }else{
                    JOptionPane.showMessageDialog(null, "Delete Data Error");
                }
                stmt.close();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    @FXML
    private void btnUpdateListProses(ActionEvent event) {
        kodeb = Integer.parseInt(VIdList.getText());
        namab = VNamaList.getText();
        jenisb = VJenisList.getValue().toString();
        jumlah = Integer.parseInt(VJumlahList.getText());
        desk = VDeskirpsiList.getText();
        if(jenisb == "Obat Luar"){
            jenisdb = "Obat_Luar";
        }else if(jenisb == "Obat Dalam"){
            jenisdb = "Obat_Dalam";
        }
        
        String sql = "UPDATE `db_barang` SET `nama_barang`= ?,`jenis_barang`= ?,`jumlah`= ?,`deskripsi_barang`= ? WHERE code_barang = ?";
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, namab);
            stmt.setString(2, jenisdb);
            stmt.setInt(3, jumlah);
            stmt.setString(4, desk);
            stmt.setInt(5, kodeb);
             
            int i = stmt.executeUpdate();
            if(i == 1){
                JOptionPane.showMessageDialog(null, "Update Barang Succesfully");
            }else{
                JOptionPane.showMessageDialog(null, "Update Barang Error");
            }
            stmt.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        AmbilDataBase();
    }

    @FXML
    private void btnSimpanListProses(ActionEvent event) {
        namab = VNamaList.getText();
        jenisb = VJenisList.getValue().toString();
        jumlah = Integer.parseInt(VJumlahList.getText());
        desk = VDeskirpsiList.getText();
        String jenisdb = null;
        if(jenisb == "Obat Dalam"){
            jenisdb = "Obat_Dalam";
        }else if(jenisb == "Obat Luar"){
            jenisdb = "Obat_Luar";
        }
        
        String sql = "INSERT INTO `db_barang`(`nama_barang`, `jenis_barang`, `jumlah`, `deskripsi_barang`) VALUES (?,?,?,?)";
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, namab);
            stmt.setString(2, jenisdb);
            stmt.setInt(3, jumlah);
            stmt.setString(4, desk);

            int i = stmt.executeUpdate(); 
            if (i == 1) {
                JOptionPane.showMessageDialog(null, "Insert Successfully");
            }else {
                JOptionPane.showMessageDialog(null, "data Can't be Write");
            }
            stmt.close();
        }catch(SQLException se){
            JOptionPane.showMessageDialog(null, se);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        AmbilDataBase();
    }

    @FXML
    private void btnCariListProses(ActionEvent event) {
        carij = CariList.getValue().toString();
        String sql1 = "SELECT * FROM db_barang WHERE jenis_barang = 'Obat_Luar'";
        String sql2 = "SELECT * FROM db_barang WHERE jenis_barang = 'Obat_Dalam'";
        if(carij == "ALL"){
            AmbilDataBase();
        }else if(carij == "Obat Luar"){
            data.clear();
            String namaj = null;
            try{
                stmt = conn.prepareStatement(sql1);
                rs = stmt.executeQuery();
                while(rs.next()){
                    if(rs.getString(3).equalsIgnoreCase("Obat_Dalam")){
                        jenisdb = "Obat Dalam";
                    }else if(rs.getString(3).equalsIgnoreCase("Obat_Luar")){
                        jenisdb = "Obat Luar";
                    }
                    data.add(new Data.Data_Table_Barang(rs.getString(1), rs.getString(2), jenisdb, rs.getString(4), rs.getString(5)));
                }
            }catch (SQLException ex) {
                Logger.getLogger(User_admin_HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            TableList.setItems(data);
        }else if(carij == "Obat Dalam"){
            data.clear();
            String namaj = null;
            try{
                stmt = conn.prepareStatement(sql2);
                rs = stmt.executeQuery();
                while(rs.next()){
                    if(rs.getString(3).equalsIgnoreCase("Obat_Dalam")){
                        jenisdb = "Obat Dalam";
                    }else if(rs.getString(3).equalsIgnoreCase("Obat_Luar")){
                        jenisdb = "Obat Luar";
                    }
                    data.add(new Data.Data_Table_Barang(rs.getString(1), rs.getString(2), jenisdb, rs.getString(4), rs.getString(5)));
                }
            }catch (SQLException ex) {
                Logger.getLogger(User_admin_HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            TableList.setItems(data);
        }else{
            JOptionPane.showMessageDialog(null, "Harap Pilih Jabatan yang ingin anda Cari");
        }
    }

    @FXML
    private void btnExitListProses(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Ingin Keluar ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            Stage stage = (Stage) btnExitList.getScene().getWindow();
            stage.close();
        }
        
    }

    @FXML
    private void btnHapusFListProses(ActionEvent event) {
        VIdList.setText("");
        VNamaList.setText("");
        VJenisList.setValue("");
        VJumlahList.setText("");
        VDeskirpsiList.setText("");
    }

    @FXML
    private void btnListDataProses(ActionEvent event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("User_admin_Boking.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            
            HalamanAdmin.User_admin_BokingController Data = new HalamanAdmin.User_admin_BokingController();
            Data = fxmlLoader.getController();
            
            Data.dataUser(namauserm,kodeuserm);
                    
            
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            System.out.println("Failed to create new Window." + e);
        }
    }
    
}
