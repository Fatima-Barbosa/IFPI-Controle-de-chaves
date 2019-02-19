/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Classes.ChavePega;
import Classes.Users;
import Classes.keys;
import ModelDAO.ChavePegaDAO;
import ModelDAO.ChavesDAO;
import ModelDAO.UsersDAO;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class Home_Controller implements Initializable {

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
    
    private final ChavesDAO keysDao = new ChavesDAO();
    private final UsersDAO userDao = new UsersDAO();
    private final ChavePegaDAO cdao = new ChavePegaDAO();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
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
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tabelaChavesUsadas.setItems(Datacp);
        
        // TODO
        assert tabelaChavesUsadas != null : "fx:id=\"tabelaChavesUsadas\" was not injected: check your FXML file 'Home.fxml'.";
        assert colUser != null : "fx:id=\"colUser\" was not injected: check your FXML file 'Home.fxml'.";
        assert colSalaP != null : "fx:id=\"colSalaP\" was not injected: check your FXML file 'Home.fxml'.";
        assert colAluno != null : "fx:id=\"colAluno\" was not injected: check your FXML file 'Home.fxml'.";
        assert colHora != null : "fx:id=\"colHora\" was not injected: check your FXML file 'Home.fxml'.";
        assert tabelaChaves != null : "fx:id=\"tabelaChaves\" was not injected: check your FXML file 'Home.fxml'.";
        assert colSala != null : "fx:id=\"colSala\" was not injected: check your FXML file 'Home.fxml'.";
        assert colDescricao != null : "fx:id=\"colDescricao\" was not injected: check your FXML file 'Home.fxml'.";
        assert colID != null : "fx:id=\"colID\" was not injected: check your FXML file 'Home.fxml'.";
        assert buscKey != null : "fx:id=\"buscKey\" was not injected: check your FXML file 'Home.fxml'.";
        assert BTNbuscKey != null : "fx:id=\"BTNbuscKey\" was not injected: check your FXML file 'Home.fxml'.";
        assert UserBusc != null : "fx:id=\"UserBusc\" was not injected: check your FXML file 'Home.fxml'.";
        assert btnBuscUser != null : "fx:id=\"btnBuscUser\" was not injected: check your FXML file 'Home.fxml'.";
        assert tabelaUsuarios != null : "fx:id=\"tabelaUsuarios\" was not injected: check your FXML file 'Home.fxml'.";
        assert collNome != null : "fx:id=\"collNome\" was not injected: check your FXML file 'Home.fxml'.";
        assert collCode != null : "fx:id=\"collCode\" was not injected: check your FXML file 'Home.fxml'.";
        assert collID != null : "fx:id=\"collID\" was not injected: check your FXML file 'Home.fxml'.";

    }

    @FXML
    void buscKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Data = keysDao.filtrarList(buscKey.getText());
            } catch (SQLException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabelaChaves.setItems(Data);
        }
    }

    @FXML
    void busckey(ActionEvent event) {
        try {
            Data = keysDao.filtrarList(buscKey.getText());
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelaChaves.setItems(Data);
    }
    
        @FXML
    void BTNBuscUser(ActionEvent event) {
        try {
            Data2 = userDao.FiltrarList(UserBusc.getText());
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tabelaUsuarios.setItems(Data2);
    }

    @FXML
    void BuscUser(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Data2 = userDao.FiltrarList(UserBusc.getText());
            } catch (SQLException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabelaUsuarios.setItems(Data2);
        }
    }

 

}
