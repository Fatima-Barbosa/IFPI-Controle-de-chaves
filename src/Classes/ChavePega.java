package Classes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Fátima
 */
public class ChavePega {

    private keys k;
    private Users u;
    private SimpleStringProperty user;
    private SimpleStringProperty chave;
    private SimpleStringProperty aluno;
    private SimpleStringProperty horap;
    private SimpleStringProperty horad;
    private SimpleBooleanProperty ocupada;
    private SimpleStringProperty dia;
    

    private SimpleLongProperty id;

    public ChavePega(keys k, Users u, String aluno, String horap, String horad, String dia, long id) {
        this.k = new keys();
        this.u = new Users();
        this.aluno = new SimpleStringProperty(aluno);
        this.horap = new SimpleStringProperty(horap);
        this.horad = new SimpleStringProperty(horad);
        this.dia = new SimpleStringProperty(dia);
        this.id = new SimpleLongProperty(id);
    }


    public ChavePega(String chave, String user,  String aluno, String horap, String horad, String dia, long id, Boolean ocupada) {
        this.chave = new SimpleStringProperty(chave);
        this.user = new SimpleStringProperty(user);
        this.aluno = new SimpleStringProperty(aluno);
        this.horap = new SimpleStringProperty(horap);
        this.horad = new SimpleStringProperty(horad);
        this.dia = new SimpleStringProperty(dia);
        this.id = new SimpleLongProperty(id);
        this.ocupada = new SimpleBooleanProperty(ocupada);
    }

        public ChavePega(String chave, String user,  String aluno, String horap, String horad, String dia, Boolean ocupada) {
        this.chave = new SimpleStringProperty(chave);
        this.user = new SimpleStringProperty(user);
        this.aluno = new SimpleStringProperty(aluno);
        this.horap = new SimpleStringProperty(horap);
        this.horad = new SimpleStringProperty(horad);
        this.dia = new SimpleStringProperty(dia);
        this.ocupada = new SimpleBooleanProperty(ocupada);
    }
    public ChavePega() {

    } 

    public Users getU() {
        return u;
    }

    public void setU(Users u) {
        this.u = u;
    }

    public SimpleStringProperty getAluno() {
        return aluno;
    }

    public void setAluno(SimpleStringProperty aluno) {
        this.aluno = aluno;
    }

    public SimpleStringProperty getHorap() {
        return horap;
    }

    public void setHorap(SimpleStringProperty horap) {
        this.horap = horap;
    }

    public SimpleStringProperty getHorad() {
        return horad;
    }

    public void setHorad(SimpleStringProperty horad) {
        this.horad = horad;
    }

    public SimpleStringProperty getDia() {
        return dia;
    }

    public void setDia(SimpleStringProperty dia) {
        this.dia = dia;
    }

    public Long getId() {
        return id.getValue();
    }

    public void setId(SimpleLongProperty id) {
        this.id = id;
    }

    public keys getK() {
        return k;
    }

    public void setK(keys k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "ChavePega{" + "k=" + k + ", u=" + u + ", horad=" + horad + '}';
    }

    public SimpleStringProperty getUser() {
        return user;
    }

    public void setUser(SimpleStringProperty user) {
        this.user = user;
    }

    public SimpleStringProperty getChave() {
        return chave;
    }

    public void setChave(SimpleStringProperty chave) {
        this.chave = chave;
    }

    public SimpleBooleanProperty getOcupada() {
        return ocupada;
    }

    public void setOcupada(SimpleBooleanProperty ocupada) {
        this.ocupada = ocupada;
    }
}
