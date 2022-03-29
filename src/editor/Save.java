package editor;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Save {

    private TextArea textArea;

    public Save(TextArea textArea) {
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

    public String save(Stage stage, String filename, String directoryPath) throws FileNotFoundException {
        String text = textArea.getText().replaceAll("\n", System.getProperty("line.separator"));
        FileChooser fileChooser = new FileChooser();
        if(!filename.isEmpty()) {
            fileChooser.setInitialFileName(filename);
            File file = new File(directoryPath);
            saveText(text, file);
        }else {
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                saveText(text, file);
            }
            if(file != null) {
                return file.getName() + " " + file.getAbsolutePath();
            }
        }
        return "";

    }
}
