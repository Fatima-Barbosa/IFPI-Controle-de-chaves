package application7;

import Classes.Operador;
import ModelDAO.OperadorDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Fátima
 */
public class Tela_cad_operadores_Controller extends BaseController implements Initializable {

    OperadorDAO dao = new OperadorDAO();

    @FXML
    private TableView<Operador> tabelaOperadores;
    @FXML
    private TableColumn<Operador, String> ColNome;
    @FXML
    private TableColumn<Operador, String> ColLogin;
    @FXML
    private TableColumn<Operador, String> ColNivel;
    @FXML
    private TableColumn<Operador, String> ColID;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtLogin;
    @FXML
    private Button btnSalvar;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private ComboBox<String> BoxNivel;
    @FXML
    private Button btnSair;
    
    private ObservableList<Operador> Data
            = FXCollections.observableArrayList();

    String n[] = {"1", "2"};
    //private List<String> nivel = new ArrayList<>(n);
    @FXML
    private MenuItem CtextExcluir;
    @FXML
    private MenuItem ctextEditar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        encherCombobox();

        ColNome.setCellValueFactory(cellData -> cellData.getValue().getNome());
        ColLogin.setCellValueFactory(cellData -> cellData.getValue().getLogin());
        ColNivel.setCellValueFactory(cellData -> cellData.getValue().getTipo());
        ColID.setCellValueFactory(cellData -> cellData.getValue().getId().asString());
        atualizarTabel();
        assert tabelaOperadores != null : "fx:id=\"tabelaOperadores\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert ColNome != null : "fx:id=\"ColNome\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert ColLogin != null : "fx:id=\"ColLogin\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert ColNivel != null : "fx:id=\"ColNivel\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert ColID != null : "fx:id=\"ColID\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert txtLogin != null : "fx:id=\"txtLogin\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert btnSalvar != null : "fx:id=\"btnSalvar\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert txtSenha != null : "fx:id=\"txtSenha\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert BoxNivel != null : "fx:id=\"BoxNivel\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'tela_cad_operadores.fxml'.";

    }

    public void encherCombobox() {
        ObservableList<String> obs;
        obs = FXCollections.observableArrayList(n);
        BoxNivel.setItems(obs);
    }

    @FXML
    private void OnSalvar(ActionEvent event) {
        if (txtNome.getText().equals("") && txtLogin.getText().equals("") && txtSenha.getText().equals("") && BoxNivel.getSelectionModel().getSelectedItem() == null) {
            System.out.println("erro");
            Alert dialogo1 = new Alert(Alert.AlertType.ERROR);
            dialogo1.setTitle("Erro");
            dialogo1.setContentText("Campos vazios!");
            dialogo1.showAndWait();
        } else {
            Operador o = new Operador(txtNome.getText(), txtLogin.getText(), txtSenha.getText(), BoxNivel.getValue());
            dao.adicionar(o);
            atualizarTabel();
        }
    }

    public void atualizarTabel(){
        try {
            Data = dao.gerarLista();
            tabelaOperadores.setItems(Data);
        } catch (SQLException ex) {
            Logger.getLogger(Tela_cad_operadores_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void OnSair(ActionEvent event) throws IOException {
        navigate(event, FXMLLoader.load(getClass().getResource("FXML1.fxml")));
    }

    @FXML
    private void ConTextEcluir(ActionEvent event) {
        int id = tabelaOperadores.getSelectionModel().getSelectedItem().getId().intValue();
        try {
            dao.remover(id);
            atualizarTabel();
        } catch (SQLException ex) {
            Logger.getLogger(Tela_cad_operadores_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ConTextEditar(ActionEvent event) {
        txtNome.setText(tabelaOperadores.getSelectionModel().getSelectedItem().getNome().getValue());
        txtLogin.setText(tabelaOperadores.getSelectionModel().getSelectedItem().getLogin().getValue());
        
    }

}
