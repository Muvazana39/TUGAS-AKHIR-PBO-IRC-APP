/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HalamanMember;

import HalamanAdmin.User_admin_HomeController;
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
public class User_member_AkunController implements Initializable {

    @FXML
    private TableView<Data.Data_Table_User> TableUser;
    private ObservableList<Data.Data_Table_User> data = FXCollections.observableArrayList();
    
    @FXML
    private TableColumn<?, ?> tbIdUserAkun;
    @FXML
    private TableColumn<?, ?> tbNamaAkun;
    @FXML
    private TableColumn<?, ?> tbUmurAkun;
    @FXML
    private TableColumn<?, ?> tbGenderAkun;
    @FXML
    private TableColumn<?, ?> tbJabatanAkun;
        
    
    @FXML
    private Label LabelUser;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnLogout;
    
    @FXML
    private JFXButton btnExitAkun;
    @FXML
    private JFXComboBox VGenderAkun;
    ObservableList<String> vgenderhome = FXCollections.observableArrayList("Laki-laki", "Perempuan");
    
    @FXML
    private JFXTextField VNamaAkun;
    @FXML
    private JFXTextField VUmurAkun;
    @FXML
    private JFXTextField VUsernameAkun;
    @FXML
    private JFXPasswordField VPasswordAkun;
    @FXML
    private Label VIdUserAkun;
    @FXML
    private JFXTextField VJabatanAkun;
    @FXML
    private JFXButton btnHapusDataAkun;
    
    @FXML
    private JFXButton btnUpdateAkun;
    @FXML
    private JFXComboBox CariAkun;
    ObservableList<String> Cari = FXCollections.observableArrayList("All","Admin", "Member");
    
    @FXML
    private JFXButton btnCariAkun;
    @FXML
    private JFXButton btnAkun;
    
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement stmt2 = null;
    private ResultSet rs2 = null;
    
    String jabatan, nama, gender, username, pass, jab = null, namauserm;
    int kode, umur,kodeuserm;
    @FXML
    private JFXButton btnDataBok;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = Control.C_Connection.Koneksi();
        VGenderAkun.setItems(vgenderhome);
        CariAkun.setItems(Cari);
        CariAkun.setValue("All");
        
        setKolomTable();
        AmbilDataDatabase();
        
        TableUser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    

    public void dataUser(String namauser,int kodeuser){
        namauserm = namauser;
        kodeuserm = kodeuser;
        LabelUser.setText(namauserm);
        VIdUserAkun.setText(String.valueOf(kodeuserm));
        String sql = "SELECT * FROM db_user";
        String namaj = null;
        String gen = null;
        
        try{
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) == kodeuserm){
                    if(rs.getInt(2) == 2){
                        namaj = "Member";
                    }else{
                        namaj = "Admin";
                    }
                    if(rs.getString(5).equalsIgnoreCase("L")){
                        gen = "Laki-laki";
                    }else if(rs.getString(5).equalsIgnoreCase("P")){
                        gen = "Perempuan";
                    }
                    VIdUserAkun.setText(rs.getString(1));
                    VNamaAkun.setText(rs.getString(3));
                    VUmurAkun.setText(rs.getString(4));
                    VJabatanAkun.setText(namaj);
                    VGenderAkun.setValue(gen);
                    VUsernameAkun.setText(rs.getString(6));
                    VPasswordAkun.setText(rs.getString(7));
                    namauserm = rs.getString(3);
                }else{
                    //JOptionPane.showMessageDialog(null, kodeuserm);
                }
                
                
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    
    private void setKolomTable(){
        tbIdUserAkun.setCellValueFactory(new PropertyValueFactory<>("kode_user"));
        tbNamaAkun.setCellValueFactory(new PropertyValueFactory<>("Nama_user"));
        tbUmurAkun.setCellValueFactory(new PropertyValueFactory<>("umur_user"));
        tbGenderAkun.setCellValueFactory(new PropertyValueFactory<>("gender_user"));
        tbJabatanAkun.setCellValueFactory(new PropertyValueFactory<>("jabatan_user"));
    }
 
    private void AmbilDataDatabase(){
        data.clear();
        String sql = "SELECT * FROM db_user";
        
        String namaj = null;
        try{
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            //stmt2 = conn.prepareStatement(sql2);
            //rs2 = stmt2.executeQuery();
            while(rs.next()){
                if(rs.getInt(2) == 2){
                    namaj = "Member";
                }else{
                    namaj = "Admin";
                }
                data.add(new Data.Data_Table_User(""+rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), ""+namaj));
            }
        }catch (SQLException ex) {
            Logger.getLogger(User_admin_HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TableUser.setItems(data);
    }

    @FXML
    private void btnHomeProses(ActionEvent event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("User_member_Home.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            
            HalamanMember.User_member_HomeController Data = new HalamanMember.User_member_HomeController();
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
    private void btnExitAkunProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ingin Keluar ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            Stage stage = (Stage) btnExitAkun.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void btnHapusDataAkunProses(ActionEvent event) {
        VNamaAkun.setText("");
        VUmurAkun.setText("");
        VGenderAkun.setValue("Pilih Gender");
        VUsernameAkun.setText("");
        VPasswordAkun.setText("");
    }

    @FXML
    private void btnUpdateAkunProses(ActionEvent event) {
        nama = VNamaAkun.getText();
        umur = Integer.parseInt(VUmurAkun.getText());
        gender = VGenderAkun.getValue().toString();
        username = VUsernameAkun.getText();
        pass = VPasswordAkun.getText();
        String gen = null;
        if(gender == "Laki-laki"){
            gen = "L";
        }else if(gender == "Perempuan"){
            gen = "P";
        }
        
        String sql = "UPDATE `db_user` SET `nama_user`= ?,`umur`= ?,`gender`= ?,`username`= ?,`password`= ? WHERE code_user = ?";
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nama);
            stmt.setInt(2, umur);
            stmt.setString(3, gen);
            stmt.setString(4, username);
            stmt.setString(5, pass);
            stmt.setInt(6, kodeuserm);
             
            int i = stmt.executeUpdate();
            if(i == 1){
                JOptionPane.showMessageDialog(null, "Update Data Succesfully");
                namauserm = nama;
                LabelUser.setText(nama);
            }else{
                JOptionPane.showMessageDialog(null, "Update Data Error");
            }
            stmt.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        AmbilDataDatabase();
    }

    @FXML
    private void btnCariAkunProses(ActionEvent event) {
        jabatan = CariAkun.getValue().toString();
        String sql1 = "SELECT * FROM db_user WHERE code_jabatan = 1";
        String sql2 = "SELECT * FROM db_user WHERE code_jabatan = 2";
        if(jabatan == "All"){
            AmbilDataDatabase();
        }else if(jabatan == "Admin"){
            data.clear();
            String namaj = null;
            try{
                stmt = conn.prepareStatement(sql1);
                rs = stmt.executeQuery();
                while(rs.next()){
                    if(rs.getInt(2) == 2){
                        namaj = "Member";
                    }else{
                    namaj = "Admin";
                    }
                    data.add(new Data.Data_Table_User(""+rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), ""+namaj));
                }
            }catch (SQLException ex) {
                Logger.getLogger(User_admin_HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            TableUser.setItems(data);
        }else if(jabatan == "Member"){
            data.clear();
            String namaj = null;
            try{
                stmt = conn.prepareStatement(sql2);
                rs = stmt.executeQuery();
                while(rs.next()){
                    if(rs.getInt(2) == 2){
                        namaj = "Member";
                    }else{
                    namaj = "Admin";
                    }
                    data.add(new Data.Data_Table_User(""+rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), ""+namaj));
                }
            }catch (SQLException ex) {
                Logger.getLogger(User_admin_HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            TableUser.setItems(data);
        }else{
            JOptionPane.showMessageDialog(null, "Harap Pilih Jabatan yang ingin anda Cari");
        }
    }

    @FXML
    private void btnAkunProses(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Anda Sudah di My Akun");
    }

    @FXML
    private void btnListDataProses(ActionEvent event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("User_member_Boking.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            
            HalamanMember.User_member_BokingController Data = new HalamanMember.User_member_BokingController();
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
