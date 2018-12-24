package application7;

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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class UserPadraoController extends BaseController implements Initializable {

    Users u = new Users();
    UsersDAO dao = new UsersDAO();
    ChavePega c = new ChavePega();
    ChavePegaDAO cdao = new ChavePegaDAO();

    @FXML
    private TableView<Users> tabelaViewUsers;

    @FXML
    private TableColumn<Users, String> colUsers;

    @FXML
    private TableColumn<Users, String> colCode;

    private TableColumn<Users, String> colID;

    private ObservableList<Users> Data
            = FXCollections.observableArrayList();

    private ObservableList<ChavePega> DataChaves
            = FXCollections.observableArrayList();

    @FXML
    private TableView<ChavePega> tabelaChavesEmUso;
    @FXML
    private TableColumn<ChavePega, String> colChave;
    @FXML
    private TableColumn<ChavePega, String> colUsuario;
    @FXML
    private TableColumn<ChavePega, String> colDevolucao;
    @FXML
    private TextField txtbusc;
    @FXML
    private Button btnBuscUsers;
    @FXML
    private TextField txtUser;
    @FXML
    private ComboBox<String> labs;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colUsers.setCellValueFactory(cellData -> cellData.getValue().getNomeUser());
        colCode.setCellValueFactory(cellData -> cellData.getValue().getCpf());

        colChave.setCellValueFactory(cellData -> cellData.getValue().getChave());
        colUsuario.setCellValueFactory(cellData -> cellData.getValue().getUser());
        colDevolucao.setCellValueFactory(cellData -> cellData.getValue().getHorad());

        try {
            Data = dao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            DataChaves = cdao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(UserPadraoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelaViewUsers.setItems(Data);
        tabelaChavesEmUso.setItems(DataChaves);
        carregarChaves();
        assert txtUser != null : "fx:id=\"txtUser\" was not injected: check your FXML file 'teste.fxml'.";
        assert tabelaChavesEmUso != null : "fx:id=\"tabelaChavesEmUso\" was not injected: check your FXML file 'teste.fxml'.";
        assert colChave != null : "fx:id=\"colChave\" was not injected: check your FXML file 'teste.fxml'.";
        assert colUsuario != null : "fx:id=\"colUsuario\" was not injected: check your FXML file 'teste.fxml'.";
        assert colDevolucao != null : "fx:id=\"colDevolucao\" was not injected: check your FXML file 'teste.fxml'.";
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

    @FXML
    private void clikBusc(KeyEvent event) {
        /*
        event.getEventType(VK_ENTER);
        KeyCode key = event.getCode().ENTER;
        KeyEvent.KEY_PRESSED.equals(KeyCode.ENTER)
        KeyCode e = event.getCode();
         */
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
    private void pegar(ActionEvent event) {
        String dataEfetivaDevolucaoNulla = "0000-00-00";
        if (txtUser.getText().equals("") && txtSenha.getText().equals("")) {
            System.out.println("erro");
            Alert dialogo1 = new Alert(Alert.AlertType.ERROR);
            dialogo1.setTitle("Erro");
            dialogo1.setContentText("Campos vazios!");
            dialogo1.showAndWait();
        } else {
            if (dao.checkLogin(txtUser.getText(), txtSenha.getText())) {
                
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date hora = Calendar.getInstance().getTime();
                String horaformatada = sdf.format(hora);
                

                System.out.println("Hora: " + horaformatada);
                ChavePega cp = new ChavePega(
                        labs.getValue(),
                        txtUser.getText(),
                        txtAluno.getText(),
                        horaformatada,
                        txtHora.getValue().toString(),
                        true
                );

                try {
                    cdao.adicionar(cp);
                    limparCampos();
                    carregarChaves();
                    DataChaves = cdao.gerarLista();
                } catch (SQLException ex) {
                    Logger.getLogger(UserPadraoController.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("Entrou");

        try {
            labs.setItems(cdao.FiltrarList());
        } catch (SQLException ex) {
            Logger.getLogger(UserPadraoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void imgOverDevolver(DragEvent event) {
        System.out.println("Chegou no Devolver...");
        if (event.getGestureSource() == tabelaChavesEmUso && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    private void imgDroppDevolver(DragEvent event) throws SQLException {
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
    private void detectar(MouseEvent event) {
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
        navigate(event, FXMLLoader.load(getClass().getResource("Login.fxml")));
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
}
