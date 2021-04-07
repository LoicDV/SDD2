package GUI;

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

        // Ajout de l'ensemble des menus.
        this.getMenus().add(menu1);
        this.getMenus().add(menu2);
        this.getMenus().add(menu3);
        this.getMenus().add(menu4);
    }
}
