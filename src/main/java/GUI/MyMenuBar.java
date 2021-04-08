package src.main.java.GUI;

// Import JavaFX.
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MyMenuBar extends MenuBar{
    /**
     * Objet qui 
     */
    public MyMenuBar() {
        
        // Onglet 1.
        Menu menu1 = new Menu("File");
        
        // Sous-menu 1.
        MenuItem menu1Item1 = new MenuItem("Open");
        menu1Item1.setOnAction(event -> {
            new OpenScript();
        });
        MenuItem menu1Item2 = new MenuItem("Save");
        menu1Item2.setOnAction(event -> {
            new SaveFile(Main.getFile(), true);
        });
        MenuItem menu1Item3 = new MenuItem("Save as...");
        menu1Item3.setOnAction(event -> {
            new SaveFile(Main.getFile(), false);
        });
        
        // Ajout du sous-menu 1 au menu 1.
        menu1.getItems().add(menu1Item1);
        menu1.getItems().add(menu1Item2);
        menu1.getItems().add(menu1Item3);

        // Onglet 2.
        Menu menu2 = new Menu("Run");

        // Sous-menu 2.
        MenuItem menu2Item1 = new MenuItem("Compile");
        menu2Item1.setOnAction(event -> {
            new Compilation(true);
        });
        MenuItem menu2Item2 = new MenuItem("Sweep line");
        menu2Item2.setOnAction(event -> {
            new Compilation(false);
        });

        // Ajout du sous-menu 2 au menu 2.
        menu2.getItems().add(menu2Item1);
        menu2.getItems().add(menu2Item2);

        // Onglet 3.
        Menu menu3 = new Menu("Segment");

        // Sous-menu 3.
        MenuItem menu3Item1 = new MenuItem("Add");
        menu3Item1.setOnAction(event -> {
            new AddSegment();
        });
        MenuItem menu3Item2 = new MenuItem("Clear");
        menu3Item2.setOnAction(event -> {
            new ClearPane();
        });

        // Ajout du sous-menu 3 au menu 3.
        menu3.getItems().add(menu3Item1);
        menu3.getItems().add(menu3Item2);

        // Onglet 4.
        Menu menu4 = new Menu("Help");

        // Sous-menu 4.
        MenuItem menu4item1 = new MenuItem("Open a file");
        menu4item1.setOnAction(event -> {
            new Helper(1);
        });
        MenuItem menu4item2 = new MenuItem("Save a file");
        menu4item2.setOnAction(event -> {
            new Helper(2);
        });
        MenuItem menu4item3 = new MenuItem("Add a segment");
        menu4item3.setOnAction(event -> {
            new Helper(3);
        });
        MenuItem menu4item4 = new MenuItem("Remove a segment");
        menu4item4.setOnAction(event -> {
            new Helper(4);
        });
        MenuItem menu4item5 = new MenuItem("Change a segment");
        menu4item5.setOnAction(event -> {
            new Helper(5);
        });
        MenuItem menu4item6 = new MenuItem("Clear the window");
        menu4item6.setOnAction(event -> {
            new Helper(6);
        });
        MenuItem menu4item7 = new MenuItem("Get Intersection(s)");
        menu4item7.setOnAction(event -> {
            new Helper(7);
        });
        MenuItem menu4item8 = new MenuItem("Get Sweep Line");
        menu4item8.setOnAction(event -> {
            new Helper(8);
        });

        // Ajout du sous-menu 4 au menu 4.
        menu4.getItems().add(menu4item1);
        menu4.getItems().add(menu4item2);
        menu4.getItems().add(menu4item3);
        menu4.getItems().add(menu4item4);
        menu4.getItems().add(menu4item5);
        menu4.getItems().add(menu4item6);
        menu4.getItems().add(menu4item7);
        menu4.getItems().add(menu4item8);

        // Ajout de l'ensemble des menus.
        this.getMenus().add(menu1);
        this.getMenus().add(menu2);
        this.getMenus().add(menu3);
        this.getMenus().add(menu4);
    }
}
