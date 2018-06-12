package application7;

import Classes.keys;
import ModelDAO.ChavesDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
//import javafx.scene.input.MouseEvent;

/**
 *
 * @author Fátima
 */
public class ChavesController extends BaseController implements Initializable {

    private final ChavesDAO dao = new ChavesDAO();

    @FXML
    private TableView<keys> tabelaChave;

    @FXML
    private TableColumn<keys, String> colsala;

    @FXML
    private TableColumn<keys, String> colDescricao;

    @FXML
    private TableColumn<keys, Boolean> colID;

    @FXML
    private TextField labelSala;

    @FXML
    private TextField labelDescricao;

    @FXML
    private JFXButton btnSalvar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private ImageView imgExcluir;

    private ObservableList<keys> Data
            = FXCollections.observableArrayList();
    int op = 0;
    @FXML
    private MenuItem contextEditar;
    @FXML
    private MenuItem contextExcluir;

    private void limparCampos() {
        labelSala.setText("");
        labelSala.setText("");
    }

    @FXML
    void ONSalvar(ActionEvent event) {
        if (op == 0) {
            if (labelDescricao.getText().equals("") && labelSala.getText().equals("")) {
                System.out.println("erro");
                Alert dialogo1 = new Alert(Alert.AlertType.ERROR);

                dialogo1.setTitle("Status da operação");
                dialogo1.setHeaderText("Erro na operação!");
                dialogo1.setContentText("Campos vazios!");
                dialogo1.showAndWait();
            } else {
                keys k = new keys(labelSala.getText(), labelDescricao.getText(), false);
                dao.adicionar(k);

                try {
                    Data = dao.gerarLista();
                } catch (SQLException ex) {
                    Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
                }

                tabelaChave.setItems(Data);
                limparCampos();
                System.out.println("uuuhh");
            }
        } else if (op == 1) {
            keys k = new keys(
                    labelSala.getText(),
                    labelDescricao.getText(),
//                    false
                    tabelaChave.getSelectionModel().getSelectedItem().getPega().getValue()
            );
            dao.update(k);
            try {
                Data = dao.gerarLista();
            } catch (SQLException ex) {
                Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabelaChave.setItems(Data);
            op = 0;
            btnSalvar.setText("Adicionar");
            limparCampos();
        }

    }

    @FXML
    void ONVoltar(ActionEvent event) throws IOException {
        navigate(event, FXMLLoader.load(getClass().getResource("FXML1.fxml")));
    }

    @FXML
    void OverExcluir(DragEvent event) {
        System.out.println("Chegou no Excluir...");
        if (event.getGestureSource() == tabelaChave && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    void DroppedExcluir(DragEvent event) throws SQLException {
        System.out.println("Largou no remover...");

        Dragboard db = event.getDragboard();
        System.out.println("Arrastando sala: " + db.getString());
        boolean sucess = false;
        if (db.hasString()) {
            System.out.println("entrou");
            System.out.println("Apagando a sala: " + db.getString());
            Data.remove(linha);
            dao.remover(db.getString());
            sucess = true;
        }
        atualizarTabela();
        event.setDropCompleted(sucess);
        event.consume();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        colsala.setCellValueFactory(cellData -> cellData.getValue().getSala());
        colDescricao.setCellValueFactory(cellData -> cellData.getValue().getDescricao());
        colID.setCellValueFactory(cellData -> cellData.getValue().getPega());

        try {
            Data = dao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelaChave.setItems(Data);
        assert tabelaChave != null : "fx:id=\"tabelaChave\" was not injected: check your FXML file 'Chaves.fxml'.";
        assert colsala != null : "fx:id=\"colsala\" was not injected: check your FXML file 'Chaves.fxml'.";
        assert colDescricao != null : "fx:id=\"colDescricao\" was not injected: check your FXML file 'Chaves.fxml'.";
        assert colID != null : "fx:id=\"colID\" was not injected: check your FXML file 'Chaves.fxml'.";
        assert labelSala != null : "fx:id=\"labelSala\" was not injected: check your FXML file 'Chaves.fxml'.";
        assert labelDescricao != null : "fx:id=\"labelDescricao\" was not injected: check your FXML file 'Chaves.fxml'.";
        assert btnSalvar != null : "fx:id=\"btnSalvar\" was not injected: check your FXML file 'Chaves.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'Chaves.fxml'.";
        assert imgExcluir != null : "fx:id=\"imgExcluir\" was not injected: check your FXML file 'Chaves.fxml'.";
        assert imgEditar != null : "fx:id=\"imgEditar\" was not injected: check your FXML file 'Chaves.fxml'.";
    }
    int linha = -1;

    @FXML
    private void Detectar(javafx.scene.input.MouseEvent event) {
        linha = tabelaChave.getSelectionModel().getSelectedIndex();
        System.out.println("Linha: " + linha);
        System.out.println("Começando a arrastar...");

        Dragboard db = tabelaChave.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();

        if (content.putString(Data.get(linha).getSala().getValue())) {
            db.setContent(content);
            event.consume();
            System.out.println("Arrastando sala: " + db.getString());
        } else {
            System.out.println("Erro no arrasto");
        }
    }

    @FXML
    private void OverEditar(DragEvent event) {
        System.out.println("Chegou no Editar...");
        if (event.getGestureSource() == tabelaChave && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    private void DroppedEditar(DragEvent event) {
        System.out.println("Largou no Editar...");
        Dragboard db = event.getDragboard();
        boolean sucess = false;
        if (db.hasString()) {
            labelSala.setText(tabelaChave.getSelectionModel().getSelectedItem().getSala().getValue());
            labelDescricao.setText(tabelaChave.getSelectionModel().getSelectedItem().getDescricao().getValue());
            btnSalvar.setText("Editar");
            op = 1;
        }
        atualizarTabela();
    }

    @FXML
    private void ContextEdit(ActionEvent event) {
        keys k = new keys(
                    labelSala.getText(),
                    labelDescricao.getText(),
//                    false
                    tabelaChave.getSelectionModel().getSelectedItem().getPega().getValue()
            );
            dao.update(k);
    }

    @FXML
    private void ContextExclui(ActionEvent event) {
        try {
            dao.remover(tabelaChave.getSelectionModel().getSelectedItem().getSala().getValue());
            atualizarTabela();
        } catch (SQLException ex) {
            Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void atualizarTabela(){
        try {
            Data = dao.gerarLista();
        } catch (SQLException ex) {
            Logger.getLogger(ChavesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelaChave.setItems(Data);
    }
}
