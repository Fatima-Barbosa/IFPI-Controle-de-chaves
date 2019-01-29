package ModelDAO;

import Classes.Operador;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte messageDigest[] = md.digest(o.getSenha().getValue().getBytes("UTF-8"));
//            
//            StringBuilder sb = new StringBuilder();
//            
//            for(byte b: messageDigest){
//                sb.append(String.format("%02X", 0xFF & b));
//                
//            }
//            String senhaHex = sb.toString();
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, o.getNome().getValue());
            stmt.setString(2, o.getLogin().getValue());
            stmt.setString(3, o.getSenha().getValue());
            stmt.setString(4, o.getTipo().getValue());
            stmt.execute();
            System.out.println("Adicionado com sucesso! ;>");
            stmt.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(OperadorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erro no adicionar, caiu no catch!");
        }
//        catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(OperadorDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(OperadorDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public final ObservableList<Operador> gerarLista() throws SQLException {
        connection = new ConnectionFactory().getConnection();
        ObservableList<Operador> Lista
                = FXCollections.observableArrayList();
        stmt = connection.prepareStatement("select * from keycontroll.operador order by nomeCompleto;");

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
        System.out.println("Excluido com sucesso!");
        stmt.close();
        connection.close();

    }

    public final String checkLogin(String login, String senha) {
        String nivel = "sn";
        if (VerificaLogin(login, senha)) {
            connection = new ConnectionFactory().getConnection();

            try {
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
        try {
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
            stmt = connection.prepareStatement("UPDATE keycontroll.operador SET nomeCompleto = ?, login = ?, senha = ?, nivel =? WHERE id = ?");
            stmt.setString(1, c.getNome().getValue());
            stmt.setString(2, c.getLogin().getValue());
            stmt.setString(3, c.getSenha().getValue());
            stmt.setString(4, c.getTipo().getValue());

            stmt.setString(5, c.getId().getValue().toString());

            stmt.executeUpdate();
            connection.close();
            stmt.close();
            System.out.println("Usuário atualizado!\n");
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

            stmt = connection.prepareStatement("select * from operador where login = ? and senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
            stmt.close();
            connection.close();
            System.out.println("login: " + login
                    + "Senha: " + senha
                    + "id: " + id);
        } catch (SQLException e) {
        }

        return id;
    }

    public final ObservableList<Operador> gerarListaDeBusca(String str) {
        connection = new ConnectionFactory().getConnection();
        ObservableList<Operador> Lista
                = FXCollections.observableArrayList();
        try {
            stmt = connection.prepareStatement("select * from keycontroll.operador where nomeCompleto like ? or login like ? order by nomeCompleto;");
            stmt.setString(1, "%" + str +"%");
            stmt.setString(2, "%" + str +"%");
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
            Logger.getLogger(OperadorDAO.class.getName()).log(Level.SEVERE, null, ex);
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
