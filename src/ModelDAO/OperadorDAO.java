package ModelDAO;

import Classes.Operador;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            stmt.setInt(4, o.getTipo());
            stmt.execute();
            System.out.println("Adicionado com sucesso! ;>");
            stmt.close();
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(OperadorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erro no adicionar, caiu no catch!");
        }
        
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
