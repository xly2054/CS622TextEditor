package Editor;

import Obj.TextFile;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Arrays;
/**
 * Models for TextEditor
 */
public class EditorModel {

    Clipboard systemClipboard = Clipboard.getSystemClipboard();                 //Single clipboard for clipboard related operation

    /**
     * Open a text file and convert to TextFile Object
     * @param file
     * @return
     */
    public TextFile open(Path file) {
        try {
            List<String> lines = Files.readAllLines(file);                      //read all lines from file
            return new TextFile(file, lines);                                   //convert file object and file content into TextFile object
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Write TextFile object back to file
     * @param textFile
     */
    public void save(TextFile textFile){
        try{
            Files.write(textFile.getFile(), textFile.getContent(), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);     //Write content into file, truncate if existing file already have content
            System.out.println(Arrays.toString(textFile.getContent().toArray()));                                                       //for debug
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Terminate application
     */
    public void exit(){
        System.exit(0);
    }

    /**
     * Copy input string to clipboard
     * @param input
     */
    public void copy(String input) {
        ClipboardContent content = new ClipboardContent();
        content.putString(input);
        systemClipboard.setContent(content);
    }

    /**
     * Return current value of clipboard
     * @return
     */
    public String paste() {
        return systemClipboard.getString();
    }

}
