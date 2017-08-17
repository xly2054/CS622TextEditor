package Editor;

import Obj.TextFile;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * TextEditor controller
 */
public class EditorController {

    //field
    @FXML
    private TabPane tabPane;
    private TextFile currentTextFile;
    private EditorModel model;
    private TextArea areaText;

    //constructor
    public EditorController(EditorModel model){
        this.model = model;
    }


    /**
     * Creates new tab with empty text area
     * @throws IOException
     */
    @FXML
    private void onNew() throws IOException{

        Tab tab = new Tab("Untitled");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FX/TextArea.fxml"));      //load Text tab UI
        tab.setContent(loader.load());                                                                  //load UI into new tab
        TextController controller = loader.getController();
        tab.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {                       //select current tab on tab change event
            if (isNowSelected) {
                areaText = controller.getTextArea();                                                    //update class variable and tracking text area on current tab
            }
        });
        tabPane.getTabs().add(tab);                                                                     //add new tab to view
        tabPane.getSelectionModel().select(tab);                                                        //select newly added tab
    }

    /**
     * Open file and display in a new tab
     * @throws IOException
     */
    @FXML
    private void onOpen() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("./"));
        File file = chooser.showOpenDialog(tabPane.getScene().getWindow());                             //display file explorer for user
        if (file != null) {                                                                             //if a file is selected from dialog
            TextFile in = model.open(file.toPath());                                                    //load file
            currentTextFile = in;
            onNew();                                                                                    //creates a new tab
            if (in.getContent()!= null) {
                areaText.clear();
                tabPane.getSelectionModel().getSelectedItem().setText(file.getName());                  //change tab name to file name
                currentTextFile.getContent().forEach(line -> areaText.appendText(line + "\n"));         //display file content
            } else {
                System.out.println("Failed");
            }
        }
    }

    /**
     * Save text back to file
     */
    @FXML
    private void onSave(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        File file = fileChooser.showSaveDialog(null);           //display file dialog for user
        currentTextFile = new TextFile(file.toPath(),null);

        TextFile textFile = new TextFile(currentTextFile.getFile(), Arrays.asList(areaText.getText().split("\n")));     //convert text from editor back to object
        model.save(textFile);                                                                                                  //Using model writes to file
        tabPane.getSelectionModel().getSelectedItem().setText(file.getName());                                                  //set tab name to file name
    }

    /**
     * Close current selected tab
     */
    @FXML
    private void onCloseTab(){
        Tab tab = tabPane.getSelectionModel().getSelectedItem();
        tab.getTabPane().getTabs().remove(tab);
    }

    /**
     * Terminate application
     */
    @FXML
    private void onExit(){
        model.exit();
    }

    /**
     * Change all chars to BOLD
     */
    @FXML
    private void onBold(){
        areaText.setFont(Font.font(areaText.getFont().toString(), FontWeight.BOLD, areaText.getFont().getSize()));
    }

    /**
     * Change all chars to ITALIC
     */
    @FXML
    private void onItalic(){
        areaText.setFont(Font.font(areaText.getFont().toString(), FontPosture.ITALIC, areaText.getFont().getSize()));
    }

    /**
     * Cut selected text
     */
    @FXML
    private void onCut(){
        String toClip = areaText.getSelectedText();                 //get selected
        model.copy(toClip);                                         //copy selected
        areaText.deleteText(areaText.getSelection());               //delete selected
    }

    /**
     * Copy selected text
     */
    @FXML
    private void onCopy(){
        String toClip = areaText.getSelectedText();
        model.copy(toClip);                                         //copy selected
    }

    /**
     * Paste text on selected spot
     */
    @FXML
    private void onPaste(){
        String input = model.paste();                                                                   //get text back from clipboard
        String firstPart = areaText.getText(0,areaText.getSelection().getStart());                      //retrieve text from beginning of file up to selection
        String lastPart = areaText.getText(areaText.getSelection().getEnd(),areaText.getLength());      //retrieve text from end of selection to end of file
        String updated = firstPart + input + lastPart;                                                  //adding text from clipboard in between
        areaText.setText(updated);                                                                      //update text area
    }
}
