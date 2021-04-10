package src.main.java.GUI;

// Import basique.
import java.io.File;

// Import JavaFX.
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Classe qui gere l'ouverture des fichiers et ce qui va etre dessiner.
 */
public class OpenScript {

    /**
     * Objet pour ouvrir un fichier clear la Pane.
     */
    public OpenScript() {
        new ClearPane();
        openFile();
    }

    /**
     * Permet d'ouvrir une fenetre pour ouvrir un fichier.
     */
    public static void openFile() {

        // Composant javaFX pour ouvrir un fichier 
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Scene");
        // Extension du fichier.
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT files (*.txt)", "*.txt"));

        File file;
        // Si pas de fichier ouvert.
        if ((file = fileChooser.showOpenDialog(Main.getStage())) == null) {
            Exception.noFileOpen();
        }
        // Si un fichier selectionné.
        else {
            Main.setFile(file);
            DrawSegment.draw(file);
        }
    }
}