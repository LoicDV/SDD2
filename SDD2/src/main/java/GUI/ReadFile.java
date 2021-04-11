package src.main.java.GUI;

// Import logique.
import src.main.java.logique.Point;

// Import basique.
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Consiste a lire le fichier en question.
 */
public class ReadFile {

    /**
     * Lis le fichier en parametre pour obtenir les segments et les retourne en ArrayList de tableau de 2 Points.
     * @param file File.
     * @return ArrayList de Point[].
     */
    public static ArrayList<Point[]> read(File file) {

        // Composant pour lire le fichier.
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        // Tableau -> Tableau de points a 2 dimensions.
        ArrayList<Point[]> stockTabPoints = new ArrayList<Point[]>();

        // Test avant.
        try {

            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

        }

        catch (FileNotFoundException e) {
            Exception.noFileOpen();
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

                // Creation des 2 points.
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
            Exception.noFormatEditor();
        }

        catch (NumberFormatException e) {
            Exception.noFormatEditor();
        }

        return stockTabPoints;

    }
}