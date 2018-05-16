package ModelDAO;

import Classes.keys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Fátima
 */
public class ChavesDAO {

    private Connection connection;
    private PreparedStatement stmt;
    private String sql;

    public ChavesDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adicionar(keys k) {

        connection = new ConnectionFactory().getConnection();

        sql = "insert into chaves"
                + "(sala, descricao, ocupada)"
                + " values(?,?,?)";
        try {
            // prepared statement para inserção
            stmt = connection.prepareStatement(sql);
            // seta os valores
            stmt.setString(1, k.getSala().getValue());
            stmt.setString(2, k.getDescricao().getValue());
            stmt.setBoolean(3, k.getPega().getValue());
            stmt.execute();

            System.out.println("Adicionado com sucesso!");

            stmt.close();

            connection.close();

        } catch (SQLException ex) {
            System.out.println("erro no adicionar dao: " + ex);
            throw new RuntimeException(ex);
            //return -1;
        }
    }

    public void update(keys c) {
        connection = new ConnectionFactory().getConnection();

        try {
            stmt = connection.prepareStatement("UPDATE keycontroll.chaves SET sala = ?, descricao = ?, ocupada = ? WHERE sala = ?;");
            stmt.setString(4, c.getSala().getValue());
            stmt.setString(1, c.getSala().getValue());
            stmt.setString(2, c.getDescricao().getValue());
            stmt.setBoolean(3, c.getPega().getValue());

            stmt.executeUpdate();
            connection.close();
            stmt.close();
            System.out.println("Atualizado!\n");
        } catch (SQLException ex) {
            Logger.getLogger(ChavesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void OcuparChave(String c) {
        connection = new ConnectionFactory().getConnection();

        try {
            stmt = connection.prepareStatement("UPDATE keycontroll.chaves SET ocupada = true WHERE sala = ?;");
            stmt.setString(1, c);
            //  stmt.setBoolean(1, c.getPega().getValue());

            stmt.executeUpdate();
            connection.close();
            stmt.close();
            System.out.println("Atualizado!\n");
        } catch (SQLException ex) {
            Logger.getLogger(ChavesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DevolverChave(String c) {
        connection = new ConnectionFactory().getConnection();

        try {
            stmt = connection.prepareStatement("UPDATE keycontroll.chaves SET ocupada = false WHERE sala = ?;");
            stmt.setString(1, c);
            //  stmt.setBoolean(1, c.getPega().getValue());

            stmt.executeUpdate();
            connection.close();
            stmt.close();
            System.out.println("Atualizado!\n");
        } catch (SQLException ex) {
            System.out.println("nao atualizou");
            Logger.getLogger(ChavesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void remover(String sala) throws SQLException {
        connection = new ConnectionFactory().getConnection();

        sql = "delete from chaves where sala = ?";
        stmt = connection.prepareStatement(sql);
        // seta os valores
        stmt.setString(1, sala);
        // executa
        stmt.execute();
        System.out.println("Excluido com sucesso!");
        stmt.close();

    }

    public final ObservableList<keys> gerarLista() throws SQLException {
        connection = new ConnectionFactory().getConnection();
        ObservableList<keys> Lista
                = FXCollections.observableArrayList();

        stmt = connection.prepareStatement("select * from keycontroll.chaves;");
//
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Lista.add(new keys(
                    rs.getString("sala"),
                    rs.getString("descricao"),
                    rs.getBoolean("ocupada"))
            );
        }
//        stmt.close();
        connection.close();
        return Lista;
    }
    
        public final ObservableList<keys> filtrarList(String n) throws SQLException {
        connection = new ConnectionFactory().getConnection();
        ObservableList<keys> Lista
                = FXCollections.observableArrayList();

        stmt = connection.prepareStatement("select * from keycontroll.chaves where sala like ?;");
        stmt.setString(1, "%" + n + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Lista.add(new keys(
                    rs.getString("sala"),
                    rs.getString("descricao"),
                    rs.getBoolean("ocupada"))
            );
        }
//        stmt.close();
        connection.close();
        return Lista;
    }

    public void nome(long id) throws SQLException {
        connection = new ConnectionFactory().getConnection();

        sql = "select * from chaves where id = ?";
        stmt = connection.prepareStatement(sql);
        // seta os valores
        ObservableList<keys> Lista
                = FXCollections.observableArrayList();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Lista.add(new keys(
                    rs.getString("sala"),
                    rs.getString("descricao"),
                    rs.getBoolean("ocupada"))
            );
        }
        stmt.setLong(1, id);
        // executa
        stmt.execute();
        System.out.println("Excluido com sucesso!");
        stmt.close();

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
