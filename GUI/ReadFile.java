package GUI;

import logique.Point;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// Lecture de fichier.
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

// Exception liée au fichier.
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFile {
    
    public static ArrayList<Point[]> read(File file) {

        // Composant pour lire le fichier.
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        
        // Tableau -> Tableau de points à 2 dimensions.
        ArrayList<Point[]> stockTabPoints = new ArrayList<Point[]>();

        // Test avant.
        try {
            
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

        }

        catch (FileNotFoundException e) {
            OpenScript.noFileOpen();
        }

        // Code.
        try {
  
            String line = bufferedReader.readLine();

            while (line != null) {

                // exemple : "119.16 80.47 156.06 187.22"
                String[] tabStrings = line.split(" ");
                Double[] tabDoubles = new Double[tabStrings.length];

                // Passage String -> Double.
                for (int i = 0; i < tabStrings.length; i++) {  

                    tabDoubles[i] = Double.parseDouble(tabStrings[i]);
                
                }

                // Création des 2 points.
                Point p1 = new Point(tabDoubles[0], tabDoubles[1]);
                Point p2 = new Point(tabDoubles[2], tabDoubles[3]);

                // Tableau de points.
                Point[] tabPoints = new Point[] {p1, p2};

                stockTabPoints.add(tabPoints);

                // Prendre la prochaine ligne.
                line = bufferedReader.readLine();

            }

            bufferedReader.close();

        }

        catch (IOException e) {
            noFormatEditor();
        }

        catch (NumberFormatException e) {
            noFormatEditor();
        }

        return stockTabPoints;

    }

    public static void noFormatEditor() {
        AlertType alertType = AlertType.ERROR;
        Alert alert = new Alert(alertType, "Warning in place");
        alert.getDialogPane().setContentText("The format in the file are not good !");
        alert.getDialogPane().setHeaderText("Editor Error");
        alert.showAndWait();
    }
}