import Editor.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * TextEditor Application
 */
public class TextEditor extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FX/UI.fxml"));      //load interface
        loader.setControllerFactory(t -> new EditorController(new EditorModel()));              //load controller

        stage.setScene(new Scene(loader.load()));                                               //render scene
        stage.show();                                                                           //display scene
    }

    public static void main(String[] args) {
        launch(args);
    }
}
