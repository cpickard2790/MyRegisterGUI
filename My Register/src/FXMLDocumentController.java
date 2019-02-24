/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**

 @author cpick_000
 */
public class FXMLDocumentController implements Initializable
{
    @FXML
    private AnchorPane rootPane;

    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleOpenButton(ActionEvent event) throws IOException
    {
        AnchorPane fxml2 = FXMLLoader.load(getClass().getResource("RegisterTable.fxml"));
        rootPane.getChildren().setAll(fxml2);
    }

    @FXML
    private void handleQuitButton(ActionEvent event)
    {
        Platform.exit(); 
    }
    
}
