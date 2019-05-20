package ModelDAO;

import Classes.Operador;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;

/**
 *
 * @author Fátima
 */
public class OperadorDAO {

    private Connection connection;
    private PreparedStatement stmt;
    private String sql;

    public OperadorDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adicionar(Operador o) {
        connection = new ConnectionFactory().getConnection();

        sql = "insert into operador"
                + "(nomeCompleto, login, senha, nivel)"
                + " values(?,?,?,?);";
        try {
            String codificado = Base64.getEncoder().encodeToString(o.getSenha().getValue().getBytes());
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, o.getNome().getValue());
            stmt.setString(2, o.getLogin().getValue());
            stmt.setString(3, codificado);
            stmt.setString(4, o.getTipo().getValue());
            stmt.execute();
//            System.out.println("Adicionado com sucesso! ;>");
            stmt.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(OperadorDAO.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("erro no adicionar, caiu no catch!");
        }

    }

    public final ObservableList<Operador> gerarLista() throws SQLException {
        connection = new ConnectionFactory().getConnection();
        ObservableList<Operador> Lista
                = FXCollections.observableArrayList();
        stmt = connection.prepareStatement("select * from operador order by nomeCompleto;");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Lista.add(new Operador(
                    rs.getString("nomeCompleto"),
                    rs.getString("login"),
                    rs.getString("nivel"),
                    rs.getLong("id")
            )
            );
        }
        stmt.close();
        connection.close();
        return Lista;
    }

    public void remover(long nome) throws SQLException {
        connection = new ConnectionFactory().getConnection();

        sql = "delete from operador where id = ?";
        stmt = connection.prepareStatement(sql);
        // seta os valores
        stmt.setLong(1, nome);
        // executa
        stmt.execute();
        stmt.close();
        connection.close();

    }

    public final String checkLogin(String login, String senha) {
        String nivel = "sn";
        if (VerificaLogin(login, senha)) {
            connection = new ConnectionFactory().getConnection();

            try {
                //String strcodificado = Base64.getEncoder().encodeToString(senha.getBytes());

                stmt = connection.prepareStatement("select * from operador WHERE login =? and senha = ?;");
                stmt.setString(1, login);
                stmt.setString(2, senha);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    nivel = rs.getString("nivel");
                }
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return nivel;
        }
        return nivel;
    }

    public final boolean VerificaLogin(String login, String senha) {
        connection = new ConnectionFactory().getConnection();
        boolean check = false;
        //String strcodificado1 = Base64.getEncoder().encodeToString(senha.getBytes());
//        System.out.println("senha cript: "+senha+"\n"+strcodificado1);
        try {
            //String strcodificado = Base64.getEncoder().encodeToString(senha.getBytes());
            stmt = connection.prepareStatement("select * from operador WHERE login =? and senha = ?;");
            stmt.setString(1, login);
            stmt.setString(2, senha);
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

    public final String senha(Long id) {
        connection = new ConnectionFactory().getConnection();
        String senha = null;
        try {
            stmt = connection.prepareStatement("select * from operador WHERE id = ?;");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                senha = rs.getString("senha");
            }
            stmt.close();

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return senha;
    }

    public void update(Operador c) {
        connection = new ConnectionFactory().getConnection();

        try {
            String strcodificado = Base64.getEncoder().encodeToString(c.getSenha().getValue().getBytes());
            stmt = connection.prepareStatement("UPDATE operador SET nomeCompleto = ?, login = ?, senha = ?, nivel =? WHERE id = ?");
            stmt.setString(1, c.getNome().getValue());
            stmt.setString(2, c.getLogin().getValue());
            stmt.setString(3, strcodificado);
            stmt.setString(4, c.getTipo().getValue());

            stmt.setString(5, c.getId().getValue().toString());

            stmt.executeUpdate();
            connection.close();
            stmt.close();
//            System.out.println("Usuário atualizado!\n");

            Dialog dialogo = new Alert(Alert.AlertType.INFORMATION);
            dialogo.setHeaderText("Operação realizada!");
            dialogo.setContentText("Usuário atualizado!");
            dialogo.setTitle("Informação");
            dialogo.show();

        } catch (SQLException ex) {
            Dialog dialogo = new Alert(Alert.AlertType.WARNING);
            dialogo.setHeaderText("Atenção");
            dialogo.setContentText("O usuário já existe!"
                    + "\nTente outro");
            dialogo.setTitle("Erro");
            dialogo.show();

        }
    }

    public long RetornarID(String login, String senha) {
        connection = new ConnectionFactory().getConnection();
        long id = 0;
        try {
            String strcodificado = Base64.getEncoder().encodeToString(senha.getBytes());
            stmt = connection.prepareStatement("select * from operador where login = ? and senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, strcodificado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
        }

        return id;
    }

    public final ObservableList<Operador> gerarListaDeBusca(String str) {
        connection = new ConnectionFactory().getConnection();
        ObservableList<Operador> Lista
                = FXCollections.observableArrayList();
        try {
            stmt = connection.prepareStatement("select * from operador where nomeCompleto like ? or login like ? order by nomeCompleto;");
            stmt.setString(1, "%" + str + "%");
            stmt.setString(2, "%" + str + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Lista.add(new Operador(
                        rs.getString("nomeCompleto"),
                        rs.getString("login"),
                        rs.getString("nivel"),
                        rs.getLong("id")
                )
                );
            }
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
        }
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
