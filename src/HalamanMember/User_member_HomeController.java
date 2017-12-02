/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HalamanMember;

import HalamanAdmin.User_admin_HomeController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ChaVaZaSRL
 */
public class User_member_HomeController implements Initializable,ChangeListener {

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnAkun;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private TableView<Data.Data_Table_Barang> TableHome;
    private ObservableList<Data.Data_Table_Barang> data = FXCollections.observableArrayList();
    
    @FXML
    private TableColumn<?, ?> tbIdBarangHome;
    @FXML
    private TableColumn<?, ?> tbNamaHome;
    @FXML
    private TableColumn<?, ?> tbJenisHome;
    @FXML
    private TableColumn<?, ?> tbDeskHome;
    @FXML
    private TableColumn<?, ?> tbJumlahHome;
    @FXML
    private JFXButton btnExitHome;
    @FXML
    private JFXComboBox VNamaHome;
    private ObservableList<String> Vnama = FXCollections.observableArrayList();
    @FXML
    private Label VIdHome;
    @FXML
    private Label VJumHome;
    @FXML
    private JFXTextArea VDesHome;
    @FXML
    private JFXButton btnBokingHome;
    @FXML
    private JFXButton btnHapusHome;
    @FXML
    private JFXComboBox CariList;
    private ObservableList<String> cari = FXCollections.observableArrayList("ALL","Obat Luar","Obat Dalam");
    @FXML
    private JFXButton btnCariHome;
    @FXML
    private Label LabelUser;
    @FXML
    private JFXSlider VJumSlideHome;

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    
    String jenisdb,carij,namac,namauserm,tgl,catatan;
    int codeuserm,codeb,jumslide,hasil;
    private JFXDatePicker Tanggal;
    @FXML
    private JFXDatePicker VTanggalHome;
    @FXML
    private JFXButton btnDataBok;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = Control.C_Connection.Koneksi();
        CariList.setItems(cari);
        CariList.setValue("ALL");
        VTanggalHome.setValue(LocalDate.now());
        VNamaHome.setValue("Cari Barang");
        
        VJumSlideHome.setValue(0);
        VJumSlideHome.valueProperty().addListener(this);
        
        setKolomTable();
        AmbilDataBase();
        daftarBarang();
        setCellValueFromTableToTextField();
        TableHome.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
    public void dataUser(String namauser,int kodeuser){
        namauserm = namauser;
        codeuserm = kodeuser;
        LabelUser.setText(String.valueOf(namauserm));
        
    }
    
    private void daftarBarang(){
        String sql = "SELECT * FROM db_barang";
        try{
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                Vnama.add(rs.getString(2));
            }
        }catch (SQLException ex) {
            Logger.getLogger(User_admin_HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        VNamaHome.setItems(Vnama);
    }
    
    private void setCellValueFromTableToTextField(){
        TableHome.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Data.Data_Table_Barang pl= (TableHome.getItems().get(TableHome.getSelectionModel().getSelectedIndex()));
                VJumSlideHome.setValue(0);
                VTanggalHome.setValue(LocalDate.now());
                VIdHome.setText(pl.getKode_barang());
                VNamaHome.setValue(pl.getNama_barang());
                VJumSlideHome.setMax(Double.valueOf(pl.getJumlah()));
            }
            
        });
    }

    private void setKolomTable(){
        tbIdBarangHome.setCellValueFactory(new PropertyValueFactory<>("kode_barang"));
        tbNamaHome.setCellValueFactory(new PropertyValueFactory<>("Nama_barang"));
        tbJenisHome.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        tbJumlahHome.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        tbDeskHome.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
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
        TableHome.setItems(data);
    }

    @FXML
    private void btnHomeProses(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Anda Sudah di Home's Member");
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
            
            Data.dataUser(namauserm,codeuserm);
            
            
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
    private void btnExitHomeProses(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "You want to EXIT ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if(alert.getResult() == ButtonType.YES){
            Stage stage = (Stage) btnExitHome.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void VNamaHomeProses(ActionEvent event) {
        namac = VNamaHome.getValue().toString();
        String sql = "SELECT * FROM db_barang";
        try{
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                if(rs.getString(2).equalsIgnoreCase(namac)){
                    VJumSlideHome.setValue(0);
                    VTanggalHome.setValue(LocalDate.now());
                    VIdHome.setText(rs.getString(1));
                    VJumSlideHome.setMax(Double.valueOf(rs.getString(4)));
                }
            }
        }catch (SQLException ex) {
            Logger.getLogger(User_admin_HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnBokingHomeProses(ActionEvent event) {
        if(VIdHome.getText().equalsIgnoreCase("") || VDesHome.getText().equals("") || VNamaHome.getValue().toString().equalsIgnoreCase("Cari Barang") || VJumHome.getText().equals("0") ){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Harap Isi Data-data user atau ID user telah Dipakai", ButtonType.OK);
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ingin Memboking ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            codeb = Integer.parseInt(VIdHome.getText());
            tgl = VTanggalHome.getValue().toString();
            catatan = VDesHome.getText();
            jumslide = (int) Integer.toUnsignedLong((int) VJumSlideHome.getValue());

            String sql1 = "INSERT INTO `db_boking`(`code_user`, `code_barang`, `jumlah_barang`, `tgl_boking`, `Verifikasi`, `deskripsi`) VALUES (?,?,?,?,?,?)";
            String sql2 = "Select * from db_barang";
            String sql3 = "UPDATE `db_barang` SET `jumlah`=? WHERE code_barang = ?";

            try{
                stmt = conn.prepareStatement(sql1);
                stmt.setInt(1, codeuserm);
                stmt.setInt(2, codeb);
                stmt.setInt(3, jumslide);
                stmt.setString(4, tgl);
                stmt.setString(5, "Tidak Diterima");
                stmt.setString(6, catatan);

                int i = stmt.executeUpdate(); 
                if (i == 1) {
                    JOptionPane.showMessageDialog(null, "Boking Obat Succesfully");
                    try{
                        stmt = conn.prepareStatement(sql2);
                        rs = stmt.executeQuery();
                        while(rs.next()){
                            if(rs.getInt(1) == codeb){
                                hasil = rs.getInt(4) - jumslide;
                            }
                        }
                        stmt = conn.prepareStatement(sql3);
                        stmt.setInt(1, hasil);
                        stmt.setInt(2, codeb);
                        stmt.executeUpdate();
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    hapusView();
                    AmbilDataBase();
                }else {
                    JOptionPane.showMessageDialog(null, "Boking Error");
                }
                stmt.close();
                
            }catch(SQLException se){
                JOptionPane.showMessageDialog(null, se);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        }
        
    }

    @FXML
    private void btnHapusHomeProses(ActionEvent event) {
        hapusView();
    }
    
    private void hapusView(){
        VIdHome.setText("");
        VNamaHome.setValue("Cari Barang");
        VJumHome.setText("0");
        VJumSlideHome.setValue(0);
        VTanggalHome.setValue(LocalDate.now());
        VDesHome.setText("");
    }

    @FXML
    private void btnCariHomeProses(ActionEvent event) {
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
            TableHome.setItems(data);
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
            TableHome.setItems(data);
        }else{
            JOptionPane.showMessageDialog(null, "Harap Pilih Jabatan yang ingin anda Cari");
        }
    }

    @FXML
    private void VJumSlideHomeProses(MouseEvent event) {
        
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        int val = 0;
        val = (int) Integer.toUnsignedLong((int) VJumSlideHome.getValue());
        
        VJumHome.setText(""+val);
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
            
            Data.dataUser(namauserm,codeuserm);
                    
            
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException e) {
            System.out.println("Failed to create new Window." + e);
        }
    }

    
}
