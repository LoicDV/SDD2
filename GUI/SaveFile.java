package GUI;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import logique.Point;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SaveFile {

    // boolean qui dit si on sauvegarde sur le meme fichier ou non.
    // true = même fichier // false = fichier différent.
    public SaveFile(File file, boolean other) {
        if (other) {
            try {
                save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Scene");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT files (*.txt)", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(Main.getStage());
            try {
                save(selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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