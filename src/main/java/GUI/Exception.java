package src.main.java.GUI;

// Import JavaFX.
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Exception extends Throwable {

    /**
     * Exception sur le fait de ne pas avoir su rajouter de segment car pas de donnee.
     */
    public static void noAddSegment() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("No numbers in the field !");
        alert.getDialogPane().setHeaderText("Numbers Error");
        alert.showAndWait();
    }

    /**
     * Exception sur le fait de ne pas avoir su ouvrir de fichier car aucun selectionne.
     */
    public static void noFileOpen() {
        AlertType alertType = AlertType.WARNING;
        Alert alert = new Alert(alertType);
        alert.setTitle("WARNING");
        alert.getDialogPane().setContentText("You don't have open file !");
        alert.getDialogPane().setHeaderText("File Error");
        alert.showAndWait();
    }

    /**
     * Exception sur le fait de ne pas avoir su rajouter de segment car overlap un autre.
     */
    public static void doubleLineException() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("You can't add this Segment because you overlap another Segment !");
        alert.getDialogPane().setHeaderText("AddSegment Warning");
        alert.showAndWait();
    }

    /**
     * Exception sur le fait de ne pas avoir su ouvir le fichier car pas le bon format a l'interieur.
     */
    public static void noFormatEditor() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("The format in the file are not good !");
        alert.getDialogPane().setHeaderText("Editor Error");
        alert.showAndWait();
    }

    /**
     * Exception sur le fait de ne pas avoir su sauvegarder le fichier car pas de fichier ouvert precedemment.
     */
    public static void noSaveDone() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("You can't save because you don't have opened file before !");
        alert.getDialogPane().setHeaderText("Save Warning");
        alert.showAndWait();
    }

    /**
     * Exception sur le fait de ne pas avoir su sauvegarder car pas ouvert de fichier au prealable.
     */
    public static void noFileTake() {
        AlertType alertType = AlertType.WARNING;
        Alert alert = new Alert(alertType);
        alert.setTitle("WARNING");
        alert.getDialogPane().setContentText("No file has been chose !");
        alert.getDialogPane().setHeaderText("File not found");
        alert.showAndWait();
    }

    /**
     * Exception sur le fait de ne pas avoir su ouvrir le fichier car pas le bon format.
     */
    public static void noGoodFile() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType);
        alert.setTitle("ERROR");
        alert.getDialogPane().setContentText("The file must be a txt !");
        alert.getDialogPane().setHeaderText("File not file.txt");
        alert.showAndWait();
    }
}