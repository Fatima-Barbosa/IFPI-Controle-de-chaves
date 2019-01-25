package Controllers;

import Classes.Users;
import ModelDAO.UsersDAO;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import javafx.scene.input.TransferMode;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Fátima
 */
public class CadastroFuncionariosController extends LoginController implements Initializable {

    private final UsersDAO dao = new UsersDAO();

    @FXML
    private Button btnVoltar;
    @FXML
    private PasswordField labelSenha;
    @FXML
    private TextField labelCPF;
    @FXML
    private TextField labelNome;
    @FXML
    private TableView<Users> tabelaUsers;
//    private ImageView imgExcluir;
//    @FXML
    private ImageView imgEditar;
    @FXML
    private JFXButton btnAdicionar;
    @FXML
    private TableColumn<Users, String> colNome;
    @FXML
    private TableColumn<Users, String> colCodigo;
    @FXML
    private TableColumn<Users, String> colID;

    private ObservableList<Users> Data
            = FXCollections.observableArrayList();
    int op = 0;
    @FXML
    private MenuItem cont_Editar;
    @FXML
    private MenuItem cont_Excluir;
    @FXML
    private TextField txt_busc;
    @FXML
    private Button btn_busc;

    private void limparCampos() {
        labelNome.setText("");
        labelCPF.setText("");
        labelSenha.setText("");
    }

    @FXML
    void voltarbtn(ActionEvent event) throws IOException {
        System.out.println("TESTE!");
        navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/Menu.fxml")));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        colNome.setCellValueFactory(cellData -> cellData.getValue().getNomeUser());
        colCodigo.setCellValueFactory(cellData -> cellData.getValue().getCpf());
        colID.setCellValueFactory(cellData -> cellData.getValue().getId().asString());
        try {
            Data = dao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelaUsers.setItems(Data);
        assert tabelaUsers != null : "fx:id=\"tabelaUsers\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert colNome != null : "fx:id=\"colNome\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert colCodigo != null : "fx:id=\"colCodigo\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert colID != null : "fx:id=\"colID\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert cont_Editar != null : "fx:id=\"cont_Editar\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert cont_Excluir != null : "fx:id=\"cont_Excluir\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert labelNome != null : "fx:id=\"labelNome\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert labelCPF != null : "fx:id=\"labelCPF\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert labelSenha != null : "fx:id=\"labelSenha\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
//        assert imgExcluir != null : "fx:id=\"imgExcluir\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert imgEditar != null : "fx:id=\"imgEditar\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert btnAdicionar != null : "fx:id=\"btnAdicionar\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert txt_busc != null : "fx:id=\"txt_busc\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
        assert btn_busc != null : "fx:id=\"btn_busc\" was not injected: check your FXML file 'CadastroFuncionarios.fxml'.";
}
    int linha = -1;

    @FXML
    private void detectar(javafx.scene.input.MouseEvent event) {
        linha = tabelaUsers.getSelectionModel().getSelectedIndex();
        System.out.println("Linha: " + linha);
        System.out.println("Começando a arrastar...");

        Dragboard db = tabelaUsers.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        if (content.putString(Data.get(linha).getId().toString())) {
            db.setContent(content);
            event.consume();
            System.out.println("Arrastando objeto com ID: " + db.getString());
        } else {
            System.out.println("Erro no arrasto");
        }
    }

//    private void OverExcluir(DragEvent event) {
//        System.out.println("Chegou no Ecluir...");
//        if (event.getGestureSource() == tabelaUsers && event.getDragboard().hasString()) {
//            event.acceptTransferModes(TransferMode.MOVE);
//        }
//        event.consume();
//    }

//    private void DroppedExcluir(DragEvent event) throws SQLException {
//        System.out.println("Largou no remover...");
//
//        Dragboard db = event.getDragboard();
//        System.out.println("Arrastando usuario: " + db.getString());
//        boolean sucess = false;
//        if (db.hasString()) {
//            System.out.println("Apagando o usuario: " + db.getString());
//            Data.remove(linha);
//            dao.remover(Long.parseLong((db.toString())));
//            sucess = true;
//        }
//        event.setDropCompleted(sucess);
//        event.consume();
//    }

    @FXML
    private void OverEdit(DragEvent event) {
        System.out.println("Chegou no Editar...");
        if (event.getGestureSource() == tabelaUsers && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    private void DroppedEdit(DragEvent event) throws SQLException {
        System.out.println("Largou no Editar...");
        Dragboard db = event.getDragboard();
        btnAdicionar.setText("Editar");
        op = 1;
        boolean sucess = false;
        if (db.hasString()) {

            String id = tabelaUsers.getSelectionModel().getSelectedItem().getCpf().getValue();
            System.out.println("entrou..........");
            labelNome.setText(tabelaUsers.getSelectionModel().getSelectedItem().getNomeUser().getValue());
            labelCPF.setText(tabelaUsers.getSelectionModel().getSelectedItem().getCpf().getValue());
            labelSenha.setText(dao.senha(id));
            btnAdicionar.setText("Editar");
            sucess = true;
        }

        event.setDropCompleted(sucess);
        event.consume();

    }

    @FXML
    private void add(ActionEvent event) {

        if (op != 0) {
            System.out.println("entrou..........NoEdit");
            Users k = new Users(
                    labelNome.getText(),
                    labelSenha.getText(),
                    labelCPF.getText(),
                    tabelaUsers.getSelectionModel().getSelectedItem().getId().getValue()
            );
            dao.update(k);
            Alert dialogo = new Alert(Alert.AlertType.INFORMATION);

                dialogo.setTitle("Status da operação");
                dialogo.setHeaderText("Operaçao bem sucedida!");
                dialogo.setContentText("Atualizado com sucesso!");
                dialogo.showAndWait();
            atualizar();
            try {
                Data = dao.gerarLista();
            } catch (SQLException ex) {
                Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);

            }
            tabelaUsers.setItems(Data);
            btnAdicionar.setText("Adicionar");
            op = 0;
            limparCampos();

        } else {
            if (labelNome.getText().equals("") && labelCPF.getText().equals("") && labelSenha.getText().equals("")) {
                System.out.println("erro");
                Alert dialogo1 = new Alert(Alert.AlertType.ERROR);

                dialogo1.setTitle("Status da operação");
                dialogo1.setHeaderText("Erro na operação!");
                dialogo1.setContentText("Campos vazios!");
                dialogo1.showAndWait();
            } else {

                Users u = new Users();
                u.setNomeUser(new SimpleStringProperty(labelNome.getText()));
                u.setCpf(new SimpleStringProperty(labelCPF.getText()));
                u.setSenha(new SimpleStringProperty(labelSenha.getText()));
                dao.adicionar(u);
                atualizar();

                Alert dialogo = new Alert(Alert.AlertType.INFORMATION);

                dialogo.setTitle("Status da operação");
                dialogo.setHeaderText("Operaçao bem sucedida!");
                dialogo.setContentText("adicionado com sucesso!");
                dialogo.showAndWait();
                limparCampos();

            }
        }
    }

    void atualizar() {
        try {
            Data = dao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(CadastroFuncionariosController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelaUsers.setItems(Data);
        System.out.println("atualizando....");
    }

    @FXML
    private void on_cont_Editar(ActionEvent event) {
            String id = tabelaUsers.getSelectionModel().getSelectedItem().getCpf().getValue();
            System.out.println("entrou..........");
            labelNome.setText(tabelaUsers.getSelectionModel().getSelectedItem().getNomeUser().getValue());
            labelCPF.setText(tabelaUsers.getSelectionModel().getSelectedItem().getCpf().getValue());
            labelSenha.setText(dao.senha(id));
            btnAdicionar.setText("Editar");
            
            op = 2;
            
    }

    @FXML
    private void on_cont_Excluir(ActionEvent event) {
        try {
            dao.remover(tabelaUsers.getSelectionModel().getSelectedItem().getId().getValue());
            atualizar();
        } catch (SQLException ex) {
            Logger.getLogger(CadastroFuncionariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void on_busc(ActionEvent event) {
        try {
            Data = dao.FiltrarList(txt_busc.getText());
            tabelaUsers.setItems(Data);
            System.out.println("atualizando....");
            
        } catch (SQLException ex) {
            Logger.getLogger(CadastroFuncionariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void on_txt_busc(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Data = dao.FiltrarList(txt_busc.getText());
            } catch (SQLException ex) {
                Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabelaUsers.setItems(Data);
        }
    }
    
}
