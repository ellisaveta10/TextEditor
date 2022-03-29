package editor;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveAs {

    private TextArea textArea;

    public SaveAs(TextArea textArea) {
        this.textArea = textArea;
    }

    private void saveText(String text, File file) throws FileNotFoundException {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(text);
            writer.close();
        }catch(IOException e){
            Logger.getLogger(SaveAs.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String saveAs(Stage stage) throws FileNotFoundException {
        String text = textArea.getText().replaceAll("\n", System.getProperty("line.separator"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            // here save the data to file
            saveText(text, file);
        }
        if(file != null) {
            return file.getName() + " " + file.getAbsolutePath();
        }else return "";
    }
}
