package GUI;

import java.io.FileNotFoundException;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MyMenuBar extends MenuBar{
    
    public MyMenuBar() {
        
        // Onglet 1.
        Menu menu1 = new Menu("File");

        MenuItem menu1Item1 = new MenuItem("Open");
        menu1Item1.setOnAction(event -> {
            try {
                new OpenScript();
            } catch (FileNotFoundException e) {
                OpenScript.noFileOpen();
            }
        });

        MenuItem menu1Item2 = new MenuItem("Save");
        menu1Item2.setOnAction(event -> {
            new SaveFile(Main.getFile(), true);
        });

        MenuItem menu1Item3 = new MenuItem("Save as...");
        menu1Item3.setOnAction(event -> {
            new SaveFile(Main.getFile(), false);
        });

        menu1.getItems().add(menu1Item1);
        menu1.getItems().add(menu1Item2);
        menu1.getItems().add(menu1Item3);

        // Onglet 2.
        Menu menu2 = new Menu("Run");

        MenuItem menu2Item1 = new MenuItem("Compile");
        menu2Item1.setOnAction(event -> {
            new Compilation(true);
        });

        MenuItem menu2Item2 = new MenuItem("Sweep line");
        menu2Item2.setOnAction(event -> {
            new Compilation(false);
        });

        menu2.getItems().add(menu2Item1);
        menu2.getItems().add(menu2Item2);

        // Onglet 3.
        Menu menu3 = new Menu("Segment");

        MenuItem menu3Item1 = new MenuItem("Add");
        menu3Item1.setOnAction(event -> {
            new AddSegment();
        });
        
        MenuItem menu3Item2 = new MenuItem("Clear");
        menu3Item2.setOnAction(event -> {
            new ClearPane();
        });

        menu3.getItems().add(menu3Item1);
        menu3.getItems().add(menu3Item2);

        // Onglet 4.
        Menu menu4 = new Menu("Help");

        this.getMenus().add(menu1);
        this.getMenus().add(menu2);
        this.getMenus().add(menu3);
        this.getMenus().add(menu4);
    }
}
