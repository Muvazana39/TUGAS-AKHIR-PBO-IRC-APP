/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HalamanAdmin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
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
public class User_admin_BokingController implements Initializable {

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnListBarang;
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
    private JFXButton btnHapusAllBok;
    @FXML
    private JFXButton btnExitBok;
    @FXML
    private JFXTextField VNamaBok;
    @FXML
    private JFXTextField VJumlahBok;
    @FXML
    private Label VIdBok;
    @FXML
    private JFXTextArea VCatatanBok;
    @FXML
    private JFXTextField VTanggalBok;
    @FXML
    private JFXButton btnHapusDataBok;
    @FXML
    private JFXButton btnUpdateBok;
    @FXML
    private JFXButton btnHapusFBok;
    
    @FXML
    private Label LabelUser;
    @FXML
    private JFXButton btnDataBok;
    @FXML
    private JFXComboBox VStatusBok;
    ObservableList<String> vstatusbok = FXCollections.observableArrayList("Diterima", "Tidak Diterima");
    
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement stmt2 = null;
    private ResultSet rs2 = null;
    @FXML
    private JFXTextField VNamaBBok;
    
    private String namauserm, status;
    private int kode, kodeuserm;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = Control.C_Connection.Koneksi();
        VStatusBok.setItems(vstatusbok);
        
        setKolomTable();
        AmbilDataDatabase();
        setCellValueFromTableToTextField();
        TableBok.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    
    public void dataUser(String namauser,int kodeuser){
        namauserm = namauser;
        kodeuserm = kodeuser;
        LabelUser.setText(namauserm);
        
    }

    private void setCellValueFromTableToTextField(){
        TableBok.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Data.Data_Table_Boking pl= (TableBok.getItems().get(TableBok.getSelectionModel().getSelectedIndex()));
                VIdBok.setText(pl.getKode_boking());
                VNamaBok.setText(pl.getNama_user());
                VNamaBBok.setText(pl.getNama_barang());
                VJumlahBok.setText(pl.getJumlah());
                VTanggalBok.setText(pl.getTgl_boking());
                VStatusBok.setValue(pl.getKonfirmasi());
                VCatatanBok.setText(pl.getCatatan());
            }
            
        });
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
    private void btnHapusAllBokProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Konfirmasi Untuk Menghapus Semua Data Boking ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            String sql = "DELETE FROM `db_boking`";

            try{
                stmt = conn.prepareStatement(sql);

                int i = stmt.executeUpdate();
                if(i >= 1){
                    JOptionPane.showMessageDialog(null, "Delete Data Boking Succesfully");
                    AmbilDataDatabase();
                    VIdBok.setText("");
                    VNamaBok.setText("");
                    VNamaBBok.setText("");
                    VJumlahBok.setText("");
                    VTanggalBok.setText("");
                    VStatusBok.setValue("");
                    VCatatanBok.setText("");
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
    private void btnExitBokProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ingin Keluar ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            Stage stage = (Stage) btnExitBok.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void btnHapusDataBokProses(ActionEvent event) {
        if(VIdBok.getText().equalsIgnoreCase("") || VNamaBok.getText().equalsIgnoreCase("") || VNamaBBok.getText().equals("") || VJumlahBok.getText().equals("") || VTanggalBok.getText().equals("") || VStatusBok.getValue().toString().equalsIgnoreCase("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Harap Isi Data-data Boking yang akan di DIHAPUS", ButtonType.OK);
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Konfirmasi Untuk Menghapus Data Boking ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.YES){
                kode = Integer.parseInt(VIdBok.getText());
                String sql = "DELETE FROM `db_boking` WHERE code_boking = ?";
                try{
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, kode);

                    int i = stmt.executeUpdate();
                    if(i == 1){
                        JOptionPane.showMessageDialog(null, "Delete Akun Succesfully");
                        AmbilDataDatabase();
                        VIdBok.setText("");
                        VNamaBok.setText("");
                        VNamaBBok.setText("");
                        VJumlahBok.setText("");
                        VTanggalBok.setText("");
                        VStatusBok.setValue("");
                        VCatatanBok.setText("");
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
    private void btnUpdateBokProses(ActionEvent event) {
        if(VIdBok.getText().equalsIgnoreCase("") || VNamaBok.getText().equalsIgnoreCase("") || VNamaBBok.getText().equals("") || VJumlahBok.getText().equals("") || VTanggalBok.getText().equals("") || VStatusBok.getValue().toString().equalsIgnoreCase("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Harap Isi Data-data Boking yang akan di UPDATE", ButtonType.OK);
            alert.showAndWait();
        }else{
            kode = Integer.parseInt(VIdBok.getText());
            status = VStatusBok.getValue().toString();


            String sql = "UPDATE `db_boking` SET `verifikasi`= ? WHERE code_boking = ?";
            try{
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, status);
                stmt.setInt(2, kode);

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
    private void btnHapusFBokProses(ActionEvent event) {
        VIdBok.setText("");
        VNamaBok.setText("");
        VNamaBBok.setText("");
        VJumlahBok.setText("");
        VTanggalBok.setText("");
        VStatusBok.setValue("");
        VCatatanBok.setText("");
    }


    @FXML
    private void btnListDataProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Anda Sudah Di Data Transaksi", ButtonType.OK);
        alert.showAndWait();
    }
    
}
