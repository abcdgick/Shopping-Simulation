/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package uts_2020130017;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane FormPane;
    @FXML
    private MediaView mediaView;
    @FXML
    private ChoiceBox<String> chbJenis;
    @FXML
    private TextField txtSubtotal;
    @FXML
    private TextField txtJumlah;
    @FXML
    private TextField txtHarga;
    @FXML
    private Button btnBayar;
    @FXML
    private Button btnTambah;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnKeluar;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnPilih;
    @FXML
    private Button btnBatalPilih;
    @FXML
    private Button btnAdmin;
    @FXML
    private TableView<BarangModel> tbvBarang;
    
    public static Media music;
    public static MediaPlayer mediaPlayer;
    public static ObservableList<BarangModel> keranjang = FXCollections.observableArrayList();
    
    final private String path ="src/uts_2020130017/Assets/";
    public static ArrayList<BarangModel> all = new ArrayList<>();
    private ObservableList<BarangModel> tampil = FXCollections.observableArrayList();
    private final ArrayList<String> jenis = new ArrayList<>();
    private BarangModel disBarang = null;
    public static int index = -1;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playAudio();
        masukinData();
        initChBox();
    }    

    @FXML
    private void bayarKlik(ActionEvent event) {
        if(keranjang.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Mohon isikan keranjang belanja anda terlebih dahulu", ButtonType.OK);
            a.showAndWait();
        } else{
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Pembayaran.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
        }
    }

    @FXML
    private void tambahKlik(ActionEvent event) {
        if(disBarang == null && index < 0){
                Alert a = new Alert(Alert.AlertType.ERROR,"Mohon pilih barang yang ingin anda beli", ButtonType.OK);
                a.showAndWait();
        } else{
            try {
                int jumlah = Integer.parseInt(txtJumlah.getText());
                if(jumlah > 0){
                    if(index >= 0){
                        keranjang.get(index).setJumlah(jumlah);
                        keranjang.get(index).setSub(disBarang.getJumlah() * disBarang.getHarga());
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, disBarang.getNamaBarang()+
                                " sebanyak " + disBarang.getJumlah()+
                                " berhasil diupdate", ButtonType.OK);
                        a.showAndWait();
                        batalPilihKlik(event);
                    } else{
                        disBarang.setJumlah(jumlah);
                        disBarang.setSub(disBarang.getJumlah() * disBarang.getHarga());
                        keranjang.add(disBarang);
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, disBarang.getNamaBarang()+
                                " sebanyak " + disBarang.getJumlah()+
                                " berhasil ditambahkan ke keranjang", ButtonType.OK);
                        a.showAndWait();
                        batalPilihKlik(event);
                    }
                } else{
                    Alert a = new Alert(Alert.AlertType.ERROR,"Jumlah pembelian tidak valid!", ButtonType.OK);
                    a.showAndWait();
                }
            } catch(NumberFormatException e){
                Alert a = new Alert(Alert.AlertType.ERROR,"Mohon isikan jumlah pembelian dengan benar", ButtonType.OK);
                a.showAndWait();
                txtJumlah.requestFocus();
            }
        }
    }

    @FXML
    private void editKlik(ActionEvent event) {
        if(keranjang.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR, "Anda belum menaruh barang ke keranjang!", ButtonType.OK);
            a.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_ListPembelian.fxml"));
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            
            String css = this.getClass().getResource("Style.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
            System.out.println(index);
            if(index >= 0){
                prepareEdit(event);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void resetKlik(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Keranjang");
        alert.setHeaderText("Anda yakin ingin melakukan reset keranjang?");
        alert.setContentText("Perintah ini bersifat permanen");
        
        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Siap");
            keranjang.clear();
        }
        batalPilihKlik(event);
    }
    
    @FXML
    private void keluarKlik(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Anda akan keluar dari Form Belanja Alfamart");
        alert.setContentText("Yakin mau berhenti belanja?");
        
        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Siap");
            System.exit(0);
        }
    }


    @FXML
    private void pilihKlik(ActionEvent event) {
        try {
            batalPilihKlik(event);
            disBarang = tbvBarang.getSelectionModel().getSelectedItem();
            txtHarga.setText(String.valueOf(disBarang.getHarga()));
            txtJumlah.setEditable(true);
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Anda belum memilih barang!", ButtonType.OK);
            a.showAndWait();
        }
    }
    
    @FXML
    private void adminKlik(ActionEvent event) {
        try {
            batalPilihKlik(event);
            disBarang = tbvBarang.getSelectionModel().getSelectedItem();
            if(disBarang == null) {
                Alert a = new Alert(Alert.AlertType.ERROR,"Anda belum memilih barang!", ButtonType.OK);
                a.showAndWait();
            } else {
                TextInputDialog tx = new TextInputDialog();
                tx.setTitle("Login Admin");
                tx.setContentText("Password Admin: ");
                tx.showAndWait();
                if(tx.getResult() != null){
                    if(tx.getResult().equals("alfa123")){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_UbahHarga.fxml"));
                        Parent root = (Parent)loader.load();
                        FXML_UbahHargaController isidt = (FXML_UbahHargaController)loader.getController();
                        isidt.ubah(disBarang);

                        Scene scene = new Scene(root);
                        Stage stg = new Stage();
                        String css = this.getClass().getResource("Style.css").toExternalForm();
                        scene.getStylesheets().add(css);

                        stg.initModality(Modality.APPLICATION_MODAL);
                        stg.setResizable(false);
                        stg.setIconified(false);
                        stg.setScene(scene);
                        stg.showAndWait();
                        setTabel(event);
                    } else{
                        Alert a = new Alert(Alert.AlertType.ERROR,"Password Admin Salah!", ButtonType.OK);
                        a.showAndWait();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void batalPilihKlik(ActionEvent event) {
        disBarang = null;
        txtHarga.clear();
        txtJumlah.clear();
        txtJumlah.setEditable(false);
        txtSubtotal.clear();
        index = -1;
    }
    
    //bakal aktif ketika kita teken enter di textfield jumlah
    @FXML
    private void hitungSubTotal(ActionEvent event) {
        try {
            int jumlah = Integer.parseInt(txtJumlah.getText());
            if(jumlah < 1){
                Alert a = new Alert(Alert.AlertType.ERROR,"Jumlah pembelian tidak valid!", ButtonType.OK);
                a.showAndWait();
            } else {
            txtSubtotal.setText(String.valueOf(disBarang.getHarga() * Integer.parseInt(txtJumlah.getText())));
            }
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Mohon isikan jumlah pembelian dengan benar", ButtonType.OK);
            a.showAndWait();
        }
    }
    
    private void prepareEdit(ActionEvent event){
        disBarang = keranjang.get(index);
        txtHarga.setText(String.valueOf(disBarang.getHarga()));
        txtJumlah.setEditable(true);
        txtJumlah.setText(String.valueOf(disBarang.getJumlah()));
        txtSubtotal.setText(String.valueOf(disBarang.getSub()));
        
        chbJenis.setValue(disBarang.getJenis());
        setTabel(event);
    }
    
    private void playAudio(){
        music = new Media(getClass().getResource("Sound.mp4").toExternalForm()); 
        
        mediaPlayer = new MediaPlayer(music);
        
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
    }
    
    private void masukinData(){
        jenis.add("Dapur");
        jenis.add("Makanan");
        jenis.add("Mandi");
        jenis.add("Minuman");
        
        //String IDBarang, String namaBarang, String jenis, 
        //Image image, int harga, int jumlah, int stok, double diskon
        all.add(new BarangModel("BE01", "Beras Alfamart", "Dapur", getImage("Dapur", "Beras"), 64000, 0, 45, 0.1));
        all.add(new BarangModel("GA01", "Garam Alfamart", "Dapur", getImage("Dapur", "Garam"), 8500, 0, 167, 0.11));
        all.add(new BarangModel("GU01", "Gula Gulaku", "Dapur", getImage("Dapur", "Gula"), 13000, 0, 212, 0.11));
        all.add(new BarangModel("KE01", "Kecap Bango", "Dapur", getImage("Dapur", "Kecap"), 30000, 0, 189, 0.15));
        all.add(new BarangModel("KO01", "Bumbu Masak Kokita", "Dapur", getImage("Dapur", "Kokita"), 12000, 0, 121, 0.1));
        all.add(new BarangModel("MI01", "Sasa Penyedap Rasa", "Dapur", getImage("Dapur", "Micin"), 10500, 0, 153, 0.15));
        all.add(new BarangModel("MI02", "Minyak Goreng Tropicana", "Dapur", getImage("Dapur", "Minyak"), 35000, 0, 88, 0.05));
        all.add(new BarangModel("SA01", "Saos Sambal ABC", "Dapur", getImage("Dapur", "Sambel"), 15000, 0, 91, 0.2));
        all.add(new BarangModel("TE01", "Telur", "Dapur", getImage("Dapur", "Telur"), 32000, 0, 25, 0.05));
        all.add(new BarangModel("TO01", "Saos Tomat Del Monte", "Dapur", getImage("Dapur", "Tomat"), 17000, 0, 79, 0.2));
        
        all.add(new BarangModel("CH01", "Chitato", "Makanan", getImage("Makanan", "Chitato"), 24000, 0, 132, 0.15));
        all.add(new BarangModel("IN01", "Indomie Goreng", "Makanan", getImage("Makanan", "Indomie"), 3200, 0, 158, 0.2));
        all.add(new BarangModel("OR01", "Oreo", "Makanan", getImage("Makanan", "Oreo"), 15000, 0, 56, 0.15));
        all.add(new BarangModel("PO01", "PotatoBee", "Makanan", getImage("Makanan", "PotatoBee"), 27000, 0, 82, 0.15));
        all.add(new BarangModel("SI01", "Silverqueen", "Makanan", getImage("Makanan", "Silverqueen"), 23000, 0, 64, 0.1));
        all.add(new BarangModel("TA01", "Tango", "Makanan", getImage("Makanan", "Tango"), 18000, 0, 52, 0.1));
        
        all.add(new BarangModel("CL01", "Clear", "Mandi", getImage("Mandi", "Clear"), 28000, 0, 57, 0.18));
        all.add(new BarangModel("HS01", "Head&Shoulders", "Mandi", getImage("Mandi", "Head&Shoulders"), 33000, 0, 45, 0.17));
        all.add(new BarangModel("LI01", "Lifebuoy", "Mandi", getImage("Mandi", "Lifebuoy"), 7800, 0, 138, 0.2));
        all.add(new BarangModel("PE01", "Pepsodent", "Mandi", getImage("Mandi", "Pepsodent"), 24000, 0, 99, 0.2));
        all.add(new BarangModel("RE01", "Rexona", "Mandi", getImage("Mandi", "Rexona"), 23000, 0, 88, 0.1));
        all.add(new BarangModel("WA01", "Watsons", "Mandi", getImage("Mandi", "Watsons"), 43000, 0, 29, 0.25));
        
        all.add(new BarangModel("CI01", "Cimory", "Minuman", getImage("Minuman", "Cimory"), 8300, 0, 78, 0.16));
        all.add(new BarangModel("FA01", "Fanta", "Minuman", getImage("Minuman", "Fanta"), 6300, 0, 92, 0.14));
        all.add(new BarangModel("FR01", "FruitTea", "Minuman", getImage("Minuman", "FruitTea"), 7800, 0, 89, 0.12));
        all.add(new BarangModel("MI03", "Mizone", "Minuman", getImage("Minuman", "Mizone"), 7200, 0, 65, 0.15));
        all.add(new BarangModel("NU01", "Nutriboost", "Minuman", getImage("Minuman", "Nutriboost"), 8500, 0, 78, 0.15));
        all.add(new BarangModel("PO02", "Pocari Sweat", "Minuman", getImage("Minuman", "Pocari"), 8100, 0, 55, 0.13));
    }
 
    private Image getImage(String jenis, String nama){
        File file = new File(path+jenis+"/"+nama+".png");
        Image image = new Image(file.toURI().toString());
        return image;
    }
    
    private void initChBox(){
        chbJenis.getItems().addAll(jenis);
        chbJenis.setOnAction(this::setTabel);
    }
    
    private void setTabel(ActionEvent event){
        pilah();
        
        if(!tampil.isEmpty()){
            
            tbvBarang.getColumns().clear();
            
            //ok i don't know wtf happened, but this shit just
            //straight up erased tampil, idk why
            //tbvBarang.getItems().clear();
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
            tbvBarang.getColumns().addAll(col);
            col = new TableColumn("Nama Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, String>("namaBarang"));
            tbvBarang.getColumns().addAll(col);
            col = new TableColumn("Harga Barang");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("harga"));
            tbvBarang.getColumns().addAll(col);
            col = new TableColumn("Stok");
            col.setCellValueFactory(new PropertyValueFactory<BarangModel, Integer>("stok"));
            tbvBarang.getColumns().addAll(col);
            
            tbvBarang.setItems(tampil);

        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Jenis Barang Kosong", ButtonType.OK);
            a.showAndWait();
            //tbvBarang.getScene().getWindow().hide();
        }
    }
    
    private void pilah(){
        tampil.clear();
        for (BarangModel dis: all){
            if(dis.getJenis().equals(chbJenis.getValue())){
                tampil.add(dis);
            }
        }
    }

}
