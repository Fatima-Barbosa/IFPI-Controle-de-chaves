package Classes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Fátima
 */
public class keys {

    private SimpleStringProperty sala;
    private SimpleStringProperty descricao;
    private SimpleBooleanProperty pega;

    public keys(String sala, String descricao, Boolean pega) {
        this.sala = new SimpleStringProperty(sala);
        this.descricao = new SimpleStringProperty(descricao);
        this.pega = new SimpleBooleanProperty(pega);
    }

    public keys(String sala) {
        this.sala = new SimpleStringProperty(sala);
    }

    
    
    public keys(String sala, String descricao) {
        this.sala = new SimpleStringProperty(sala);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public keys() {
    }
    
    

    public SimpleStringProperty getSala() {
        return sala;
    }

    public void setSala(SimpleStringProperty sala) {
        this.sala = sala;
    }

    public SimpleStringProperty getDescricao() {
        return descricao;
    }

    public void setDescricao(SimpleStringProperty descricao) {
        this.descricao = descricao;
    }

    public SimpleBooleanProperty getPega() {
        return pega;
    }

    public void setPega(SimpleBooleanProperty pega) {
        this.pega = pega;
    }

    @Override
    public String toString() {
        return "" + sala;
    }

}
