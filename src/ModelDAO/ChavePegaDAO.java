package ModelDAO;

import Classes.ChavePega;
import Classes.keys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 *
 * @author Fátima
 */
public class ChavePegaDAO {

    private Connection connection;
    private PreparedStatement stmt;
    private String sql;

    ChavesDAO chave = new ChavesDAO();

    public ChavePegaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adicionar(ChavePega c) {
        connection = new ConnectionFactory().getConnection();

        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        sql = "insert into chavepega (aluno,chavePega,usuario,operadorID,horap,horad,datap,ocupado) values (?,?,?,?,?,?,?,?);";
        try {
            // prepared statement para inserção
            stmt = connection.prepareStatement(sql);
            // seta os valores
            stmt.setString(1, c.getAluno().getValue());
            stmt.setObject(2, c.getChave().getValue());
            stmt.setObject(3, c.getUser().getValue());
            stmt.setLong(4, c.getOperador().longValue());
            stmt.setString(5, c.getHorap().getValue());
            stmt.setString(6, c.getHorad().getValue());
            stmt.setString(7, dateFormat.format(d));
            stmt.setBoolean(8, true);

            stmt.execute();
            stmt.close();
            connection.close();
            chave.OcuparChave(c.getChave().getValue());

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void devolver(String c, String dataD) {
        connection = new ConnectionFactory().getConnection();

        sql = "UPDATE chavepega set ocupado = false, dataEfetivaD = ? where chavepega=?;";

        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, dataD);
            stmt.setString(2, c);
            stmt.execute();
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChavePegaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        chave.DevolverChave(c);
    }

    public void removerDados() {
        connection = new ConnectionFactory().getConnection();

        sql = "truncate table chavepega;";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.execute();
            System.out.println("Excluido com sucesso!");
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChavePegaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public final ObservableList<ChavePega> gerarLista() throws SQLException {
        connection = new ConnectionFactory().getConnection();
        ObservableList<ChavePega> Lista
                = FXCollections.observableArrayList();

        stmt = connection.prepareStatement("select * from chavepega where ocupado = true;");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ChavePega c = new ChavePega(
                    rs.getString("chavePega"),
                    rs.getString("usuario"),
                    rs.getLong("operadorID"),
                    rs.getString("aluno"),
                    rs.getString("horap"),
                    rs.getString("horad"),
                    rs.getString("datap"),
                    rs.getLong("id"),
                    rs.getBoolean("ocupado"),
                    rs.getString("dataEfetivaD"),
                    new Button("Devolver")
            );
            Lista.add(c);
        }
        stmt.close();
        connection.close();
        return Lista;
    }

    public final List<ChavePega> RelatorioList() {
        connection = new ConnectionFactory().getConnection();
        List<ChavePega> Lista = new ArrayList<>();

        try {
            stmt = connection.prepareStatement("select * from chavepega;");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ChavePega c = new ChavePega(
                        rs.getString("chavePega"),
                        rs.getString("usuario"),
                        rs.getLong("operadorID"),
                        rs.getString("aluno"),
                        rs.getString("horap"),
                        rs.getString("horad"),
                        rs.getString("datap"),
                        rs.getLong("id"),
                        rs.getBoolean("ocupado"),
                        rs.getString("dataEfetivaD")
                );
                Lista.add(c);
            }
            stmt.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(ChavePegaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Lista;
    }

    public final List<ChavePega> RelatorioFiltrado(String sala, String data, String dataD) {
        connection = new ConnectionFactory().getConnection();
        List<ChavePega> Lista = new ArrayList<>();
        try {
            stmt = connection.prepareStatement(
                    "select * from chavePega WHERE chavePega = ? "
                    + "and (extract(year from datap) >= ? and extract(year from datap) <= ?) "
                    + "and (extract(month from datap) >= ? or extract(month from datap) <= ?) "
                    + "and (extract(day from datap) >= ? or extract(day from datap) <= ?);"
            );
            stmt.setString(1, sala);
            stmt.setString(2, data.substring(0, 4));
            stmt.setString(4, data.substring(5, 7));
            stmt.setString(6, data.substring(8, 10));
            stmt.setString(3, dataD.substring(0, 4));
            stmt.setString(5, dataD.substring(5, 7));
            stmt.setString(7, dataD.substring(8, 10));
            
            System.out.println("sala: "+sala+"\ndatas: "+data.substring(0, 4)
                                +"  "+data.substring(5, 7)
                                +"  "+data.substring(8, 10)
                                +"  "+dataD.substring(0, 4)
                                +"  "+dataD.substring(5, 7)
                                +"  "+dataD.substring(8, 10)
            );

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ChavePega c = new ChavePega(
                        rs.getString("chavePega"),
                        rs.getString("usuario"),
                        rs.getLong("operadorID"),
                        rs.getString("aluno"),
                        rs.getString("horap"),
                        rs.getString("horad"),
                        rs.getString("datap"),
                        rs.getLong("id"),
                        rs.getBoolean("ocupado"),
                        rs.getString("dataEfetivaD")
                );
                Lista.add(c);
            }
            stmt.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Erro:  " + ex);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Erro");
            a.setContentText("Preencha todos os campos!");
            a.show();
        }
        System.out.println(""+Lista.toString());
        return Lista;
    }

    public ObservableList<String> FiltrarList() throws SQLException {
        connection = new ConnectionFactory().getConnection();
        ObservableList<String> Lista
                = FXCollections.observableArrayList();

        stmt = connection.prepareStatement("select * from chaves where ocupada=false;");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            keys k = new keys(
                    rs.getString("sala"),
                    rs.getString("descricao"),
                    rs.getBoolean("ocupada")
            );
            Lista.add(k.getSala().getValue());
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
