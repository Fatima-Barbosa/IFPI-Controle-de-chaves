package Controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Fátima
 */
public class MenuController extends LoginController implements Initializable {


    @FXML
    private JFXButton BTN_Home;

    @FXML
    private JFXButton BTN1;

    @FXML
    private JFXButton BTNUsers;

    @FXML
    private JFXButton btnRelatorio;

    @FXML
    private JFXButton BTNoperadores;

    @FXML
    private JFXButton btnSair;

    @FXML
    private AnchorPane anchorPaneMae;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        assert BTN_Home != null : "fx:id=\"BTN_Home\" was not injected: check your FXML file 'Menu.fxml'.";
        assert BTN1 != null : "fx:id=\"BTN1\" was not injected: check your FXML file 'Menu.fxml'.";
        assert BTNUsers != null : "fx:id=\"BTNUsers\" was not injected: check your FXML file 'Menu.fxml'.";
        assert btnRelatorio != null : "fx:id=\"btnRelatorio\" was not injected: check your FXML file 'Menu.fxml'.";
        assert BTNoperadores != null : "fx:id=\"BTNoperadores\" was not injected: check your FXML file 'Menu.fxml'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'Menu.fxml'.";
        assert anchorPaneMae != null : "fx:id=\"anchorPaneMae\" was not injected: check your FXML file 'Menu.fxml'.";

    }

    @FXML
    private void ON_Home() throws IOException {
        AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
        anchorPaneMae.getChildren().setAll(pane);
    }

    @FXML
    void onRelatorios(ActionEvent event) throws IOException {
//        navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/EspelhoRelatorio.fxml")));     
        AnchorPane relatorio = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/EspelhoRelatorio.fxml"));
        anchorPaneMae.getChildren().setAll(relatorio);
    }

    @FXML
    void ONKeys(ActionEvent event) throws IOException {
//        navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/Chaves.fxml")));
        AnchorPane chaves = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Chaves.fxml"));
        anchorPaneMae.getChildren().setAll(chaves);
    }

    @FXML
    void ONoperadores() throws IOException {
        AnchorPane op = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/Tela_cad_operadores.fxml"));
        anchorPaneMae.getChildren().setAll(op);
//        navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/Tela_cad_operadores.fxml")));
    }

    @FXML
    void ONusuarios(ActionEvent event) throws IOException {
//        navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/CadastroFuncionarios.fxml")));
        AnchorPane funk = (AnchorPane) FXMLLoader.load(getClass().getResource("/View/CadastroFuncionarios.fxml"));
        anchorPaneMae.getChildren().setAll(funk);
    }

    @FXML
    void onSair(ActionEvent event) throws IOException {
        navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/Login.fxml")));
    }
    
}
