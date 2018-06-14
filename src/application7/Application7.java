package application7;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.ImageIcon;

/**
 *
 * @author Fátima
 */
public class Application7 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Sistema de Cotrole de chaves");
        stage.setResizable(false);
        stage.show();
        //..\imagens\edit-property.png
        
        Image imageIcon = new Image(getClass().getResourceAsStream("/imagens/padlock_77917.png"));
        stage.getIcons().add(imageIcon);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
