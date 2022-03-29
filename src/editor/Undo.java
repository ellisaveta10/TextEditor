package editor;

import javafx.scene.control.TextArea;

public class Undo {
    private TextArea textArea;

    public Undo(TextArea textArea) {
        this.textArea = textArea;
    }

    public boolean isUndoable(){
        return textArea.getText().trim().length() == 0;
    }

    public void undo(){
        if (!isUndoable()){
            System.out.println("ok");

        }
        else{
            System.out.println("not");
        }
    }
}
