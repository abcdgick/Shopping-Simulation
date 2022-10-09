/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package uts_2020130017;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class UTS_2020130017 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        //String css = this.getClass().getResource("Style.css").toExternalForm();
        //scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.show();
        
         stage.setOnCloseRequest(event -> {
            event.consume();
            quit(stage);
        });
    }

    private void quit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Anda akan keluar dari Form Belanja Alfamart");
        alert.setContentText("Yakin mau berhenti belanja?");
        
        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Siap");
            stage.close();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
