/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uts_2020130017;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import static uts_2020130017.FXMLDocumentController.keranjang;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class FXML_StrukController implements Initializable {

    @FXML
    private Button btnExit;
    @FXML
    private Label listItem;
    @FXML
    private Label listHarga;
    @FXML
    private Label listKali;
    @FXML
    private Label listJumlah;
    @FXML
    private Label txtItem;
    @FXML
    private Label txtDiskon;
    @FXML
    private Label txtBelanja;
    @FXML
    private Label txtPpn;
    @FXML
    private Label txtBayar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void exitKlik(ActionEvent event) {
        ChoiceDialog<String> rating = new ChoiceDialog<>("5", "5", "4", "3", "2", "1");
        rating.setTitle("Terima Kasih");
        rating.setHeaderText("Kasih rating");
        rating.setContentText("Kasih rating pengalaman berbelanja kamu!");
        rating.showAndWait();
        System.exit(0);
    }
    
    public void transfer(double subtotal, double disc, double total, double ppn, double bayar){
        siapinText();
        txtItem.setText("Rp. " + String.format(Locale.GERMAN, "%,d",Math.round(subtotal)));
        txtDiskon.setText("Rp. " + String.format(Locale.GERMAN, "%,d",Math.round(disc)));
        txtBelanja.setText("Rp. " + String.format(Locale.GERMAN, "%,d",Math.round(total)));
        txtPpn.setText("Rp. " + String.format(Locale.GERMAN, "%,d",Math.round(ppn)));
        txtBayar.setText("Rp. " + String.format(Locale.GERMAN, "%,d",Math.round(bayar)));
    }
    
    //spasi nya dua (\n\n)
    private void siapinText(){
        String tempItem = "", tempJumlah = "", tempHarga = "", tempKali = "";
        for(BarangModel diss:keranjang){
            tempItem +=  diss.getNamaBarang() + "\n\n";
            tempJumlah += diss.getJumlah() + "\n\n";
            tempHarga += String.format(Locale.GERMAN, "%,d",diss.getHarga()) + "\n\n";
            tempKali += String.format(Locale.GERMAN, "%,d",(diss.getHarga()) * diss.getJumlah()) + "\n\n";
        }
        listItem.setText(tempItem);
        listJumlah.setText(tempJumlah);
        listHarga.setText(tempHarga);
        listKali.setText(tempKali);
    }
    
}
