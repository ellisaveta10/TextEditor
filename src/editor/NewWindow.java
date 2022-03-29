package editor;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class NewWindow {
    public String text;

    public NewWindow(TextArea textArea) {
        this.text = textArea.getText(0,textArea.getLength());
    }

    public void new_window(MenuBar menuBar, Scene scene, Stage primaryStage, TextArea textArea) {

        textArea.clear();

        Stage stage  = new Stage();
        stage.setTitle("Text Editor");
        stage.setScene(scene);
        stage.show();



        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                textArea.setText(text);
                primaryStage.setScene(scene);
            }
        });


    }
}
