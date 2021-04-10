package src.main.java.GUI;

// Import logique.
import src.main.java.logique.Point;

// Import basique.
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

// Import JavaFX.
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Classe qui gere la sauvegarde au sein du programme.
 */
public class SaveFile {

    /**
     * Objet qui permet de gerer la sauvegarde de fichier.
     * boolean qui dit si on sauvegarde sur le meme fichier ou non.
     * true = meme fichier // false = fichier different.
     * @param file File.
     * @param other boolean.
     */
    public SaveFile(File file, boolean other) {
        // Simple sauvegarde.
        if (other) {
            try {
                // Si pas ouvert de fichier au prealable.
                if (Main.getFile() == null) {
                    Exception.noSaveDone();
                }
                // Si on a deja ouvert un fichier au prealable.
                else {
                    save(file);
                }
            } catch (IOException e) {
                Exception.noFileTake();
            }
        }
        // Sauvegarde sous...
        else {
            // Selection du fichier.
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Scene");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT files (*.txt)", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(Main.getStage());
            try {
                // Si pas de fichier selectionne.
                if (selectedFile == null) {
                    Exception.noSaveDone();
                }
                // Si fichier selectionne.
                else {
                    save(selectedFile);
                }
            } catch (IOException e) {
                Exception.noGoodFile();
            }
        }
    }

    /** 
     * Ecrit dans le fichier les segments presents dans la fenetre.
     * @param file File.
     * @throws IOException IOException.
     */
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