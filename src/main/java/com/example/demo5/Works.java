package com.example.demo5;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class Works {
    VBox vBox;
    Works() {
        vBox=new VBox();
//        for (int i = 1; i <= 5; i++) {
//            CheckBox checkBox = new CheckBox("Option " + i);
//            // Add an event listener to each checkbox
//            checkBox.setOnAction(event -> handleCheckBoxAction(checkBox));
//            vBox.getChildren().add(checkBox);
//        }
        CheckBox checkBox = new CheckBox("Option ");
        // Add an event listener to each checkbox
        checkBox.setOnAction(event -> handleCheckBoxAction(checkBox));
        vBox.getChildren().add(checkBox);
    }

    private void handleCheckBoxAction(CheckBox checkBox) {
        if (checkBox.isSelected()) {
            System.out.println(checkBox.getText() + " is selected");
        } else {
            System.out.println(checkBox.getText() + " is deselected");
        }
    }
    public VBox getvBox(){
        return vBox;
    }
}
