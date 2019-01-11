package Classes;

import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Fátima
 */
public class Operador {
    private SimpleStringProperty nome;    
    private SimpleStringProperty login;
    private SimpleStringProperty senha;
    private SimpleStringProperty tipo;
    private SimpleLongProperty id;

    public Operador(String nome, String login, String senha, String tipo, Long id) {
        this.nome = new SimpleStringProperty(nome);
        this.login = new SimpleStringProperty(login);
        this.senha = new SimpleStringProperty(senha);
        this.tipo = new SimpleStringProperty(tipo);
        this.id = new ReadOnlyLongWrapper(id);
    }

    public Operador(String nome, String login, String senha, String tipo) {
        this.nome = new SimpleStringProperty(nome);
        this.login = new SimpleStringProperty(login);
        this.senha = new SimpleStringProperty(senha);
        this.tipo = new SimpleStringProperty(tipo);
    }
    
    public Operador(String nome, String login, String tipo, Long id) {
        this.nome = new SimpleStringProperty(nome);
        this.login = new SimpleStringProperty(login);
        this.tipo = new SimpleStringProperty(tipo);        
        this.id = new ReadOnlyLongWrapper(id);
    }

    public Operador() {
    }
    
    public SimpleStringProperty getNome() {
        return nome;
    }

    public void setNome(SimpleStringProperty nome) {
        this.nome = nome;
    }

    public SimpleStringProperty getLogin() {
        return login;
    }

    public void setLogin(SimpleStringProperty login) {
        this.login = login;
    }

    public SimpleStringProperty getSenha() {
        return senha;
    }

    public void setSenha(SimpleStringProperty senha) {
        this.senha = senha;
    }
    
    public SimpleLongProperty getId() {
        return id;
    }

    public void setId(SimpleLongProperty id) {
        this.id = id;
    }

    public SimpleStringProperty getTipo() {
        return tipo;
    }

    public void setTipo(SimpleStringProperty tipo) {
        this.tipo = tipo;
    }

    
}
