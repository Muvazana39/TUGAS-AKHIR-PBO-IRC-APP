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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import static projectta.FXMLDocumentController.user;

/**
 * FXML Controller class
 *
 * @author ChaVaZaSRL
 */
public class User_admin_HomeController implements Initializable {

    @FXML
    private JFXButton btnExitHome;
    @FXML
    private Label LabelUser;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnListBarang;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private TableView<Data.Data_Table_User> TableUser;
    @FXML
    private TableColumn<?, ?> tbIdUserHome;
    @FXML
    private TableColumn<?, ?> tbNamaHome;
    @FXML
    private TableColumn<?, ?> tbUmurHome;
    @FXML
    private TableColumn<?, ?> tbGenderHome;
    @FXML
    private TableColumn<?, ?> tbUsernameHome;
    @FXML
    private TableColumn<?, ?> tbPasswordHome;
    @FXML
    private TableColumn<?, ?> tbJabatanHome;
    @FXML
    private JFXComboBox VGenderHome;
    ObservableList<String> vgenderhome = FXCollections.observableArrayList("Laki-laki", "Perempuan");
    
    @FXML
    private JFXComboBox VJabatanHome;
    ObservableList<String> vjabatanhome = FXCollections.observableArrayList("Admin", "Member");
    
    @FXML
    private JFXTextField VNamaHome;
    @FXML
    private JFXTextField VUmurHome;
    @FXML
    private JFXTextField VUsernameHome;
    @FXML
    private JFXPasswordField VPasswordHome;
    @FXML
    private Label VIdUserHome;
    
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement stmt2 = null;
    private ResultSet rs2 = null;
    private ObservableList<Data.Data_Table_User> data = FXCollections.observableArrayList();
    @FXML
    private JFXComboBox CariHome;
    ObservableList<String> Cari = FXCollections.observableArrayList("All","Admin", "Member");
    
    
    private String jabatan, nama, gender, username, pass, jab = null, namauserm;
    private int kode, umur,kodeuserm;
    @FXML
    private JFXButton btnHapusMHome;
    @FXML
    private JFXButton btnHapusDataHome;
    @FXML
    private JFXButton btnUpdateHome;
    @FXML
    private JFXButton btnSimpanHome;
    @FXML
    private JFXButton btnHapusAHome;
    @FXML
    private JFXButton btnCariHome;
    @FXML
    private JFXButton btnDataBok;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = Control.C_Connection.Koneksi();
        VGenderHome.setItems(vgenderhome);
        VJabatanHome.setItems(vjabatanhome);
        CariHome.setItems(Cari);
        CariHome.setValue("All");
        VGenderHome.setValue("Pilih Gender");
        VJabatanHome.setValue("Pilih Jabatan");
        

        setKolomTable();
        AmbilDataDatabase();
        setCellValueFromTableToTextField();
        TableUser.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
    public void dataUser(String namauser,int kodeuser){
        namauserm = namauser;
        kodeuserm = kodeuser;
        LabelUser.setText(namauserm);
        
    }
    private void setCellValueFromTableToTextField(){
        TableUser.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                String gend = null;
                Data.Data_Table_User pl= (TableUser.getItems().get(TableUser.getSelectionModel().getSelectedIndex()));
                if(pl.getGender_user().equalsIgnoreCase("L")){
                    gend = "Laki-laki";
                }else if(pl.getGender_user() .equalsIgnoreCase("P")){
                    gend = "Perempuan";
                }
                VIdUserHome.setText(pl.getKode_user());
                VNamaHome.setText(pl.getNama_user());
                VUmurHome.setText(pl.getUmur_user());
                VGenderHome.setValue(gend);
                VJabatanHome.setValue(pl.getJabatan_user());
                VUsernameHome.setText(pl.getUsername_user());
                VPasswordHome.setText(pl.getPassword_user());
            }
            
        });
    }
    private void setKolomTable(){
        tbIdUserHome.setCellValueFactory(new PropertyValueFactory<>("kode_user"));
        tbNamaHome.setCellValueFactory(new PropertyValueFactory<>("Nama_user"));
        tbUmurHome.setCellValueFactory(new PropertyValueFactory<>("umur_user"));
        tbGenderHome.setCellValueFactory(new PropertyValueFactory<>("gender_user"));
        tbUsernameHome.setCellValueFactory(new PropertyValueFactory<>("username_user"));
        tbPasswordHome.setCellValueFactory(new PropertyValueFactory<>("password_user"));
        tbJabatanHome.setCellValueFactory(new PropertyValueFactory<>("jabatan_user"));
    }
 
    private void AmbilDataDatabase(){
        data.clear();
        String sql = "SELECT * FROM db_user";
        //String sql2 = "Select * from db_jabatan";
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
    private void btnExitHomeProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ingin Keluar ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            Stage stage = (Stage) btnExitHome.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void btnHomeProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Anda Sudah di Admin's Home", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void btnListBarangProses(ActionEvent event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("User_admin_List.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            
            HalamanAdmin.User_admin_ListController Data = new HalamanAdmin.User_admin_ListController();
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
    private void btnHapusDataHomeProses(ActionEvent event) {
        VIdUserHome.setText("");
        VNamaHome.setText("");
        VUmurHome.setText("");
        VGenderHome.setValue("Pilih Gender");
        VJabatanHome.setValue("Pilih Jabatan");
        VUsernameHome.setText("");
        VPasswordHome.setText("");
    }
    
    @FXML
    private void btnSimpanHomeProses(ActionEvent event) {
        if(!VIdUserHome.getText().equalsIgnoreCase("") || VUsernameHome.getText().equalsIgnoreCase("") || VPasswordHome.getText().equals("") || VNamaHome.getText().equals("") || VUmurHome.getText().equals("") || VGenderHome.getValue().toString().equalsIgnoreCase("Pilih Gender") || VJabatanHome.getValue().toString().equalsIgnoreCase("Pilih Jabatan")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Harap Isi Data-data user atau ID user telah Dipakai", ButtonType.OK);
            alert.showAndWait();
        }else if(jikaInt(VUmurHome) == false){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kolom Umur Salah", ButtonType.OK);
            alert.showAndWait();
        }else{
            
            username = VUsernameHome.getText();
            pass = VPasswordHome.getText();
            nama = VNamaHome.getText();
            umur = Integer.parseInt(VUmurHome.getText());
            gender = VGenderHome.getValue().toString();
            jabatan = VJabatanHome.getValue().toString();
            String gen = null;
            if(jabatan == "Admin"){
                jab = "1";
            }else if(jabatan == "Member"){
                jab = "2";
            }
            if(gender == "Laki-laki"){
                gen = "L";
            }else if(gender == "Perempuan"){
                gen = "P";
            }

            String sql = "INSERT INTO `db_user`(`code_jabatan`, `nama_user`, `umur`, `gender`, `username`, `password`) VALUES (?,?,?,?,?,?)";
            try{
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, jab);
                stmt.setString(2, nama);
                stmt.setInt(3, umur);
                stmt.setString(4, gen);
                stmt.setString(5, username);
                stmt.setString(6, pass);

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
            AmbilDataDatabase();
        }
        
    }
    
    private boolean jikaInt(JFXTextField input){
        try{
            int umur = Integer.parseInt(input.getText());
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    @FXML
    private void btnUpdateHomeProses(ActionEvent event) {
        if(VIdUserHome.getText().equalsIgnoreCase("") || VUsernameHome.getText().equalsIgnoreCase("") || VPasswordHome.getText().equals("") || VNamaHome.getText().equals("") || VUmurHome.getText().equals("") || VGenderHome.getValue().toString().equalsIgnoreCase("Pilih Gender") || VJabatanHome.getValue().toString().equalsIgnoreCase("Pilih Jabatan")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Harap Isi Data-data user yang akan di UPDATE", ButtonType.OK);
            alert.showAndWait();
        }else{
            kode = Integer.parseInt(VIdUserHome.getText());
            nama = VNamaHome.getText();
            umur = Integer.parseInt(VUmurHome.getText());
            gender = VGenderHome.getValue().toString();
            username = VUsernameHome.getText();
            pass = VPasswordHome.getText();
            jabatan = VJabatanHome.getValue().toString();
            String gen = null;
            if(jabatan == "Admin"){
                jab = "1";
            }else if(jabatan == "Member"){
                jab = "2";
            }
            if(gender == "Laki-laki"){
                gen = "L";
            }else if(gender == "Perempuan"){
                gen = "P";
            }


            String sql = "UPDATE `db_user` SET `code_jabatan`= ?,`nama_user`= ?,`umur`= ?,`gender`= ?,`username`= ?,`password`= ? WHERE code_user = ?";
            try{
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, jab);
                stmt.setString(2, nama);
                stmt.setInt(3, umur);
                stmt.setString(4, gen);
                stmt.setString(5, username);
                stmt.setString(6, pass);
                stmt.setInt(7, kode);

                int i = stmt.executeUpdate();
                if(i == 1){
                    JOptionPane.showMessageDialog(null, "Update Data Succesfully");
                }else{
                    JOptionPane.showMessageDialog(null, "Update Data Error");
                }
                stmt.close();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }

            AmbilDataDatabase();
        }
        
    }

    @FXML
    private void btnCariHomeProses(ActionEvent event) {
        jabatan = CariHome.getValue().toString();
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
    private void btnHapusAHomeProses(ActionEvent event) {
        if(VIdUserHome.getText().equalsIgnoreCase("") || VUsernameHome.getText().equalsIgnoreCase("") || VPasswordHome.getText().equals("") || VNamaHome.getText().equals("") || VUmurHome.getText().equals("") || VGenderHome.getValue().toString().equalsIgnoreCase("Pilih Gender") || VJabatanHome.getValue().toString().equalsIgnoreCase("Pilih Jabatan")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Harap Isi Data-data user yang akan di DIHAPUS", ButtonType.OK);
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Konfirmasi Untuk Menghapus Akun? ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES){
                kode = Integer.parseInt(VIdUserHome.getText());
                String sql = "DELETE FROM `db_user` WHERE code_user = ?";
                try{
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, kode);

                    int i = stmt.executeUpdate();
                    if(i == 1){
                        JOptionPane.showMessageDialog(null, "Delete Akun Succesfully");
                        AmbilDataDatabase();
                        VIdUserHome.setText("");
                        VNamaHome.setText("");
                        VUmurHome.setText("");
                        VGenderHome.setValue("Pilih Gender");
                        VJabatanHome.setValue("Pilih Jabatan");
                        VUsernameHome.setText("");
                        VPasswordHome.setText("");
                    }else{
                        JOptionPane.showMessageDialog(null, "Delete Data Error");
                    }
                    stmt.close();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
        
    }

    @FXML
    private void btnHapusMHomeProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Konfirmasi Untuk Menghapus Semua Akun Member ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            String sql = "DELETE FROM `db_user` WHERE code_jabatan = 2";

            try{
                stmt = conn.prepareStatement(sql);

                int i = stmt.executeUpdate();
                if(i >= 1){
                    JOptionPane.showMessageDialog(null, "Delete Akun Member Succesfully");
                    AmbilDataDatabase();
                    VIdUserHome.setText("");
                    VNamaHome.setText("");
                    VUmurHome.setText("");
                    VGenderHome.setValue("Pilih Gender");
                    VJabatanHome.setValue("Pilih Jabatan");
                    VUsernameHome.setText("");
                    VPasswordHome.setText("");
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
