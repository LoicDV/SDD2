package GUI;

// Import de base.
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

// Import menu.
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // Menu.
        // Onglet 1.
        Menu menu1 = new Menu("File");
        MenuItem menu1Item1 = new MenuItem("Open");
        MenuItem menu1Item2 = new MenuItem("Save");
        MenuItem menu1Item3 = new MenuItem("Save as...");

        menu1.getItems().add(menu1Item1);
        menu1.getItems().add(menu1Item2);
        menu1.getItems().add(menu1Item3);

        // Onglet 2.
        Menu menu2 = new Menu("Run");
        MenuItem menu2Item1 = new MenuItem("Compile");
        MenuItem menu2Item2 = new MenuItem("Sweep line");
        
        menu2.getItems().add(menu2Item1);
        menu2.getItems().add(menu2Item2);

        // Onglet 3.
        Menu menu3 = new Menu("Segment");
        MenuItem menu3Item1 = new MenuItem("Add");
        MenuItem menu3Item2 = new MenuItem("Change");
        MenuItem menu3Item3 = new MenuItem("Remove");

        menu3.getItems().add(menu3Item1);
        menu3.getItems().add(menu3Item2);
        menu3.getItems().add(menu3Item3);

        // Onglet 4.
        Menu menu4 = new Menu("Help");
        
        // Toute la barre.
        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        menuBar.getMenus().add(menu3);
        menuBar.getMenus().add(menu4);

        // La mettre en haut.
        VBox vBox = new VBox(menuBar);

        // Stack.
        // Mettre nos éléments dans le stack.
        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        // Fenêtre.
        // Taille de la fenêtre.
        Scene scene = new Scene(root, 300, 250);

        // Info de base de la fenetre initiale.
        stage.setTitle("Map overlay");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}