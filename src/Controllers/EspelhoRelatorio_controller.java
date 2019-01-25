package Controllers;

import Classes.ChavePega;
import ModelDAO.ChavePegaDAO;
import ModelDAO.ChavesDAO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Fátima
 */
public class EspelhoRelatorio_controller extends LoginController implements Initializable {

    @FXML
    private TextArea txtRelatorio;
    @FXML
    private Button bntVisualizar;
    @FXML
    private ComboBox<String> Box_salas;
    @FXML
    private Button bntSalvar;
    @FXML
    private DatePicker dataInicio;
    @FXML
    private DatePicker dataFinal;
    @FXML
    private Button bntSair;

    ChavePegaDAO dao = new ChavePegaDAO();

    ChavesDAO cdao = new ChavesDAO();
    @FXML
    private Button btnRelatorioGeral;
    @FXML
    private Button btnSALVAR_relatorioGeral;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        carregarChaves();
        assert txtRelatorio != null : "fx:id=\"txtRelatorio\" was not injected: check your FXML file 'EspelhoRelatorio.fxml'.";
        assert Box_salas != null : "fx:id=\"Box_salas\" was not injected: check your FXML file 'EspelhoRelatorio.fxml'.";
        assert bntSalvar != null : "fx:id=\"bntSalvar\" was not injected: check your FXML file 'EspelhoRelatorio.fxml'.";
        assert bntVisualizar != null : "fx:id=\"bntVisualizar\" was not injected: check your FXML file 'EspelhoRelatorio.fxml'.";
        assert dataInicio != null : "fx:id=\"dataInicio\" was not injected: check your FXML file 'EspelhoRelatorio.fxml'.";
        assert dataFinal != null : "fx:id=\"dataFinal\" was not injected: check your FXML file 'EspelhoRelatorio.fxml'.";
        assert bntSair != null : "fx:id=\"bntSair\" was not injected: check your FXML file 'EspelhoRelatorio.fxml'.";

    }

    @FXML
    private void on_Vizualizar(ActionEvent event) {
        String n = "";
        String nada = "Não há registro!";
        String salas = Box_salas.getValue();
        String datainicio = dataInicio.getValue().toString();
        String datafinal = dataFinal.getValue().toString();

        if (salas == null) {
            System.out.println("salas = 0 ");
        } else if (datafinal == null && datainicio == null) {
            System.out.println("datas vazias");
        } else {

            int tamanho = 0;
            tamanho = dao.RelatorioFiltrado(salas, datainicio, datafinal).size();

            if (tamanho <= 0) {
                System.out.println("tamanho da lista = " + tamanho);
                n += nada;
            } else {

                for (int j = 0; j < tamanho; j++) {

                    n += dao.RelatorioFiltrado(salas, datainicio, datafinal).get(j).totring();

                }
            }
            txtRelatorio.setText(n);
        }
    }

    @FXML
    private void on_relatorio_geral(ActionEvent event) {
        String n = "";
        String nada = "Não há registro!";

        int tamanho = 0;
        tamanho = dao.RelatorioList().size();

        if (tamanho <= 0) {
            System.out.println("blá");
            n += nada;
        } else {
            for (int k = 0; k < tamanho; k++) {

                n += dao.RelatorioList().get(k).totring();

            }
        }

        txtRelatorio.setText(n);
    }
// salvando o relatório por chaves

    @FXML
    private void on_Salvar(ActionEvent event) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        Date date = new Date();
        String Dataformatada = dateFormat.format(date);

        Document doc = new Document(PageSize.A4, 30f, 10f, 10f, 10f);

        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

        Date hora = Calendar.getInstance().getTime();
        String horaformatada = sdf.format(hora);

        System.out.println("" + horaformatada);
        Font fontDeLink = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);

        String salas = Box_salas.getValue();
        String datainicio = dataInicio.getValue().toString();
        String datafinal = dataFinal.getValue().toString();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream("C:/Users/Public/Relatorio"
                    + Dataformatada + horaformatada + ".pdf"));
            doc.open();
            List<ChavePega> cp = new ChavePegaDAO().RelatorioFiltrado(salas, datainicio, datafinal);

            doc.add(new Paragraph("                                                           "
                    + "Relatório de uso das salas\n       ", fontDeLink));

            for (int i = 0; i < cp.size(); i++) {
                doc.add(new Paragraph("Usuario:--------------------------------------------------------" + cp.get(i).getUser().getValue(), fontDeLink));
                doc.add(new Paragraph("Chave:----------------------------------------------------------" + cp.get(i).getChave().getValue(), fontDeLink));
                doc.add(new Paragraph("ID da chave:-------------------------------------------------" + cp.get(i).getId(), fontDeLink));
                doc.add(new Paragraph("Aluno:-------------------------------------------------------" + cp.get(i).getAluno().getValue(), fontDeLink));
                doc.add(new Paragraph("Data do emprestimo:------------------------------------------" + cp.get(i).getDia().getValue(), fontDeLink));
                doc.add(new Paragraph("Hora do emprestimo:------------------------------------------" + cp.get(i).getHorap().getValue(), fontDeLink));
                doc.add(new Paragraph("Hora prevista para devolução:-------------------------------" + cp.get(i).getHorad().getValue(), fontDeLink));
                doc.add(new Paragraph("Data efetiva da devolução:-----------------------------------" + cp.get(i).getDataEfetiva().getValue(), fontDeLink));
                doc.add(new Paragraph("Ocupada:-----------------------------------------------------" + cp.get(i).getOcupada().getValue(), fontDeLink));
                doc.add(new Paragraph("ID do Operador:----------------------------------------------" + cp.get(i).getOperador().getValue().toString(), fontDeLink));
                doc.add(new Paragraph("                                                "));
            }

            doc.close();

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Relatorio gerado em: C:/Users/Public/");
            a.show();

        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//Salvando o relatório geral 

    @FXML
    private void on_SalvarGeral(ActionEvent event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        Date date = new Date();
        String Dataformatada = dateFormat.format(date);

        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        Date hora = Calendar.getInstance().getTime();
        String horaformatada = sdf.format(hora);

        Document doc = new Document(PageSize.A4, 30f, 10f, 10f, 10f);

        Font fontDeLink = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);

        try {
            PdfWriter.getInstance(doc, new FileOutputStream("C:/Users/Public/RelatorioGeral"
                    + Dataformatada + horaformatada + ".pdf"));

            doc.open();
            List<ChavePega> cp = new ChavePegaDAO().RelatorioList();

            doc.add(new Paragraph("                                                           "
                    + "Relatório geral\n       ", fontDeLink));

            for (int i = 0; i < cp.size(); i++) {
                doc.add(new Paragraph("Usuario:--------------------------------------------------------" + cp.get(i).getUser().getValue(), fontDeLink));
                doc.add(new Paragraph("Chave:----------------------------------------------------------" + cp.get(i).getChave().getValue(), fontDeLink));
                doc.add(new Paragraph("ID da chave:-------------------------------------------------" + cp.get(i).getId(), fontDeLink));
                doc.add(new Paragraph("Aluno:-------------------------------------------------------" + cp.get(i).getAluno().getValue(), fontDeLink));
                doc.add(new Paragraph("Data do emprestimo:------------------------------------------" + cp.get(i).getDia().getValue(), fontDeLink));
                doc.add(new Paragraph("Hora do emprestimo:------------------------------------------" + cp.get(i).getHorap().getValue(), fontDeLink));
                doc.add(new Paragraph("Hora prevista para devolução:-------------------------------" + cp.get(i).getHorad().getValue(), fontDeLink));
                doc.add(new Paragraph("Data efetiva da devolução:-----------------------------------" + cp.get(i).getDataEfetiva().getValue(), fontDeLink));
                doc.add(new Paragraph("Ocupada:-----------------------------------------------------" + cp.get(i).getOcupada().getValue(), fontDeLink));
                doc.add(new Paragraph("ID do Operador:----------------------------------------------" + cp.get(i).getOperador().getValue().toString(), fontDeLink));
                doc.add(new Paragraph("                                                "));
            }

            doc.close();

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Relatorio gerado em: C:/Users/Public/");
            a.show();

        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void on_Sair(ActionEvent event) throws IOException {
        navigateTeste(event, FXMLLoader.load(getClass().getResource("/View/Menu.fxml")));
    }

    public void carregarChaves() {
        try {
            Box_salas.setItems(cdao.carregarChaves());
        } catch (SQLException ex) {
            Logger.getLogger(EspelhoRelatorio_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String inverteData(DatePicker dataInicio) {
        String data1 = "";
        String dia = dataInicio.getValue().toString().substring(8, 10);
        String mes = dataInicio.getValue().toString().substring(5, 7);
        String ano = dataInicio.getValue().toString().substring(0, 4);
        data1 += dia + "/" + mes + "/" + ano;
        System.out.println("data1:" + data1);
        return data1;
    }

}
