package src.main.java.GUI;

// Import logique.
import src.main.java.logique.Point;

// Import basique.
import java.util.ArrayList;
import java.io.File;

// Import JavaFX.
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

/**
 * Fenetre principale de notre programme.
 */
public class Main extends Application {

    // Variables instances.
    private static ArrayList<Point[]> containers;
    private static ArrayList<Line> set;
    private static ArrayList<Circle> setCircle;
    private static Stage stage;
    private static File file;
    private static BorderPane root;
    private static Pane pane;
    private static DisplayContainers grid;
    private static ScrollPane scrollPane;


    /**
     * Cree et ouvre notre fenetre principale de notre map overlay.
     * @param primaryStage Stage.
     */
    @Override
    public void start(Stage primaryStage) {

        containers = new ArrayList<Point[]>();
        set = new ArrayList<Line>();
        setCircle = new ArrayList<Circle>();
        stage = primaryStage;
        file = null;

        // Mettre la barre d'onglet en haut.
        VBox vBox = new VBox(new MyMenuBar());

        // Zone de dessin.
        scrollPane = new ScrollPane();
        pane = new Pane();
        scrollPane.setContent(pane);

        // Zone de changement de Segment.
        grid = new DisplayContainers();

        // Mettre nos elements dans la fenetre.
        root = new BorderPane();
        root.setCenter(scrollPane);
        root.setTop(vBox);
        root.setRight(grid);

        // Contenu de la fenetre.
        Scene scene = new Scene(root, 800, 700);

        // Settings de la fenetre.
        primaryStage.setTitle("Map overlay");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("map_overlay.jpg"));
        primaryStage.show();
    }

    /**
     * Retourne le Stage de la fenetre.
     * @return Stage.
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Retourne le Pane de la fenetre.
     * @return Pane.
     */
    public static Pane getPane() {
        return pane;
    }

    /**
     * Remplace l'ancien Pane par le nouveau en parametre.
     * @param newPane Pane.
     */
    public void setPane(Pane newPane) {
        pane = newPane;
    }

    /**
     * Retourne le BorderPane de la fenetre.
     * @return BorderPane.
     */
    public static BorderPane getBorderPane() {
        return root;
    }

    /**
     * Retourne la ScrollPane de la fenetre.
     * @return ScrollPane.
     */
    public static ScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * Retourne la File de la fenetre.
     * @return File.
     */
    public static File getFile() {
        return file;
    }

    /**
     * Remplace l'ancien File par le nouveau en parametre.
     * @param newFile File.
     */
    public static void setFile(File newFile) {
        file = newFile;
    }

    /**
     * Retourne le ArrayList de tableau de 2 Points de la fenetre.
     * @return ArrayList de Point[].
     */
    public static ArrayList<Point[]> getContainers() {
        return containers;
    }

    /**
     * Retourne le ArrayList de Line de la fenetre.
     * @return ArrayList de Line.
     */
    public static ArrayList<Line> getSet() {
        return set;
    }

    /**
     * Retourne le ArrayList de Circle de la fenetre.
     * @return ArrayList de Circle.
     */
    public static ArrayList<Circle> getSetCircle() {
        return setCircle;
    }

    /**
     * Remplace l'ancien ArrayList par une nouvelle vide.
     */
    public static void setSetCircle() {
        setCircle = new ArrayList<Circle>();
    }

    /**
     * Retourne le DisplayContainers de la fenetre.
     * @return DisplayContainers.
     */
    public static DisplayContainers getDisplayContainers() {
        return grid;
    }

    /**
     * Lance notre fenetre.
     * @param args String[].
     */
    public static void runApp(String[] args) {
        launch(args);
    }
}