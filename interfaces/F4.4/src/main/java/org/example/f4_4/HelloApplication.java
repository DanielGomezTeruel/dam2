package org.example.f4_4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        VBox mainContainer = new VBox();
        mainContainer.setFillWidth(true);
        root.setCenter(mainContainer);


        TextField display = new TextField();
        display.setFont(Font.font("Arial", 18));
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);

        Button equalsButton = new Button("=");

        equalsButton.setOnAction(event -> {
            try {
                double result = ParserCalculos.calculate(display.getText());

                display.setText(String.valueOf(result));

            } catch (Exception e) {
                display.setText("Error");
            }
        });

        GridPane displayContainer = new GridPane();

        displayContainer.setPadding(new Insets(10, 2, 0, 7));
        displayContainer.setHgap(10);

        ColumnConstraints displayColumn = new ColumnConstraints();
        displayColumn.setPercentWidth(75);

        ColumnConstraints equalsColumn = new ColumnConstraints();
        equalsColumn.setPercentWidth(25);

        displayContainer.getColumnConstraints().addAll(
                displayColumn,
                equalsColumn
        );

        displayContainer.add(display, 0, 0);
        displayContainer.add(equalsButton, 1, 0);

        display.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        equalsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        formato(equalsButton);

        equalsButton.setScaleX(0.85);


        GridPane buttonsContainer = new GridPane();

        String[][] buttons = {
                {"C", "()", "%", "⌫"},
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"+/-", "0", ",", "+"}
        };

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {

                Button button = new Button(buttons[row][col]);

                formato(button);

                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                button.setScaleX(0.85);
                button.setScaleY(0.85);

                button.setOnAction(event -> {
                    String value = button.getText();

                    switch (value) {
                        case "C":
                            display.clear();
                            break;

                        case "⌫":
                            if (!display.getText().isEmpty()) {
                                display.deleteText(
                                        display.getLength() - 1,
                                        display.getLength()
                                );
                            }
                            break;

                        case "()":
                            String current = display.getText();

                            if (current.isEmpty()) {
                                display.appendText("(");
                            } else {
                                char last = current.charAt(current.length() - 1);

                                if (Character.isDigit(last) || last == ')') {
                                    display.appendText(")");
                                } else {
                                    display.appendText("(");
                                }
                            }
                            break;

                        default:
                            display.appendText(value);
                            break;
                    }
                });

                buttonsContainer.add(button, col, row);
            }
        }

        for (int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(25);
            column.setHgrow(Priority.ALWAYS);

            buttonsContainer.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 5; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(20);
            row.setVgrow(Priority.ALWAYS);

            buttonsContainer.getRowConstraints().add(row);
        }

        VBox.setVgrow(buttonsContainer, Priority.ALWAYS);
        buttonsContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


        buttonsContainer.setHgap(5);
        buttonsContainer.setVgap(5);


        mainContainer.setSpacing(10);

        mainContainer.getChildren().addAll(
                displayContainer,
                buttonsContainer
        );

        Scene scene = new Scene(root, 400, 500);

        stage.setTitle("Calculadora");
        stage.setScene(scene);
        stage.show();
    }

    private void formato(Button button) {
        switch(button.getText()){
            case "C", "()","%","+/-",",":
                button.setStyle(
                        "-fx-background-color: #FFE6A1;" +
                                "-fx-text-fill: black;" +
                                "-fx-font-family: Arial;" +
                                "-fx-font-size: 24px;"
                );
                break;
            case "⌫":
                button.setStyle(
                        "-fx-background-color: #FF6989;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-family: Arial;" +
                                "-fx-font-size: 24px;"
                );
                break;

            case "/","*","-","+":
                button.setStyle(
                        "-fx-background-color: #79c1c8;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-family: Arial;" +
                                "-fx-font-size: 32px;"
                );
                break;
            case "=":
                button.setStyle(
                        "-fx-background-color: #73FF85;" +
                                "-fx-text-fill: black;" +
                                "-fx-font-family: Arial;" +
                                "-fx-font-size: 32px;" +
                                "-fx-font-weight: bold"
                );
                break;

            default:
                button.setStyle(
                        "-fx-background-color: #939393;" +
                                "-fx-text-fill: white;" +
                                "-fx-font-family: Arial;" +
                                "-fx-font-size: 24px;"
                );
                break;
        }
    }
}

