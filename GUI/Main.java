package GUI;


import java.io.File;
import java.util.ArrayList;
import logique.Point;

// Import de base.
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

// Import menu.
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

// Import dessin
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Main extends Application {

    private static ArrayList<Point[]> containers;
    private static ArrayList<Line> set;
    private static ArrayList<Circle> setCircle;
    private static Stage stage;
    private static File file;
    private static BorderPane root;
    private static Pane pane;
    private static DisplayContainers grid;

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
        pane = new Pane();

        // Zone de changement de Segment.
        grid = new DisplayContainers();

        // Mettre nos éléments dans la fenêtre.
        root = new BorderPane();
        root.setCenter(pane);
        root.setTop(vBox);
        root.setRight(grid);

        // Fenêtre.
        // Taille de la fenêtre.
        Scene scene = new Scene(root, 800, 700);

        // Info de base de la fenetre initiale.
        primaryStage.setTitle("Map overlay");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("ressources/map_overlay.jpg"));
        primaryStage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static Pane getPane() {
        return pane;
    }

    public void setPane(Pane newPane) {
        pane = newPane;
    }

    public static BorderPane getBorderPane() {
        return root;
    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File newFile) {
        file = newFile;
    }

    public static ArrayList<Point[]> getContainers() {
        return containers;
    }

    public static ArrayList<Line> getSet() {
        return set;
    }

    public static ArrayList<Circle> getSetCircle() {
        return setCircle;
    }

    public static void setSetCircle() {
        setCircle = new ArrayList<Circle>();
    }

    public static DisplayContainers getDisplayContainers() {
        return grid;
    }

    public static void runApp(String[] args) {
        launch(args);
    }
}