package editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;

public class Find {
    private Stage findStage;
    private GridPane gridPane = new GridPane();
    private Scene findScene;
    private TextArea textArea;
    private int cursorPosition;
    private TextField textField;

    public Find(TextArea textArea){
        findScene = new Scene(gridPane, 350, 150);
        this.textArea = textArea;
        cursorPosition = textArea.getCaretPosition();
        textField = new TextField();
    }

    public void createNonModalDialog(Stage primaryStage){
        try {
            findStage = new Stage();
            findStage.setScene(findScene);
            findStage.initOwner(primaryStage);
            findStage.setAlwaysOnTop(false);
            findStage.setTitle("Find");
            findStage.setResizable(false);
            createDialogElements();
            findStage.show();

        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public void createDialogElements(){
        Label findLbl = new Label("Find what:");

        textField.setPrefColumnCount(15);

        Button findButton = new Button("Find");

        Button cancelButton = new Button("Cancel");
        findButton.defaultButtonProperty();
        cancelButton.cancelButtonProperty();


        RadioButton upButton = new RadioButton("Up");
        RadioButton downButton = new RadioButton("Down");

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(upButton, downButton);
        downButton.setSelected(true);

//        gridPane.addRow(0, findLbl, textField, findButton);
//        gridPane.addRow(1, upButton, downButton);
//        gridPane.addRow(2, cancelButton);
        gridPane.add(findLbl, 0, 0);
        gridPane.add(textField,1, 0);
        gridPane.add(findButton, 2, 0);
        gridPane.add(upButton, 0, 1);
        gridPane.add(downButton, 1, 1);
        gridPane.add(cancelButton, 0, 3);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10.0);
        gridPane.setHgap(10.0);
        findButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        cancelButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        findLbl.setStyle("-fx-font: normal bold 15px 'serif' ");
        textField.setStyle("-fx-font: normal bold 12px 'serif' ");
        gridPane.setStyle("-fx-background-color: BEIGE;");

        onButtonFind(findButton, upButton, downButton);
        onButtonCancel(cancelButton);


    }

    public void onButtonFind(Button findButton, RadioButton up, RadioButton down){
        findButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Text Error");

                if (textField.getText() != null && !textField.getText().isEmpty()) {

                    if(up.isSelected()){
                        cursorPosition = textArea.getCaretPosition() - textField.getLength()-1;
                    }
                    if(down.isSelected()){
                        cursorPosition = textArea.getCaretPosition();
                    }


                    if(down.isSelected()) {
                        cursorPosition = textArea.getText().indexOf(textField.getText(), cursorPosition);
                    }else{
                        cursorPosition = textArea.getText().lastIndexOf(textField.getText(), cursorPosition);
                    }

                    if (cursorPosition <= -1) {
                        alert.setContentText("Cannot find \"" + textField.getText() + "\"");
                        alert.showAndWait();
                    } else {
                        textArea.selectRange(cursorPosition, cursorPosition + textField.getLength());
                    }
                } else {
                    alert.setContentText("Missing search key");
                    alert.showAndWait();
                }
            }
        });
    }

    public void onButtonCancel(Button cancelButton){
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                findStage.close();
            }
        });
    }

    public void findNext(Stage primaryStage){
        if(!checkIfInitialized()) {
            if (textField.equals(null)) {
                findStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Text Error");
                cursorPosition = textArea.getCaretPosition();
                cursorPosition = textArea.getText().indexOf(textField.getText(), cursorPosition);
                if (cursorPosition <= -1) {
                    alert.setContentText("Cannot find \"" + textField.getText() + "\"");
                    alert.showAndWait();
                } else {
                    textArea.selectRange(cursorPosition, cursorPosition + textField.getLength());
                }
            }
        }else{
            createNonModalDialog(primaryStage);
        }
    }

    public void findPrevious(Stage primaryStage){
        if(!checkIfInitialized()) {
            if (textField.equals(null)) {
                findStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Text Error");
                cursorPosition = textArea.getCaretPosition() - textField.getLength()-1;
                cursorPosition = textArea.getText().lastIndexOf(textField.getText(), cursorPosition);
                if (cursorPosition <= -1) {
                    alert.setContentText("Cannot find \"" + textField.getText() + "\"");
                    alert.showAndWait();
                } else {
                    textArea.selectRange(cursorPosition, cursorPosition + textField.getLength());
                }
            }
        }else{
            createNonModalDialog(primaryStage);
        }
    }

    public boolean checkIfInitialized(){
        if(findStage == null) return true;
        return false;
    }

    public void showDialog(){
        findStage.show();
    }
}