package Classes;

/**
 *
 * @author Fátima
 */
public class UsuarioA {
    private String login;
    private String senha;
    private Boolean tipo;
    private long id;

    public UsuarioA(String login, String senha, Boolean tipo, long id) {
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
        this.id = id;
    }

    public UsuarioA(String login, String senha, Boolean tipo) {
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    
    
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
}
