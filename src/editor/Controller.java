package editor;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.robot.Robot;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Controller {

    private String fileName;
    private String directoryPath;

    public Controller() {
        fileName = "";
        directoryPath = "";
    }


//    public void onUndo(MenuItem undo, TextArea textArea){
//        undo.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Undo undo = new Undo(textArea);
//                undo.undo();
//
//            }
//        });
//    }

    public void onUndo(MenuItem undo) {
        Robot robot = new Robot();
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                robot.keyPress(KeyCode.CONTROL);
                robot.keyPress(KeyCode.Z);
                robot.keyRelease(KeyCode.CONTROL);
                robot.keyRelease(KeyCode.Z);
            }
        });
    }

    public void onRedo(MenuItem redo) {
        Robot robot = new Robot();
        redo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                robot.keyPress(KeyCode.CONTROL);
                robot.keyPress(KeyCode.Y);
                robot.keyRelease(KeyCode.CONTROL);
                robot.keyRelease(KeyCode.Y);
            }
        });
    }


    public void onCopy(MenuItem copy, TextArea textArea) {
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Copy copy = new Copy();
                copy.copyToClipboard(textArea.getSelectedText());
            }
        });
    }

    public void onPaste(MenuItem paste, TextArea textArea) {
        paste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Paste paste = new Paste();
                int pos = textArea.getCaretPosition();
                textArea.insertText(pos, paste.pasteFromClipboard());
            }
        });
    }

    public void onClear(MenuItem clear, TextArea textArea) {
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.setText("");
            }
        });
    }

    public void onDelete(MenuItem delete, TextArea textArea) {
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.setText(textArea.getText().replace(textArea.getSelectedText(), ""));
            }
        });
    }

    public void onCut(MenuItem cut, TextArea textArea) {
        cut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Copy copy = new Copy();
                copy.copyToClipboard(textArea.getSelectedText());
                textArea.setText(textArea.getText().replace(textArea.getSelectedText(), ""));
            }
        });
    }

    public void onSelectAll(MenuItem selectAll, TextArea textArea) {
        selectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.selectAll();
            }
        });
    }

    public void onTimeDate(MenuItem time_date, TextArea textArea) {
        time_date.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                int pos = textArea.getCaretPosition();
                textArea.insertText(pos, dtf.format(now));
            }
        });
    }

    public void onFind(MenuItem find, TextArea textArea, Stage primaryStage, MenuItem findNext, MenuItem findPrevious) {
        Find findObject = new Find(textArea);
        find.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (findObject.checkIfInitialized()) {
                    findObject.createNonModalDialog(primaryStage);
                } else findObject.showDialog();
            }
        });
        findNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                findObject.findNext(primaryStage);
            }
        });
        findPrevious.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                findObject.findPrevious(primaryStage);
            }
        });
    }

    public void onReplace(MenuItem replace, TextArea textArea, Stage primaryStage) {
        Replace replaceObject = new Replace(textArea);
        replace.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (replaceObject.checkIfInitialized()) {
                    replaceObject.createNonModalDialog(primaryStage);
                } else {
                    replaceObject.showDialog();
                }
            }
        });
    }

//    public void onReplaceAll(MenuItem replaceAll, TextArea textArea, Stage primaryStage){
//        Replace replace = new Replace(textArea);
//        replaceAll.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                if(replace.checkIfInitialized()) {
//                    replace.createNonModalDialog(primaryStage);
//                }else {
//                    replace.showDialog();
//                }
//            }
//        });
//    }

    public void onNew(MenuItem newFile, MenuItem saveAs, TextArea textArea, Stage stage) {
        newFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.getButtonTypes().remove(ButtonType.OK);
                alert.getButtonTypes().add(ButtonType.NO);
                alert.getButtonTypes().add(ButtonType.YES);
                alert.getButtonTypes().add(ButtonType.CANCEL);
                alert.setTitle("Quit application");
                alert.setHeaderText("Do you want to save your document?");

                if (textArea.getLength() == 0) {
//                   do nothing
                } else {
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent()) {
                        if (result.get().equals(ButtonType.YES)) {
                            saveAs.fire();
                            textArea.clear();
                            fileName = "";
                            directoryPath = "";
                            stage.setTitle("Untitled.txt");
                        } else if (result.get().equals(ButtonType.NO)) {
//                            Platform.exit();
                            textArea.clear();
                            fileName = "";
                            directoryPath = "";
                            stage.setTitle("Untitled.txt");
                        } else if (result.get().equals(ButtonType.CANCEL)) {
                            alert.close();
                        }
                    }
//                    textArea.clear();
                }
            }
        });
    }


    public void onNewWindow(MenuItem newWindow, TextArea textArea, MenuBar menuBar, Scene scene, Stage primaryStage) {
        newWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                NewWindow newWindow = new NewWindow(textArea);
                newWindow.new_window(menuBar, scene, primaryStage, textArea);
            }
        });
    }

    public void onOpen(MenuItem open, TextArea textArea, Stage stage) {
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Open openFile = new Open(textArea);
                String info = openFile.openFile(stage);
                if (!info.isEmpty()) {
                    String[] parts = info.split(" ", 2);
                    fileName = parts[0];
                    directoryPath = parts[1];
                    stage.setTitle(fileName);
                }
            }
        });
    }

    public void onSave(MenuItem save, TextArea textArea, Stage stage) {
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Save saveFile = new Save(textArea);
                try {
                    String info = saveFile.save(stage, fileName, directoryPath);
                    if (!info.isEmpty()) {
                        String[] parts = info.split(" ", 2);
                        fileName = parts[0];
                        directoryPath = parts[1];
                        stage.setTitle(fileName);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onSaveAs(MenuItem saveAs, TextArea textArea, Stage stage) {
        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SaveAs saveFile = new SaveAs(textArea);
                try {
                    String info = saveFile.saveAs(stage);
                    if (!info.isEmpty()) {
                        String[] parts = info.split(" ", 2);
                        fileName = parts[0];
                        directoryPath = parts[1];
                        stage.setTitle(fileName);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onExit(MenuItem exit, MenuItem saveAs) {
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.getButtonTypes().remove(ButtonType.OK);
                alert.getButtonTypes().add(ButtonType.NO);
                alert.getButtonTypes().add(ButtonType.YES);
                alert.getButtonTypes().add(ButtonType.CANCEL);
                alert.setTitle("Quit application");
                alert.setHeaderText("Do you want to save your document?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get().equals(ButtonType.YES)) {
                        saveAs.fire();
                    } else if (result.get().equals(ButtonType.NO)) {
                        Platform.exit();
                    } else if (result.get().equals(ButtonType.CANCEL)) {
                        alert.close();
                    }
                }
            }
        });
    }

    public void onFont(MenuItem font, TextArea textArea, Stage primaryStage) {
        Font fontObj = new Font(textArea);
        font.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (fontObj.checkIfInitialized()) {
                    fontObj.createNonModalDialog(primaryStage);
                } else fontObj.showDialog();
            }
        });
    }


    public void onEdit(Menu edit, MenuItem undo, MenuItem cut, MenuItem copy, MenuItem delete, MenuItem find, MenuItem findNext, MenuItem findPrevious, TextArea textArea) {
        edit.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if(textArea.getSelectedText().equals("")){
                    cut.setDisable(true);
                    copy.setDisable(true);
                    delete.setDisable(true);
                }
                else{
                    cut.setDisable(false);
                    copy.setDisable(false);
                    delete.setDisable(false);
                }
                if (textArea.getText().isEmpty()) {
                    undo.setDisable(true);
//            redo.setDisable(true);
                    find.setDisable(true);
                    findNext.setDisable(true);
                    findPrevious.setDisable(true);
                } else {
                    undo.setDisable(false);
//            redo.setDisable(false);
                    find.setDisable(false);
                    findNext.setDisable(false);
                    findPrevious.setDisable(false);
                }
            }

        });
}

}

//package editor;
//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.VBox;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Optional;
//
//public class Controller{
//    private String fileName;
//    private String directoryPath;
//
//    public Controller(){
//        fileName = "";
//        directoryPath = "";
//    }
//
//    public void onNew(MenuItem newFile, MenuItem saveAs, TextArea textArea){
//        newFile.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Alert alert = new Alert(Alert.AlertType.NONE);
//                alert.getButtonTypes().remove(ButtonType.OK);
//                alert.getButtonTypes().add(ButtonType.NO);
//                alert.getButtonTypes().add(ButtonType.YES);
//                alert.getButtonTypes().add(ButtonType.CANCEL);
//                alert.setTitle("Quit application");
//                alert.setHeaderText("Do you want to save your document?");
//
//                if (textArea.getLength() == 0) {
////                   do nothing
//                } else {
//                    Optional<ButtonType> result = alert.showAndWait();
//                    if (result.isPresent()) {
//                        if (result.get().equals(ButtonType.YES)) {
//                            saveAs.fire();
//                        } else if (result.get().equals(ButtonType.NO)) {
//                            Platform.exit();
//                        } else if (result.get().equals(ButtonType.CANCEL)) {
//                            alert.close();
//                        }
//                    }
//                    textArea.clear();
//                }
//            }
//        });
//    }
//
//    public void onNewWindow(MenuItem newWindow, TextArea textArea, MenuBar menuBar, Scene scene, Stage primaryStage){
//        newWindow.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                NewWindow newWindow = new NewWindow(textArea);
//                newWindow.new_window(menuBar, scene, primaryStage, textArea);
//            }
//        });
//    }
//
//    public void onOpen(MenuItem open, TextArea textArea, Stage stage){
//        open.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Open openFile = new Open(textArea);
//                String info = openFile.openFile(stage);
//                if(!info.isEmpty()) {
//                    String[] parts = info.split(" ", 2);
//                    fileName = parts[0];
//                    directoryPath = parts[1];
//                }
//            }
//        });
//    }
//
//    public void onSave(MenuItem save, TextArea textArea, Stage stage){
//        save.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Save saveFile = new Save(textArea);
//                try {
//                    String info = saveFile.save(stage, fileName, directoryPath);
//                    if(!info.isEmpty()) {
//                        String[] parts = info.split(" ", 2);
//                        fileName = parts[0];
//                        directoryPath = parts[1];
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    public void onSaveAs(MenuItem saveAs, TextArea textArea, Stage stage){
//        saveAs.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                SaveAs saveFile = new SaveAs(textArea);
//                try {
//                    String info = saveFile.saveAs(stage);
//                    if(!info.isEmpty()) {
//                        String[] parts = info.split(" ", 2);
//                        fileName = parts[0];
//                        directoryPath = parts[1];
////                        stage.setTitle(fileName);
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    public void onExit(MenuItem exit, MenuItem saveAs){
//        exit.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.getButtonTypes().remove(ButtonType.OK);
//                alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.NO, ButtonType.YES);
//                alert.setTitle("Quit application");
//                alert.setHeaderText("Do you want to save your document?");
//
//                Optional<ButtonType> result = alert.showAndWait();
//                if(result.isPresent()) {
//                    if (result.get() == ButtonType.YES) {
//                        saveAs.fire();
//                    } else if (result.get() == ButtonType.CANCEL) {
//                        alert.close();
//                    } else if (result.get() == ButtonType.NO) {
//                        Platform.exit();
//                    }
//                }
//            }
//        });
//    }
//
//    public void onCopy(MenuItem copy, TextArea textArea){
//        copy.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Copy copy = new Copy();
//                copy.copyToClipboard(textArea.getSelectedText());
//            }
//        });
//    }
//
//    public void onPaste(MenuItem paste, TextArea textArea){
//        paste.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Paste paste = new Paste();
//                int pos = textArea.getCaretPosition();
//                textArea.insertText(pos,paste.pasteFromClipboard());
//            }
//        });
//    }
//
//    public void onClear(MenuItem clear, TextArea textArea){
//        clear.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                textArea.setText("");
//            }
//        });
//    }
//
//    public void onDelete(MenuItem delete, TextArea textArea){
//        delete.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                textArea.setText(textArea.getText().replace(textArea.getSelectedText(),""));
//            }
//        });
//    }
//
//    public void onCut(MenuItem cut, TextArea textArea){
//        cut.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Copy copy = new Copy();
//                copy.copyToClipboard(textArea.getSelectedText());
//                textArea.setText(textArea.getText().replace(textArea.getSelectedText(),""));
//            }
//        });
//    }
//
//    public void onSelectAll(MenuItem selectAll, TextArea textArea){
//        selectAll.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                textArea.selectAll();
//            }
//        });
//    }
//
//    public void onTimeDate(MenuItem time_date, TextArea textArea){
//        time_date.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
//                LocalDateTime now = LocalDateTime.now();
//                int pos = textArea.getCaretPosition();
//                textArea.insertText(pos,dtf.format(now));
//            }
//        });
//    }
//
//    public void onFind(MenuItem find, TextArea textArea, Stage primaryStage, MenuItem findNext, MenuItem findPrevious){
//        Find findObject = new Find(textArea);
//        find.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                if(findObject.checkIfInitialized()) {
//                    findObject.createNonModalDialog(primaryStage);
//                }else findObject.showDialog();
//            }
//        });
//        findNext.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                findObject.findNext(primaryStage);
//            }
//        });
//        findPrevious.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                findObject.findPrevious(primaryStage);
//            }
//        });
//    }
//
//    public void onReplace(MenuItem replace, TextArea textArea, Stage primaryStage){
//        Replace replaceObject = new Replace(textArea);
//        replace.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                if(replaceObject.checkIfInitialized()) {
//                    replaceObject.createNonModalDialog(primaryStage);
//                }else replaceObject.showDialog();
//            }
//        });
//    }
//
//    public void onFont(MenuItem font, TextArea textArea, Stage primaryStage){
//        Font fontObj = new Font(textArea);
//        font.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                if(fontObj.checkIfInitialized()) {
//                    fontObj.createNonModalDialog(primaryStage);
//                }else fontObj.showDialog();
//            }
//        });
//    }
//
//    public void onUndo(MenuItem undo, String text){ }
//
//
//}

