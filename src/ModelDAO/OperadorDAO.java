package ModelDAO;

import Classes.Operador;
import Classes.Users;
import java.sql.PreparedStatement;
import java.sql.Connection;
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
public class OperadorDAO {
    private Connection connection;
    private PreparedStatement stmt; 
    private String sql;

    public OperadorDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adicionar(Operador o){
        connection = new ConnectionFactory().getConnection();
        
        sql = "insert into operador"
                + "(nomeCompleto, login, senha, nivel)"
                + " values(?,?,?,?);";
        try {
            
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
