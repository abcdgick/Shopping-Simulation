/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uts_2020130017;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static uts_2020130017.FXMLDocumentController.all;
import static uts_2020130017.FXMLDocumentController.keranjang;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class FXML_UbahHargaController implements Initializable {

    @FXML
    private Label textNama;
    @FXML
    private Label textKategori;
    @FXML
    private Label textStok;
    @FXML
    private TextField txtHarga;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnBatal;
    
    private BarangModel diss;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void updateKlik(ActionEvent event) {
        int harga = -1;
        try {
            harga = Integer.parseInt(txtHarga.getText());
            if(harga <= 0){
                Alert a = new Alert(Alert.AlertType.ERROR, "Harga harus > 0!", ButtonType.OK);
                a.showAndWait();
            } else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ubah Harga");
                alert.setHeaderText("Ubah Harga Barang "+diss.getNamaBarang());
                alert.setContentText("Yakin ubah harga barang?");

                if(alert.showAndWait().get() == ButtonType.OK){
                    all.get(all.indexOf(diss)).setHarga(harga);
                    if(keranjang.indexOf(diss) >= 0) {
                        keranjang.get(keranjang.indexOf(diss)).setHarga(harga);
                        keranjang.get(keranjang.indexOf(diss)).setSub(harga * diss.getJumlah());
                    }
                    kembali();
                }
            }
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Tolong inputkan harga dengan benar", ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    private void batalKlik(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Kembali ke form utama");
        alert.setContentText("Kembali ke form utama?");
        
        if(alert.showAndWait().get() == ButtonType.OK){
            kembali();
        }
    }
    
    private void kembali(){
        btnBatal.getScene().getWindow().hide();
    }

    public void ubah(BarangModel dis){
        diss = dis;
        textNama.setText(dis.getNamaBarang());
        textKategori.setText(dis.getJenis());
        textStok.setText(String.valueOf(dis.getStok()));
        txtHarga.setText(String.valueOf(dis.getHarga()));
    }
    
}
