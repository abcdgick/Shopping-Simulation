/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package uts_2020130017;


import java.io.IOException;
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
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class FXML_PembayaranController implements Initializable {

    @FXML
    private AnchorPane BayarPane;
    @FXML
    private TextField txtSubtotal;
    @FXML
    private Button btnCetak;
    @FXML
    private Button btnBatal;
    @FXML
    private TableView<BarangModel> tbvBayar;
    @FXML
    private TextField txtDiskon;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField txtPPN;
    @FXML
    private TextField txtBayar;
    @FXML
    private CheckBox cbMember;

    private boolean isMember = false;
    private final double discMember = 0.05;
    private final double PPN = 0.11;
    private double disc, subtotal, total, ppn, bayar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        kalkuDiskon();
        setTabel();
        kalkuBayar();
    }    

    @FXML
    private void cetakKlik(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cetak Struk");
        alert.setHeaderText("Cetak Struk Belanja");
        alert.setContentText("Sudah yakin dengan belanjaannya? Data pembelian tidak dapat diedit lagi sewaktu struk sudah dicetak");
        
        if(alert.showAndWait().get() == ButtonType.OK){
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Struk.fxml"));
            Parent root = (Parent)loader.load();
            FXML_StrukController isidt= (FXML_StrukController)loader.getController();
            isidt.transfer(subtotal, disc, total, ppn, bayar);
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.show();
            btnBatal.getScene().getWindow().hide();
        } catch (IOException e){
            e.printStackTrace();
        }
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
    
    @FXML
    private void addDiskonMember(ActionEvent event) {
        if(cbMember.isSelected()) disc += discMember * subtotal;
        else disc -= discMember * subtotal;
        kalkuBayar();
    }
    
    
    /**
     * Diasumsikan:
     *  1. Jumlah pembelian diatas 10 per itemnya dapet diskon sesuai keterangan
     *  2. Sesuai dengan lagu, belanja diatas 75rb dapet diskon lagi senilai 5%
     *      dari total item
     *  3. Member juga dapet diskon 5% spesialll dari total item
     */
    private void kalkuDiskon(){
        double tmp;
        for(BarangModel dis: keranjang){
            subtotal += dis.getSub();
            if(dis.getJumlah() > 10) {
                tmp = dis.getDiskon() * dis.getSub();
                tmp = Math.round(tmp*10)/10;
                dis.setDiskon(tmp);
                dis.setSub((int) (dis.getSub()-dis.getDiskon()));
                disc += tmp; 
            }
            if(dis.getDiskon() < 1){
                dis.setDiskon(0);
            }
        }
        if(subtotal > 75000) disc += 0.05 * subtotal;
    }
    
    private void setTabel(){
        if(!keranjang.isEmpty()){
            tbvBayar.getColumns().clear();
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
            tbvBayar.getColumns().addAll(col);
            col = new TableColumn("Nama Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, String>("namaBarang"));
            tbvBayar.getColumns().addAll(col);
            col = new TableColumn("Harga Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("harga"));
            tbvBayar.getColumns().addAll(col);
            col = new TableColumn("Jumlah");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("jumlah"));
            tbvBayar.getColumns().addAll(col);
            col = new TableColumn("Diskon");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Double>("diskon"));
            tbvBayar.getColumns().addAll(col);
            col = new TableColumn("Subtotal Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("sub"));
            tbvBayar.getColumns().addAll(col);
            
            tbvBayar.setItems(keranjang);

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Jenis Barang Kosong", ButtonType.OK);
            a.showAndWait();
            //tbvBayar.getScene().getWindow().hide();
        }
    }
    
    private void kalkuBayar(){
        total = subtotal - disc;
        ppn = PPN * total;
        bayar = total + ppn;
        
        txtSubtotal.setText(String.valueOf(subtotal));
        txtDiskon.setText(String.valueOf(disc));
        txtTotal.setText(String.valueOf(total));
        txtPPN.setText(String.valueOf(ppn));
        txtBayar.setText(String.valueOf(bayar));
    }
}
