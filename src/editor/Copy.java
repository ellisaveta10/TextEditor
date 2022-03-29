package editor;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class Copy {

    public Copy(){}

    public void copyToClipboard(String copied){
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(copied);
        clipboard.setContent(content);
    }
}
