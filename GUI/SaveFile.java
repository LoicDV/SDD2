package GUI;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import logique.Point;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SaveFile {

    // boolean qui dit si on sauvegarde sur le meme fichier ou non.
    // true = même fichier // false = fichier différent.
    public SaveFile(File file, boolean other) {
        if (other) {
            try {
                if (Main.getFile() == null) {
                    noSaveDone();
                }
                else {
                    save(file);
                }
            } catch (IOException e) {
                noFileTake();
            }
        }
        else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Scene");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT files (*.txt)", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(Main.getStage());
            try {
                if (Main.getFile() == null) {
                    noSaveDone();
                }
                else {
                    save(selectedFile);
                }
            } catch (IOException e) {
                noGoodFile();
            }
        }
    }

    public void noSaveDone() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType, "Warning in place");
        alert.getDialogPane().setContentText("You can't save because you don't have opened file before !");
        alert.getDialogPane().setHeaderText("Save Warning");
        alert.showAndWait();
    }

    public void noFileTake() {
        AlertType alertType = AlertType.WARNING;
        Alert alert = new Alert(alertType, "Warning in place");
        alert.getDialogPane().setContentText("No file has been chose !");
        alert.getDialogPane().setHeaderText("File not found");
        alert.showAndWait();
    }

    public void noGoodFile() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType, "Warning in place");
        alert.getDialogPane().setContentText("The file must be a txt !");
        alert.getDialogPane().setHeaderText("File not file.txt");
        alert.showAndWait();
    }

    public void save(File file) throws IOException{
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ArrayList<Point[]> containers = Main.getContainers();
        for (Point[] tab : containers) {
            String string = String.valueOf(tab[0].getX()) + " " + String.valueOf(tab[0].getY()) + " " + String.valueOf(tab[1].getX()) + " " + String.valueOf(tab[1].getY()) + "\n";
            bufferedWriter.write(string);
        }
        bufferedWriter.close();
    }
}