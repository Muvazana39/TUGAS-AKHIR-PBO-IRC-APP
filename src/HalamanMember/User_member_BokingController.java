/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HalamanMember;

import HalamanAdmin.User_admin_HomeController;
import com.jfoenix.controls.JFXButton;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ChaVaZaSRL
 */
public class User_member_BokingController implements Initializable {

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnAkun;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private TableView<Data.Data_Table_Boking> TableBok;
    private ObservableList<Data.Data_Table_Boking> data = FXCollections.observableArrayList();
    
    @FXML
    private TableColumn<?, ?> tbIdBokingBok;
    @FXML
    private TableColumn<?, ?> tbNamaUBok;
    @FXML
    private TableColumn<?, ?> tbNamaBBok;
    @FXML
    private TableColumn<?, ?> tbJumlahBok;
    @FXML
    private TableColumn<?, ?> tbTglBok;
    @FXML
    private TableColumn<?, ?> tbStatusBok;
    @FXML
    private TableColumn<?, ?> tbCatBok;
    @FXML
    private JFXButton btnExitBok;
    @FXML
    private Label LabelUser;
    @FXML
    private JFXButton btnDataBok;
    
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement stmt2 = null;
    private ResultSet rs2 = null;
    private String namauserm;
    private int kodeuserm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = Control.C_Connection.Koneksi();
        
        setKolomTable();
        AmbilDataDatabase();
        TableBok.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
    public void dataUser(String namauser,int kodeuser){
        namauserm = namauser;
        kodeuserm = kodeuser;
        LabelUser.setText(namauserm);
        
    }
    
    private void setKolomTable(){
        tbIdBokingBok.setCellValueFactory(new PropertyValueFactory<>("kode_boking"));
        tbNamaUBok.setCellValueFactory(new PropertyValueFactory<>("nama_user"));
        tbNamaBBok.setCellValueFactory(new PropertyValueFactory<>("nama_barang"));
        tbJumlahBok.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        tbTglBok.setCellValueFactory(new PropertyValueFactory<>("tgl_boking"));
        tbStatusBok.setCellValueFactory(new PropertyValueFactory<>("konfirmasi"));
        tbCatBok.setCellValueFactory(new PropertyValueFactory<>("catatan"));
    }
 
    private void AmbilDataDatabase(){
        data.clear();
        String sql = "SELECT db_boking.code_boking, db_user.nama_user, db_barang.nama_barang, db_boking.jumlah_barang, db_boking.tgl_boking, db_boking.verifikasi, db_boking.deskripsi FROM db_boking, db_user, db_barang WHERE db_user.code_user = db_boking.code_user AND db_barang.code_barang = db_boking.code_barang";
        //String sql2 = "Select * from db_jabatan";
        String namaj = null;
        try{
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            //stmt2 = conn.prepareStatement(sql2);
            //rs2 = stmt2.executeQuery();
            while(rs.next()){
                data.add(new Data.Data_Table_Boking(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        }catch (SQLException ex) {
            Logger.getLogger(User_admin_HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TableBok.setItems(data);
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
    private void btnAkunProses(ActionEvent event) {
        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("User_member_Akun.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            
            HalamanMember.User_member_AkunController Data = new HalamanMember.User_member_AkunController();
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
    private void btnExitBokProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ingin Keluar ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            Stage stage = (Stage) btnExitBok.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void btnListBokProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Anda Sudah Di Data Transaksi", ButtonType.OK);
        alert.showAndWait();
    }
    
}
