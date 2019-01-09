package Controllers;

import java.io.IOException;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Fátima
 */
public class BaseController {
        public void navigate(Event event, Parent pageParent) throws IOException {
       
        //Creating new scene
        Scene scene = new Scene(pageParent);
        
        //get current stage
        Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        //Hide old stage
        appStage.hide(); // Optional
        
        //Set stage with new Scene
        appStage.setScene(scene);
        
        //Show up the stage
        appStage.show();
    }
}
