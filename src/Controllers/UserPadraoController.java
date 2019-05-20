package Controllers;

import Classes.ChavePega;
import Classes.Users;
import ModelDAO.ChavePegaDAO;
import ModelDAO.UsersDAO;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * FXML Controller class
 *
 * @author Fátima
 */
public class UserPadraoController extends LoginController implements Initializable {

    Users u = new Users();
    UsersDAO dao = new UsersDAO();
//    ChavePega c = new ChavePega();
    ChavePegaDAO cdao = new ChavePegaDAO();

    LoginController lg = new LoginController();

    @FXML
    private TableView<Users> tabelaViewUsers;
    @FXML
    private TableColumn<Users, String> colUsers;
    @FXML
    private TableColumn<Users, String> colCode;

    private TableColumn<Users, String> colID;

    private ObservableList<Users> Data
            = FXCollections.observableArrayList();

    public static ObservableList<ChavePega> DataChaves = FXCollections.observableArrayList();

    @FXML
    public TableView<ChavePega> tabelaChavesEmUso;

    @FXML
    private TableColumn<ChavePega, String> colChave;
    @FXML
    private TableColumn<ChavePega, String> colUsuario;
    @FXML
    private TableColumn<ChavePega, String> colDevolucao;
    @FXML
    private TableColumn<ChavePega, Button> colBTNdevolver;
    @FXML
    private TextField txtbusc;
    @FXML
    private Button btnBuscUsers;
    @FXML
    private TextField txtUser;
    @FXML
    public ComboBox<String> labs;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private TextField txtAluno;
    @FXML
    private JFXTimePicker txtHora;
    @FXML
    private ImageView imgDevolver;
    @FXML
    private Button btnSair;
    @FXML
    private MenuItem conDevolver;

    public static ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Users
        colUsers.setCellValueFactory(cellData -> cellData.getValue().getNomeUser());
        colCode.setCellValueFactory(cellData -> cellData.getValue().getCpf());

        //Keys
        colChave.setCellValueFactory(cellData -> cellData.getValue().getChave());
        colUsuario.setCellValueFactory(cellData -> cellData.getValue().getUser());
        colDevolucao.setCellValueFactory(cellData -> cellData.getValue().getHorad());
        colBTNdevolver.setCellValueFactory(new PropertyValueFactory<>("button"));
        cols();

        try {
            Data = dao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        carregarChaves();
        tabelaViewUsers.setItems(Data);
        labs.setItems(list);

        assert txtUser != null : "fx:id=\"txtUser\" was not injected: check your FXML file 'teste.fxml'.";
        assert tabelaChavesEmUso != null : "fx:id=\"tabelaChavesEmUso\" was not injected: check your FXML file 'teste.fxml'.";
        assert colChave != null : "fx:id=\"colChave\" was not injected: check your FXML file 'teste.fxml'.";
        assert colUsuario != null : "fx:id=\"colUsuario\" was not injected: check your FXML file 'teste.fxml'.";
        assert colDevolucao != null : "fx:id=\"colDevolucao\" was not injected: check your FXML file 'teste.fxml'.";
        assert colBTNdevolver != null : "fx:id=\"colBTNdevolver\" was not injected: check your FXML file 'UserPadrao.fxml'.";
        assert txtAluno != null : "fx:id=\"txtAluno\" was not injected: check your FXML file 'teste.fxml'.";
        assert labs != null : "fx:id=\"labs\" was not injected: check your FXML file 'teste.fxml'.";
        assert txtHora != null : "fx:id=\"txtHora\" was not injected: check your FXML file 'teste.fxml'.";
        assert imgDevolver != null : "fx:id=\"imgDevolver\" was not injected: check your FXML file 'teste.fxml'.";
        assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'teste.fxml'.";
        assert txtSenha != null : "fx:id=\"txtSenha\" was not injected: check your FXML file 'teste.fxml'.";
        assert txtbusc != null : "fx:id=\"txtbusc\" was not injected: check your FXML file 'teste.fxml'.";
        assert btnBuscUsers != null : "fx:id=\"btnBuscUsers\" was not injected: check your FXML file 'teste.fxml'.";
        assert tabelaViewUsers != null : "fx:id=\"tabelaViewUsers\" was not injected: check your FXML file 'teste.fxml'.";
        assert colUsers != null : "fx:id=\"colUsers\" was not injected: check your FXML file 'teste.fxml'.";
        assert colCode != null : "fx:id=\"colCode\" was not injected: check your FXML file 'teste.fxml'.";
    }

    private void cols() {
        carregamento();
        colBTNdevolver.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setButton(e.getNewValue());
        });
        tabelaChavesEmUso.setEditable(true);
    }

    public void carregamento() {
        //Carregamento        
        try {
            DataChaves = cdao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(UserPadraoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelaChavesEmUso.setItems(DataChaves);
    }

    @FXML
    private void clikBusc(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            try {
                Data = dao.FiltrarList(txtbusc.getText());
            } catch (SQLException ex) {
                Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabelaViewUsers.setItems(Data);
        }

    }

    @FXML
    private void buscarUsers(ActionEvent event) {
        try {
            Data = dao.FiltrarList(txtbusc.getText());
        } catch (SQLException ex) {
            Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelaViewUsers.setItems(Data);
    }

    @FXML
    private void pegar() {
        String dataEfetivaDevolucaoNulla = "0000-00-00";
        if (txtUser.getText().equals("") && txtSenha.getText().equals("")) {
            Alert dialogo1 = new Alert(Alert.AlertType.ERROR);
            dialogo1.setTitle("Erro");
            dialogo1.setContentText("Campos vazios!");
            dialogo1.showAndWait();
        } else {
            if (dao.checkLogin(txtUser.getText(), txtSenha.getText())) {

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date hora = Calendar.getInstance().getTime();
                String horaformatada = sdf.format(hora);

                ChavePega cp = new ChavePega(
                        labs.getValue(),
                        txtUser.getText(),
                        getIdLG(),
                        txtAluno.getText(),
                        horaformatada,
                        txtHora.getValue().toString(),
                        true,
                        new Button("Devolver")
                );

                try {
                    cdao.adicionar(cp);
                    limparCampos();
                    carregarChaves();
                    DataChaves = cdao.gerarLista();
                } catch (SQLException ex) {
                    Alert dialogo1 = new Alert(Alert.AlertType.ERROR);
                    dialogo1.setTitle("Erro");
                    dialogo1.setContentText("Algo deu errado, tente novamente!");
                    dialogo1.showAndWait();
                }
                tabelaChavesEmUso.setItems(DataChaves);
            } else {
                Alert dialogo1 = new Alert(Alert.AlertType.ERROR);
                dialogo1.setTitle("Erro");
                dialogo1.setContentText("Usuario ou senha ivalida!");
                dialogo1.showAndWait();
            }
        }
    }

    public void carregarChaves() {
        try {
            list = cdao.FiltrarList();
        } catch (SQLException ex) {
            Logger.getLogger(UserPadraoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        labs.setItems(list);
    }

    @FXML
    private void imgOverDevolver(DragEvent event) { // Esse metodo é responsavel pela acao do drag drop 
        System.out.println("Chegou no Devolver...");
        if (event.getGestureSource() == tabelaChavesEmUso && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    private void imgDroppDevolver(DragEvent event) throws SQLException {// Esse metodo é responsavel pela acao do drag drop 
        System.out.println("Largou no Devolver...");

        Dragboard db = event.getDragboard();
        System.out.println("Arrastando chave: " + db.getString());
        boolean sucess = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date data = new Date();
        String dataFormatada = dateFormat.format(data);
        if (db.hasString()) {
            System.out.println("Devolvendo o chave: " + db.getString());
            DataChaves.remove(linha);
            cdao.devolver(db.getString(), dataFormatada);
            sucess = true;
            DataChaves = cdao.gerarLista();
            tabelaChavesEmUso.setItems(DataChaves);
            carregarChaves();
        }
        event.setDropCompleted(sucess);
        event.consume();
    }
    int linha = -1;

    @FXML
    private void detectar(MouseEvent event) {// Esse metodo é responsavel pela acao do drag drop 
        linha = tabelaChavesEmUso.getSelectionModel().getSelectedIndex();
        System.out.println("Linha: " + linha);
        System.out.println("Começando a arrastar...");

        Dragboard db = tabelaChavesEmUso.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        if (content.putString(DataChaves.get(linha).getChave().getValue())) {
            db.setContent(content);
            event.consume();
            System.out.println("Arrastando objeto com nome: " + db.getString());
        } else {
            System.out.println("Erro no arrasto");
        }
    }

    @FXML
    private void onSair(ActionEvent event) throws IOException {
        navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/Login.fxml")));
    }

    public void limparCampos() {
        txtAluno.setText("");
        txtUser.setText("");
        txtSenha.setText("");
    }

    @FXML
    private void devolver(ActionEvent event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date data = new Date();
        String dataFormatada = dateFormat.format(data);
        try {
            cdao.devolver(tabelaChavesEmUso.getSelectionModel().getSelectedItem().getChave().getValue(), dataFormatada);
            DataChaves = cdao.gerarLista();
            tabelaChavesEmUso.setItems(DataChaves);
            carregarChaves();

        } catch (SQLException ex) {
            Logger.getLogger(UserPadraoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void devolverButton(ActionEvent event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date data = new Date();
        String dataFormatada = dateFormat.format(data);
        try {
            cdao.devolver(tabelaChavesEmUso.getSelectionModel().getSelectedItem().getChave().getValue(), dataFormatada);
            DataChaves = cdao.gerarLista();
            tabelaChavesEmUso.setItems(DataChaves);
            carregarChaves();

        } catch (SQLException ex) {
            Logger.getLogger(UserPadraoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ComboBox_clicked(ActionEvent event) {
        carregarChaves();
    }

    public ComboBox<String> getLabs() {
        return labs;
    }

    public void setLabs(ObservableList<String> labs) {
        this.labs.setItems(labs);
    }

    public UserPadraoController() {
    }

    @FXML
    private void txtUser_action(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txtSenha.requestFocus();
        }
    }

    @FXML
    private void txtSenha_keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            txtAluno.requestFocus();
        }
    }

    @FXML
    private void txtaluno_keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            pegar();
        }
    }

    @FXML
    private void teste(MouseEvent event) {
        if(event.getEventType() == MouseEvent.MOUSE_RELEASED){
            carregarChaves();
        }
    }

}
