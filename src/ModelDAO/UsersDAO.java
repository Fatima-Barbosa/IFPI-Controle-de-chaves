package ModelDAO;

import Classes.Users;
import com.jfoenix.controls.JFXDialog;
import java.awt.Dialog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;

/**
 *
 * @author Fátima
 */
public class UsersDAO {

    private Connection connection;
    private PreparedStatement stmt;
    private String sql;

    public UsersDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adicionar(Users u) {

        connection = new ConnectionFactory().getConnection();

        sql = "insert into users"
                + "(nomeUsers, cpf, senha)"
                + " values(?,?,?);";
        try {
            // prepared statement para inserção
            stmt = connection.prepareStatement(sql);
            // seta os valores
            stmt.setString(1, u.getNomeUser().getValue());
            stmt.setString(2, u.getCpf().getValue());

            String encoding = Base64.getEncoder().encodeToString(u.getSenha().getValue().getBytes());
            //passando senha criptografada
            stmt.setString(3, encoding);
            stmt.execute();
            
            javafx.scene.control.Dialog dialogo = new Alert(Alert.AlertType.INFORMATION);
            dialogo.setHeaderText("Operação realizada!");
            dialogo.setTitle("Informação");
            dialogo.show();  

            stmt.close();

            connection.close();

        } catch (SQLException ex) {
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setHeaderText("Atenção");
            dialog.setContentText("Usuario invalido ou já existente!");
            dialog.showAndWait();
            System.out.println("erro no adicionar dao");
            throw new RuntimeException(ex);
        }
    }

    public void update(Users c) {
        connection = new ConnectionFactory().getConnection();

        try {
            stmt = connection.prepareStatement("UPDATE users SET nomeUsers = ?, cpf = ?, senha = ? WHERE id = ?");
            stmt.setString(1, c.getNomeUser().getValue());
            stmt.setString(2, c.getCpf().getValue());

            String encoding = Base64.getEncoder().encodeToString(c.getSenha().getValue().getBytes());
            //Passando a senha atuaizada com criptográfia
            stmt.setString(3, encoding);
            stmt.setString(4, c.getId().getValue().toString());

            stmt.executeUpdate();
            connection.close();
            stmt.close();
            javafx.scene.control.Dialog dialogo = new Alert(Alert.AlertType.INFORMATION);
            dialogo.setHeaderText("Operação realizada!");
            dialogo.setTitle("Informação");
            dialogo.show();
        } catch (SQLException ex) {
            Logger.getLogger(ChavesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void remover(long nome) throws SQLException {
        connection = new ConnectionFactory().getConnection();

        sql = "delete from users where id = ?";
        stmt = connection.prepareStatement(sql);
        // seta os valores
        stmt.setLong(1, nome);
        // executa
        stmt.execute();
        System.out.println("Excluido com sucesso!");
        stmt.close();
        connection.close();

    }

    public final ObservableList<Users> gerarLista() throws SQLException {
        connection = new ConnectionFactory().getConnection();
        ObservableList<Users> Lista
                = FXCollections.observableArrayList();
        stmt = connection.prepareStatement("select * from users order by nomeUsers;");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Lista.add(new Users(
                    rs.getString("nomeUsers"),
                    rs.getString("senha"),
                    rs.getString("cpf"),
                    rs.getLong("id")
            )
            );
        }
        stmt.close();
        connection.close();
        return Lista;
    }

    public final String senha(String cpf) {
        connection = new ConnectionFactory().getConnection();
        String senha = null;
        try {
            stmt = connection.prepareStatement("select * from users WHERE cpf = ?;");
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                senha = rs.getString("senha");
            }
            stmt.close();

            connection.close();
            System.out.println("senha: " + senha);
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return senha;
    }

    public final boolean checkLogin(String login, String senha) {
        connection = new ConnectionFactory().getConnection();
        boolean check = false;
        try {
            stmt = connection.prepareStatement("select * from users WHERE nomeUsers = ? and senha = ?;");
            stmt.setString(1, login);

            String encoding = Base64.getEncoder().encodeToString(senha.getBytes());

            stmt.setString(2, encoding);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                check = true;
            }
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return check;
    }

    public final ObservableList<Users> FiltrarList(String n) throws SQLException {
        connection = new ConnectionFactory().getConnection();
        ObservableList<Users> Lista
                = FXCollections.observableArrayList();

        stmt = connection.prepareStatement("select * from users where nomeUsers like ?;");
        stmt.setString(1, "%" + n + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Lista.add(new Users(
                    rs.getString("nomeUsers"),
                    rs.getString("cpf"),
                    rs.getLong("id")
            )
            );
        }
        stmt.close();
        connection.close();
        return Lista;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement getStmt() {
        return stmt;
    }

    public void setStmt(PreparedStatement stmt) {
        this.stmt = stmt;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
