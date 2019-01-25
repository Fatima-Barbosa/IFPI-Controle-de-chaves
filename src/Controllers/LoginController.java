package Controllers;

import ModelDAO.ChavePegaDAO;
import ModelDAO.OperadorDAO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Fátima
 */
public class LoginController implements Initializable{

    ChavePegaDAO daoCP = new ChavePegaDAO();
    OperadorDAO odao = new OperadorDAO();

    public static long id;
    
    @FXML
    private TextField labelLogin;

    @FXML
    private TextField labelSenha;

    @FXML
    private JFXButton btnEntrarSuper;

    /**
     * Initializes the controller class. 
     * @param url
     * @param rb
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //    verificar();
                assert btnEntrarSuper != null : "fx:id=\"btnEntrarSuper\" was not injected: check your FXML file 'Login.fxml'.";
        assert labelLogin != null : "fx:id=\"labelLogin\" was not injected: check your FXML file 'Login.fxml'.";
        assert labelSenha != null : "fx:id=\"labelSenha\" was not injected: check your FXML file 'Login.fxml'.";
    }

    @FXML
    private void entrar(ActionEvent event) throws IOException {
        
        switch (odao.checkLogin(labelLogin.getText(), labelSenha.getText())) {
            case "1":
                navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/Menu.fxml")));
                break;
            case "2":
                setId(odao.RetornarID(labelLogin.getText(), labelSenha.getText()));
                navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/UserPadrao.fxml")));
                break;
            case "sn":
                Alert dialogo1 = new Alert(Alert.AlertType.WARNING);
                dialogo1.setTitle("Usuário invalido");
                dialogo1.setContentText("Usuário invalido");
                dialogo1.showAndWait();
                break;
            default:
                Alert dialogo2 = new Alert(Alert.AlertType.WARNING);
                dialogo2.setTitle("Usuário invalido");
                break;
        }

    }

    @FXML
    private void mover(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println("aqui");
            labelSenha.requestFocus();

        }
    }

    @FXML
    private void on_senha(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            switch (odao.checkLogin(labelLogin.getText(), labelSenha.getText())) {
            case "1":
                navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/Menu.fxml")));
                break;
            case "2":
                System.out.println("id: "+odao.RetornarID(labelLogin.getText(), labelSenha.getText()));
                setId(odao.RetornarID(labelLogin.getText(), labelSenha.getText()));                
                navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/UserPadrao.fxml")));
                break;
            case "sn":
                Alert dialogo1 = new Alert(Alert.AlertType.WARNING);
                dialogo1.setTitle("Usuário invalido");
                dialogo1.setContentText("Usuário invalido");
                dialogo1.showAndWait();
                break;
            default:
                Alert dialogo2 = new Alert(Alert.AlertType.WARNING);
                dialogo2.setTitle("Usuário invalido");
                break;
        }
        }
    }
    
    public long retormarid(){
        return id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void navigateTeste(Event event, Parent pageParent) throws IOException {
       
        //Creating new scene
        Scene scene = new Scene(pageParent);
        
        //get current stage
        Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        //Hide old stage
        appStage.hide(); // Optional
        
        //Set stage with new Scene
        appStage.setScene(scene);
        
        //Show up the stage
        appStage.show();
    }
    
}
