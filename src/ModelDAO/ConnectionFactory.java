package ModelDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Fátima
 */
public class ConnectionFactory {
    public Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bdifpi?zeroDateTimeBehavior=convertToNull", "root", "root"
            );
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
    }
}
