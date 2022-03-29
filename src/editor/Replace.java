package editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Locale;

public class Replace {
    private Stage replaceStage;
    private GridPane gridPane = new GridPane();
    private Scene replaceScene;
    private TextArea textArea;
    private int cursorPosition;
    private TextField findTextField;
    private TextField replaceTextField;

    public Replace(TextArea textArea){
        replaceScene = new Scene(gridPane, 400, 200);
        this.textArea = textArea;
        cursorPosition = textArea.getCaretPosition();
        findTextField = new TextField();
        replaceTextField = new TextField();
    }

    public void createNonModalDialog(Stage primaryStage){
        try {
            replaceStage = new Stage();
            replaceStage.setScene(replaceScene);
            replaceStage.initOwner(primaryStage);
            replaceStage.setAlwaysOnTop(false);
            replaceStage.setTitle("Find");
            replaceStage.setResizable(false);
            createDialogElements();
            replaceStage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createDialogElements(){
        Label findLbl = new Label("Find what:");
        Label replaceLbl = new Label("Replace with:");

        findTextField.setPrefColumnCount(15);
        replaceTextField.setPrefColumnCount(15);

        Button findButton = new Button("Find");
        Button replaceButton = new Button("Replace");
        Button replaceAllButton = new Button("Replace all");
        Button cancelButton = new Button("Cancel");
        findButton.defaultButtonProperty();
        cancelButton.cancelButtonProperty();

//        gridPane.addRow(0, findLbl, findTextField, findButton);
//        gridPane.addRow(1, replaceLbl, replaceTextField, replaceButton);
//        gridPane.addRow(2, replaceAllButton);
//        gridPane.addRow(3, cancelButton);

        gridPane.add(findLbl, 0, 0);
        gridPane.add(findTextField, 1, 0);
        gridPane.add(findButton, 2, 0);
        gridPane.add(replaceLbl, 0, 1);
        gridPane.add(replaceTextField, 1, 1);
        gridPane.add(replaceButton, 2, 1);
        gridPane.add(replaceAllButton, 0, 2);
        gridPane.add(cancelButton, 1, 2);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10.0);
        gridPane.setVgap(10.0);

        findButton.setPrefSize(70, 20);
        replaceButton.setPrefSize(70, 20);
        replaceAllButton.setPrefSize(80, 20);
        cancelButton.setPrefSize(80, 20);

        findButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        cancelButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        replaceButton.setStyle("-fx-background-color: #992234; -fx-text-fill: white;");
        replaceAllButton.setStyle("-fx-background-color: #992234; -fx-text-fill: white;");

        findLbl.setStyle("-fx-font: normal bold 15px 'serif' ");
        replaceLbl.setStyle("-fx-font: normal bold 15px 'serif' ");
        findTextField.setStyle("-fx-font: normal bold 12px 'serif' ");
        replaceTextField.setStyle("-fx-font: normal bold 12px 'serif' ");
        gridPane.setStyle("-fx-background-color: BEIGE;");

        onButtonFind(findButton);
        onButtonCancel(cancelButton);
        onButtonReplace(replaceButton);
        onButtonReplaceAll(replaceAllButton);

    }

    public void onButtonReplace(Button replaceButton){
        replaceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Text Error");
                cursorPosition = textArea.getCaretPosition();
                cursorPosition = textArea.getText().indexOf(findTextField.getText(), cursorPosition);

//                added!
                String r = replaceTextField.getText();

                if (cursorPosition <= -1) {
                    alert.setContentText("Cannot find \"" + findTextField.getText() + "\"");
                    alert.showAndWait();
                } else {
                    textArea.selectRange(cursorPosition, cursorPosition + findTextField.getLength());
                    textArea.replaceSelection(r);
                }
            }
        });
    }

    public void onButtonReplaceAll(Button replaceAllButton){
        replaceAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textArea.positionCaret(0);
                cursorPosition = textArea.getCaretPosition();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Text Error");
                if (textArea.getText().indexOf(findTextField.getText(), cursorPosition) == -1) {
                    alert.setContentText("Cannot find \"" + findTextField.getText() + "\"");
                    alert.showAndWait();
                }
                while(cursorPosition > -1) {
                    cursorPosition = textArea.getCaretPosition();
                    cursorPosition = textArea.getText().indexOf(findTextField.getText(), cursorPosition);

//                added!
                    String r = replaceTextField.getText();
                    if (!(cursorPosition <= -1)) {
                        textArea.selectRange(cursorPosition, cursorPosition + findTextField.getLength());
                        textArea.replaceSelection(r);
                    }
                }
            }
        });
    }


    public void onButtonFind(Button findButton){
        findButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Text Error");
                cursorPosition = textArea.getCaretPosition();
                cursorPosition = textArea.getText().indexOf(findTextField.getText(), cursorPosition);
                if (cursorPosition <= -1) {
                    alert.setContentText("Cannot find \"" + findTextField.getText() + "\"");
                    alert.showAndWait();
                } else {
                    textArea.selectRange(cursorPosition, cursorPosition + findTextField.getLength());
                }
            }
        });
    }
    public void onButtonCancel(Button cancelButton){
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                replaceStage.close();
            }
        });
    }

    public boolean checkIfInitialized(){
        if(replaceStage == null) return true;
        return false;
    }

    public void showDialog(){
        replaceStage.show();
    }

}
