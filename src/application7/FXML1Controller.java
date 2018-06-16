package application7;

import Classes.ChavePega;
import Classes.Users;
import Classes.keys;
import ModelDAO.ChavePegaDAO;
import ModelDAO.ChavesDAO;
import ModelDAO.UsersDAO;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Fátima
 */
public class FXML1Controller extends BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private final ChavesDAO keysDao = new ChavesDAO();
    private final UsersDAO userDao = new UsersDAO();
    private final ChavePegaDAO cdao = new ChavePegaDAO();

    @FXML
    private TableView<ChavePega> tabelaChavesUsadas;

    @FXML
    private TableView<keys> tabelaChaves;

    @FXML
    private TableColumn<keys, String> colSala;

    @FXML
    private TableColumn<keys, String> colDescricao;

    @FXML
    private TableColumn<keys, Boolean> colID;

    //Tabela dos Usuários**********
    @FXML
    private TableView<Users> tabelaUsuarios;

    @FXML
    private TableColumn<Users, String> collNome;

    @FXML
    private TableColumn<Users, String> collCode;

    @FXML
    private TableColumn<Users, String> collID;
    //*****************************
    @FXML
    private JFXButton BTNoperadores;

    private ImageView imagen;

    @FXML
    private JFXButton BTN1;

    @FXML
    private JFXButton BTNUsers;

    @FXML
    private JFXButton btnRelatorio;

    @FXML
    private JFXButton btnSair;

    @FXML
    private TextField buscKey;

    @FXML
    private Button BTNbuscKey;

    @FXML
    private TextField UserBusc;

    @FXML
    private Button btnBuscUser;

    private ObservableList<keys> Data
            = FXCollections.observableArrayList();

    private ObservableList<Users> Data2
            = FXCollections.observableArrayList();

    private ObservableList<ChavePega> Datacp
            = FXCollections.observableArrayList();
    @FXML
    private TableColumn<ChavePega, String> colUser;
    @FXML
    private TableColumn<ChavePega, String> colAluno;
    @FXML
    private TableColumn<ChavePega, String> colHora;
    @FXML
    private TableColumn<ChavePega, String> colSalaP;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXButton btnApagarDados;

    @FXML
    void buscKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Data = keysDao.filtrarList(buscKey.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FXML1Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabelaChaves.setItems(Data);
        }
    }

    @FXML
    void busckey(ActionEvent event) {
        try {
            Data = keysDao.filtrarList(buscKey.getText());
        } catch (SQLException ex) {
            Logger.getLogger(FXML1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelaChaves.setItems(Data);
    }

    @FXML
    void onRelatorios(ActionEvent event) throws IOException {
        navigate(event, FXMLLoader.load(getClass().getResource("EspelhoRelatorio.fxml")));
    }

    @FXML
    void BTNBuscUser(ActionEvent event) {
        try {
            Data2 = userDao.FiltrarList(UserBusc.getText());
        } catch (SQLException ex) {
            Logger.getLogger(FXML1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelaUsuarios.setItems(Data2);
    }

    @FXML
    void BuscUser(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Data2 = userDao.FiltrarList(UserBusc.getText());
            } catch (SQLException ex) {
                Logger.getLogger(FXML1Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabelaUsuarios.setItems(Data2);
        }
    }

    @FXML
    void ONKeys(ActionEvent event) throws IOException {
        navigate(event, FXMLLoader.load(getClass().getResource("Chaves.fxml")));
    }

    void ONconfig(ActionEvent event) {

    }

    @FXML
    void ONoperadores(ActionEvent event) throws IOException {
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("tela_cad_operadores.fxml"));
//        anchorPane.getChildren().setAll(a);
        navigate(event, FXMLLoader.load(getClass().getResource("Tela_cad_operadores.fxml")));
    }

    @FXML
    void ONusuarios(ActionEvent event) throws IOException {
        navigate(event, FXMLLoader.load(getClass().getResource("CadastroFuncionarios.fxml")));
    }

    @FXML
    void onSair(ActionEvent event) throws IOException {
        navigate(event, FXMLLoader.load(getClass().getResource("Login.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Tabela chaves*****************************
        colSala.setCellValueFactory(collData -> collData.getValue().getSala());
        colDescricao.setCellValueFactory(collData -> collData.getValue().getDescricao());
        colID.setCellValueFactory(collData -> collData.getValue().getPega());

        try {
            Data = keysDao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelaChaves.setItems(Data);
        //*****************************************
        //Tabela Users*****************************
        collNome.setCellValueFactory(cellData -> cellData.getValue().getNomeUser());
        collCode.setCellValueFactory(cellData -> cellData.getValue().getCpf());
        collID.setCellValueFactory(cellData -> cellData.getValue().getId().asString());

        try {
            Data2 = userDao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelaUsuarios.setItems(Data2);
        //*****************************************
        //Tabela Chaves em uso*********************
        colAluno.setCellValueFactory(cellData -> cellData.getValue().getAluno());
        colHora.setCellValueFactory(cellData -> cellData.getValue().getHorad());
        colUser.setCellValueFactory(cellData -> cellData.getValue().getUser());
        colSalaP.setCellValueFactory(cellData -> cellData.getValue().getChave());

        try {
            Datacp = cdao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(FXML1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelaChavesUsadas.setItems(Datacp);
        // TODO        
        assert imagen != null : "fx:id=\"imagen\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert BTN1 != null : "fx:id=\"BTN1\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert BTNUsers != null : "fx:id=\"BTNUsers\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert btnRelatorio != null : "fx:id=\"btnRelatorio\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert BTNoperadores != null : "fx:id=\"BTNoperadores\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert tabelaChavesUsadas != null : "fx:id=\"tabelaChavesUsadas\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert colUser != null : "fx:id=\"colUser\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert colSalaP != null : "fx:id=\"colSalaP\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert colAluno != null : "fx:id=\"colAluno\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert colHora != null : "fx:id=\"colHora\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert tabelaChaves != null : "fx:id=\"tabelaChaves\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert colSala != null : "fx:id=\"colSala\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert colDescricao != null : "fx:id=\"colDescricao\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert colID != null : "fx:id=\"colID\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert buscKey != null : "fx:id=\"buscKey\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert BTNbuscKey != null : "fx:id=\"BTNbuscKey\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert UserBusc != null : "fx:id=\"UserBusc\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert btnBuscUser != null : "fx:id=\"btnBuscUser\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert tabelaUsuarios != null : "fx:id=\"tabelaUsuarios\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert collNome != null : "fx:id=\"collNome\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert collCode != null : "fx:id=\"collCode\" was not injected: check your FXML file 'FXML1.fxml'.";
        assert collID != null : "fx:id=\"collID\" was not injected: check your FXML file 'FXML1.fxml'.";
    }

    @FXML
    private void onApagarDados(ActionEvent event) {
        boolean r = false;
        String n;

        Alert dialogo1 = new Alert(AlertType.CONFIRMATION);
        dialogo1.setTitle("Atenção");
        dialogo1.setHeaderText("Deseja realmente apagar todo o historico?");
        dialogo1.getButtonTypes().add(ButtonType.YES);
        ButtonType btn = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType btn1 = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialogo1.getButtonTypes().setAll(btn, btn1);
//        dialogo1.showAndWait().ifPresent(b -> {
//            if(b == btn){
//                //r = true;
////                n = "";
//            }
//        });
        r = dialogo1.showAndWait().get().equals(btn);
        System.out.println(""+r);
        //.getClass().equals(btn);
        //System.out.println(""+n);
        if (r == true) {
            cdao.removerDados();
            Alert dialogo = new Alert(Alert.AlertType.INFORMATION);

            dialogo.setTitle("Dados excluidos");
            dialogo.setHeaderText("Operaçao bem sucedida!");
            dialogo.setContentText("Todos os registros de chaves pegas foram excluidos!");
            dialogo.showAndWait();
        }

    }

}
