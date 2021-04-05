package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.FileNotFoundException;

public class OpenScript {

    public OpenScript() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Scene");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(Main.getStage());
        Main.setFile(file);
        DrawSegment.draw(file);
    }

    public static void noFileOpen() {
        AlertType alertType = AlertType.WARNING;
        Alert alert = new Alert(alertType, "Warning in place");
        alert.getDialogPane().setContentText("You don't have open file !");
        alert.getDialogPane().setHeaderText("File Error");
        alert.showAndWait();
    }
}