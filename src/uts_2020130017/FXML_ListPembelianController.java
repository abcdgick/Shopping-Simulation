/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uts_2020130017;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static uts_2020130017.FXMLDocumentController.keranjang;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class FXML_ListPembelianController implements Initializable {

    @FXML
    private AnchorPane ListPane;
    @FXML
    private TextField txtSubtotal;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnKembali;
    @FXML
    private TableView<BarangModel> tbvList;

    private int sub = 0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTabel();
        kalkuSub();
    }    

    @FXML
    private void editKlik(ActionEvent event) {
        BarangModel dis = tbvList.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Edit");
        alert.setHeaderText("Edit Barang Belanjaan");
        alert.setContentText("Mau edit barang " + dis.getNamaBarang()+"?");
        
        if(alert.showAndWait().get() == ButtonType.OK){
            FXMLDocumentController.index = keranjang.indexOf(dis);
            kembali();
        }
    }

    @FXML
    private void kembaliKlik(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Kembali ke form utama");
        alert.setContentText("Kembali ke form utama?");
        
        if(alert.showAndWait().get() == ButtonType.OK){
            kembali();
        }
    }
    
    private void kembali(){
        btnKembali.getScene().getWindow().hide();
    }
    
    private void setTabel(){
            tbvList.getColumns().clear();
            System.out.println("Jalan");
            TableColumn col = new TableColumn("Gambar Barang");
            col.setCellFactory(param ->{
                final ImageView imageView = new ImageView();
                imageView.setFitHeight(60);
                imageView.setFitWidth(35);
                
                TableCell<BarangModel, Image> cell = new TableCell<BarangModel, Image>(){
                    @Override
                    public void updateItem(Image item, boolean emtpy){
                        if(item != null){
                            imageView.setImage(item);
                        }
                    }
                };
                cell.setGraphic(imageView);
                return cell;
            });
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Image>("image"));
            tbvList.getColumns().addAll(col);
            col = new TableColumn("Nama Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, String>("namaBarang"));
            tbvList.getColumns().addAll(col);
            col = new TableColumn("Harga Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("harga"));
            tbvList.getColumns().addAll(col);
            col = new TableColumn("Jumlah");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("jumlah"));
            tbvList.getColumns().addAll(col);
            col = new TableColumn("Subtotal Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("sub"));
            tbvList.getColumns().addAll(col);
            
            tbvList.setItems(keranjang);
    }
    
    private void kalkuSub(){
        for(BarangModel dis: keranjang){
            sub += dis.getSub();
        }
        txtSubtotal.setText(String.valueOf(sub));
    }
}
