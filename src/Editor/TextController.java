package Editor;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Text file controller, co-exist with TextArea.fxml file
 * Tracking TextArea in new tab
 */
public class TextController {

    @FXML
    private TextArea areaText ;

    public TextArea getTextArea() {
        return areaText;
    }
}
