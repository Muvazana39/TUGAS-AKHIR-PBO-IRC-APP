/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectta;
import java.sql.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ChaVaZaSRL
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXButton SigUp;
    @FXML
    private AnchorPane PanelLogIn;
    @FXML
    private JFXButton LogIn;
    @FXML
    private JFXButton UsernameForgot;
    @FXML
    private JFXButton PasswordForgot;
    @FXML
    private JFXTextField UsernameLogIn;
    @FXML
    private JFXPasswordField PasswordLogIn;
    @FXML
    private JFXButton ClearLogIn;
    @FXML
    private AnchorPane PanelSigUp;
    @FXML
    private JFXTextField UsernameSigUp;
    @FXML
    private JFXTextField NamaSigUp;
    @FXML
    private JFXTextField UmurSigUp;
    @FXML
    private JFXRadioButton rdLakiSigUp;
    @FXML
    private ToggleGroup Gender;
    @FXML
    private JFXRadioButton rdPerempuanSigUp;
    @FXML
    private JFXButton ClearSigUp;
    @FXML
    private JFXPasswordField PasswordSigUp;
    
    public String nama,usernameS,passwordS,gender,usernameL,passwordL;
    
    public static String user;
    @FXML
    private JFXButton LogInHeader;
    
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private PreparedStatement stmt1 = null;
    private PreparedStatement stmt2 = null;
    @FXML
    private JFXButton SigUpHeader;
    @FXML
    private JFXButton btnExitHome;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PanelSigUp.setVisible(false);
        PanelLogIn.setVisible(false);
        
        
        conn = Control.C_Connection.Koneksi();
    }  
    
    public void start(Stage primaryStage){
        primaryStage.initStyle(StageStyle.UNDECORATED);
    }

    @FXML
    private void SigUpProses(ActionEvent event) {
        PanelSigUp.setVisible(true);
        PanelLogIn.setVisible(true);
    }

    @FXML
    private void UsernameForgotProses(ActionEvent event) {
        
    }

    @FXML
    private void PasswordForgotProses(ActionEvent event) {
        
    }

    @FXML
    private void ProsesClearLogIn(ActionEvent event) {
        UsernameLogIn.setText("");
        PasswordLogIn.setText("");
    }

    @FXML
    private void ProsesLogIn(ActionEvent event) {
        conn = Control.C_Connection.Koneksi();
        usernameL = UsernameLogIn.getText();
        passwordL = PasswordLogIn.getText();
        String sql1 = "SELECT * FROM `db_user` WHERE `username` = ? && `password` = ? && `code_jabatan` = 1";
        String sql2 = "SELECT * FROM `db_user` WHERE `username` = ? && `password` = ? && `code_jabatan` = 2";
        
        if(UsernameLogIn.getText().equals("") || PasswordLogIn.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Harap Isi Username atau Password!", ButtonType.OK);
            alert.showAndWait();
        }else if(!UsernameLogIn.getText().equals("") && !PasswordLogIn.getText().equals("")){
            try{
            stmt1 = conn.prepareStatement(sql1);
            stmt2 = conn.prepareStatement(sql2);
            stmt1.setString(1, usernameL);
            stmt1.setString(2, passwordL);
            stmt2.setString(1, usernameL);
            stmt2.setString(2, passwordL);
            ResultSet rs1 = stmt1.executeQuery(); 
            ResultSet rs2 = stmt2.executeQuery();
            if (rs1.next()) {
                
                String namakirim = rs1.getString(3);
                int kodekirim = rs1.getInt(1);
                
                try {
                    // Hide this current window (if this is what you want)
                    ((Node)(event.getSource())).getScene().getWindow().hide();
            
                    // Load the new fxml
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/HalamanAdmin/User_admin_Home.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    
                    HalamanAdmin.User_admin_HomeController Data = new HalamanAdmin.User_admin_HomeController();
                    Data = fxmlLoader.getController();
            
                    Data.dataUser(namakirim,kodekirim);
            
                    // Create new stage (window), then set the new Scene
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.show();
            
                } catch (IOException e) {
                    System.out.println("Failed to create new Window." + e);
                }
                //perlu deklarasi user diclass utama.                 
                user = usernameL;
                
            }else if (rs2.next()) {
                String namakirim = rs2.getString(3);
                int kodekirim = rs2.getInt(1);
                try {
                    
                    // Hide this current window (if this is what you want)
                    ((Node)(event.getSource())).getScene().getWindow().hide();
            
                    // Load the new fxml
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/HalamanMember/User_member_Home.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
            
                    HalamanMember.User_member_HomeController Data = new HalamanMember.User_member_HomeController();
                    Data = fxmlLoader.getController();
            
                    Data.dataUser(namakirim,kodekirim);
                    
                    // Create new stage (window), then set the new Scene
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.show();
            
                } catch (IOException e) {
                    System.out.println("Failed to create new Window." + e);
                }
                //perlu deklarasi user diclass utama.                 
                user = usernameL;
                
            }else {
                JOptionPane.showMessageDialog(null, "Username Atau Password Salah!");
                PasswordLogIn.setText("");
                UsernameLogIn.requestFocus();
            }
            
            rs1.close();
            rs2.close();
            stmt1.close();
            stmt2.close();
            conn.close();
            
        }catch(SQLException se){
            JOptionPane.showMessageDialog(null, se);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally{
            try{
                if(stmt1 != null && stmt2 != null){
                    stmt1.close();
                    stmt2.close();
                }
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        }
    }

    @FXML
    private void ProsesSigUp(ActionEvent event) {
        if(UsernameSigUp.getText().equalsIgnoreCase("") || PasswordSigUp.getText().equals("") || NamaSigUp.getText().equals("") || UmurSigUp.getText().equals("") || (!rdPerempuanSigUp.isSelected() && !rdLakiSigUp.isSelected())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Harap Isi Data-data user", ButtonType.OK);
            alert.showAndWait();
        }else{
            conn = Control.C_Connection.Koneksi();
            usernameS = UsernameSigUp.getText();
            passwordS = PasswordSigUp.getText();
            nama = NamaSigUp.getText();
            int umur = Integer.parseInt(UmurSigUp.getText());
            if(rdLakiSigUp.isSelected()){
                gender = "L";
            }else if(rdPerempuanSigUp.isSelected()){
                gender = "P";
            }
            String sql = "INSERT INTO `db_user`(`code_jabatan`, `nama_user`, `umur`, `gender`, `username`, `password`) VALUES (2,?,?,?,?,?)";
            try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nama);
            stmt.setInt(2, umur);
            stmt.setString(3, gender);
            stmt.setString(4, usernameS);
            stmt.setString(5, passwordS);
            int i = stmt.executeUpdate(); 
            if (i == 1) {
                JOptionPane.showMessageDialog(null, "Insert Successfully");
                PanelSigUp.setVisible(false);
                PanelLogIn.setVisible(false);
            }else {
                JOptionPane.showMessageDialog(null, "data Can't be Write");
            }
            
            stmt.close();
            conn.close();
            
        }catch(SQLException se){
            JOptionPane.showMessageDialog(null, se);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally{
            try{
                if(stmt != null){
                    stmt.close();
                }
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        }
    }

    @FXML
    private void ProsesClearSigUp(ActionEvent event) {
        UsernameSigUp.setText("");
        PasswordSigUp.setText("");
        NamaSigUp.setText("");
        UmurSigUp.setText("");
        rdLakiSigUp.setSelected(false);
        rdPerempuanSigUp.setSelected(false);
    }

    @FXML
    private void LogInHeaderProses(ActionEvent event) {
        PanelSigUp.setVisible(false);
        PanelLogIn.setVisible(false);
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
    
}
