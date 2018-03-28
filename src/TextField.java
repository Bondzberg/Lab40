

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TextField extends Application
{
    private javafx.scene.control.TextField textField;
    private OuterSpace outerSpace;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage = new Stage();
        Button btn = new Button();
        btn.setText("ENTER");
        btn.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                outerSpace.interpret(textField.getText());
            }
        });
        textField = new javafx.scene.control.TextField();
        textField.setText("Enter Movement Commands here");
        StackPane borderPane = new StackPane();
        borderPane.getChildren().addAll(btn, textField);
        primaryStage.setScene(new Scene(borderPane, 300, 200));
        primaryStage.show();
    }

    public void setOuterSpace(OuterSpace outerSpace) {
        this.outerSpace = outerSpace;
    }

    public javafx.scene.control.TextField getTextField() {
        return textField;
    }
}
