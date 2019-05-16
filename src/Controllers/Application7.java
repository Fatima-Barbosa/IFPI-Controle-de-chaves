package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Fátima
 */
public class Application7 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Sis. Controle de acesso a salas e laboratórios");
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
