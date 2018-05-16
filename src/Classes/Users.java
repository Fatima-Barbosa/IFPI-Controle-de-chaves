package Classes;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Fátima
 */
public class Users extends RecursiveTreeObject<Users>{

    private SimpleStringProperty nomeUser;
    private SimpleStringProperty senha;
    private SimpleStringProperty cpf;   
    private SimpleLongProperty id;

    public Users(String nomeUser, String senha, String cpf, Long id) {
        this.nomeUser = new SimpleStringProperty(nomeUser);
        this.senha = new SimpleStringProperty(senha);
        this.cpf = new SimpleStringProperty(cpf);
        this.id = new SimpleLongProperty(id);
    }

    public Users(String nomeUser, String senha, String cpf) {
        this.nomeUser = new SimpleStringProperty(nomeUser);
        this.senha = new SimpleStringProperty(senha);
        this.cpf = new SimpleStringProperty(cpf);
    }
    
    public Users(String nomeUser, String cpf, Long id) {
        this.nomeUser = new SimpleStringProperty(nomeUser);
        this.cpf = new SimpleStringProperty(cpf);
        this.id = new SimpleLongProperty(id);
    }

    
    
    public Users() {
    }

    public SimpleStringProperty getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(SimpleStringProperty nomeUser) {
        this.nomeUser = nomeUser;
    }

    public SimpleStringProperty getSenha() {
        return senha;
    }

    public void setSenha(SimpleStringProperty senha) {
        this.senha = senha;
    }

    public SimpleStringProperty getCpf() {
        return cpf;
    }

    public void setCpf(SimpleStringProperty cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Users{" + "nomeUser=" + nomeUser + '}';
    }

    public SimpleLongProperty getId() {
        return id;
    }

    public void setId(SimpleLongProperty id) {
        this.id = id;
    }
    
}
