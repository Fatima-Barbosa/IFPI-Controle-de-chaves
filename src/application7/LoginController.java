package application7;

import ModelDAO.ChavePegaDAO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Fátima
 */
public class LoginController extends BaseController implements Initializable {

    ChavePegaDAO daoCP = new ChavePegaDAO();

    @FXML
    private TextField labelLogin;

    @FXML
    private TextField labelSenha;

    @FXML
    private JFXButton btnEntrarSuper;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        verificar();
        assert labelLogin != null : "fx:id=\"labelLoguin\" was not injected: check your FXML file 'loguinSuperUser.fxml'.";
        assert labelSenha != null : "fx:id=\"labelSenha\" was not injected: check your FXML file 'loguinSuperUser.fxml'.";
        assert btnEntrarSuper != null : "fx:id=\"btnEntrarSuper\" was not injected: check your FXML file 'loguinSuperUser.fxml'.";
    }

    String loginp = null;
    String senhap = null;
//    Date d = new Data.
//    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
    Date date = new Date();

    private void verificar() {
        if (dateFormat.format(date).equals("01")) {
            daoCP.removerDados();
            Alert dialogo = new Alert(Alert.AlertType.INFORMATION);

            dialogo.setTitle("Dados excluidos");
            dialogo.setHeaderText("Operaçao bem sucedida!");
            dialogo.setContentText("Todos os registros de chaves pegas foram excluidos!");
            dialogo.showAndWait();
        }
    }

    @FXML
    private void entrar(ActionEvent event) throws IOException {
        if (labelLogin.getText().equals("root") && labelSenha.getText().equals("root")) {
            navigate(event, FXMLLoader.load(getClass().getResource("FXML1.fxml")));
        } else if (labelLogin.getText().equals("root1") && labelSenha.getText().equals("root1")) {
            navigate(event, FXMLLoader.load(getClass().getResource("teste.fxml")));
        }

    }

}
