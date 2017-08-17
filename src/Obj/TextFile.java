package Obj;

import java.util.List;
import java.nio.file.Path;
/**
 * Customized TextFile object, consist by file path and file content
 */
public class TextFile {

    //field
    private final Path file;
    private final List<String> content;

    //constructor
    public TextFile(Path file, List<String> content){
        this.file = file;
        this.content = content;
    }

    /**
     * return file path
     * @return
     */
    public Path getFile(){
        return file;
    }

    /**
     * return file content
     * @return
     */
    public List<String> getContent(){
        return content;
    }
}
