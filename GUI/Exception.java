package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Exception extends Throwable {

    public static void noAddSegment() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("No numbers in the field !");
        alert.getDialogPane().setHeaderText("Numbers Error");
        alert.showAndWait();
    }

    public static void noFileOpen() {
        AlertType alertType = AlertType.WARNING;
        Alert alert = new Alert(alertType);
        alert.setTitle("WARNING");
        alert.getDialogPane().setContentText("You don't have open file !");
        alert.getDialogPane().setHeaderText("File Error");
        alert.showAndWait();
    }

    public static void doubleLineException() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("You can't add this Segment because you overlap another Segment !");
        alert.getDialogPane().setHeaderText("AddSegment Warning");
        alert.showAndWait();
    }

    public static void noFormatEditor() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("The format in the file are not good !");
        alert.getDialogPane().setHeaderText("Editor Error");
        alert.showAndWait();
    }

    public static void noSaveDone() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("You can't save because you don't have opened file before !");
        alert.getDialogPane().setHeaderText("Save Warning");
        alert.showAndWait();
    }

    public static void noFileTake() {
        AlertType alertType = AlertType.WARNING;
        Alert alert = new Alert(alertType);
        alert.setTitle("WARNING");
        alert.getDialogPane().setContentText("No file has been chose !");
        alert.getDialogPane().setHeaderText("File not found");
        alert.showAndWait();
    }

    public static void noGoodFile() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("The file must be a txt !");
        alert.getDialogPane().setHeaderText("File not file.txt");
        alert.showAndWait();
    }
}