package GUI;

// Import logique.
import logique.Point;

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

public class Main extends Application {
    
    // Variables globales.
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
     * @param primaryStage
     * Cree et ouvre notre fenetre principale de notre map overlay.
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
        primaryStage.getIcons().add(new Image("ressources/map_overlay.jpg"));
        primaryStage.show();
    }

    /** 
     * @return Stage.
     * Retourne le Stage de la fenetre.
     */
    public static Stage getStage() {
        return stage;
    }

    /** 
     * @return Pane.
     * Retourne le Pane de la fenetre.
     */
    public static Pane getPane() {
        return pane;
    }

    /** 
     * @param newPane Pane.
     * Remplace l'ancien Pane par le nouveau en parametre.
     */
    public void setPane(Pane newPane) {
        pane = newPane;
    }

    /** 
     * @return BorderPane.
     * Retourne le BorderPane de la fenetre.
     */
    public static BorderPane getBorderPane() {
        return root;
    }
    
    /** 
     * @return ScrollPane.
     * Retourne la ScrollPane de la fenetre.
     */
    public static ScrollPane getScrollPane() {
        return scrollPane;
    }

    /** 
     * @return File.
     * Retourne la File de la fenetre.
     */
    public static File getFile() {
        return file;
    }

    /** 
     * @param newFile File.
     * Remplace l'ancien File par le nouveau en parametre.
     */
    public static void setFile(File newFile) {
        file = newFile;
    }

    /** 
     * @return ArrayList<Point[]>.
     * Retourne le ArrayList<Point[]> de la fenetre.
     */
    public static ArrayList<Point[]> getContainers() {
        return containers;
    }
    
    /** 
     * @return ArrayList<Line>.
     * Retourne le ArrayList<Line> de la fenetre.
     */
    public static ArrayList<Line> getSet() {
        return set;
    }

    /** 
     * @return ArrayList<Circle>.
     * Retourne le ArrayList<Circle> de la fenetre.
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
     * @return DisplayContainers.
     * Retourne le DisplayContainers de la fenetre.
     */
    public static DisplayContainers getDisplayContainers() {
        return grid;
    }

    /** 
     * @param args
     * Lance notre fenetre.
     */
    public static void runApp(String[] args) {
        launch(args);
    }
}