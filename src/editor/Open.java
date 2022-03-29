package editor;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Open {
    private TextArea textArea;

    public Open(TextArea textArea) {
        this.textArea = textArea;
    }

    public String openFile(Stage stage){
        FileChooser fileChooser= new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                textArea.clear();
                Scanner fin = new Scanner(file);
                while (fin.hasNext()) {
                    // read from the file, appending text to a control or other object
                    textArea.setText(textArea.getText() + fin.nextLine() + "\n");
                }
                fin.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            return file.getName() + " " + file.getAbsolutePath();
        }
        return "";
    }
}
