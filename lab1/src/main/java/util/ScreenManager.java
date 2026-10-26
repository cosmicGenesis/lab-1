package util;

import java.io.IOException;
import java.util.Stack;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab1.UsersApp;

public class ScreenManager {
    private static Stage stage;

    public static Stack<Scene> prevStack = new Stack<Scene>();
    public static Stack<String> prevHeaderStack = new Stack<String>();

    public static void setStage(Stage newStage) {
        stage = newStage;
    }


    //sets the curret Scene as the previous scene
    public static void setAsPrev() {
        prevStack.push(stage.getScene());
        prevHeaderStack.push(stage.getTitle());
    }

    public static void goBack() {
        if (!prevStack.isEmpty() && !prevHeaderStack.isEmpty()) {
            stage.setScene(prevStack.pop());
            stage.setTitle(prevHeaderStack.pop());
        } else {
            Platform.exit();
        }
    }

    public static void goToScreen(String fxmlSource,String title) throws IOException{
        setAsPrev();
        Parent root = FXMLLoader.load(UsersApp.class.getResource(fxmlSource));
        Scene scene = new Scene(root);

        stage.setTitle(title);
        stage.setScene(scene);
    }
}
