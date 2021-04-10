package src.main.java.GUI;

// Import JavaFX.
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Classe qui gere les aides du programmes.
 */
public class Helper {

    /**
     * @param num int.
     * Cree une fenetre d'aide pour l'utilisateur.
     */
    public Helper(int num) {
        // Notre fenetre.
        Stage stage = new Stage();

        // Pour afficher notre text.
        VBox vBox = new VBox();
 
        // Differents aides pouvant etre selectionnes.
        switch (num) {
            // Ouvrir un fichier.
            case 1:
                Text case1text1 = new Text("1. Reach the menu titled 'File'.");
                Text case1text2 = new Text("2. Select 'Open'.");
                Text case1text3 = new Text("3. Find a file with .txt extension you want to open.");
                Text case1text4 = new Text("The file will be open in the window with all segments.");
                vBox.getChildren().addAll(case1text1, case1text2, case1text3, case1text4);
                break;
            // Sauvegarder un fichier.
            case 2:
                Text case2text1 = new Text("1. Reach the menu titled 'File'.");
                Text case2text2 = new Text("2. - If you haven't opened file before, select 'Save as...'.");
                Text case2text3 = new Text("    - If you already have opened a file and you want to save on this file, select 'Save'.");
                Text case2text4 = new Text("    - If you already have opened a file and you don't want to save on this file, select 'Save as...'");
                Text case2text5 = new Text("3. ONLY IF YOU PICKED 'Save as...'");
                Text case2text6 = new Text("Find a file with .txt extension you want to save.");
                vBox.getChildren().addAll(case2text1, case2text2, case2text3, case2text4, case2text5, case2text6);
                break;
            // Ajouter un segment.
            case 3:
                Text case3text1 = new Text("1. Reach the menu titled 'Segment'");
                Text case3text2 = new Text("2. Select 'Add'");
                Text case3text3 = new Text("3. Write numbers in the 4 text fields and click on 'add'.");
                Text case3text4 = new Text("A check will be done on background to see if you overlap another segment already in the Pane.");
                vBox.getChildren().addAll(case3text1, case3text2, case3text3, case3text4);
                break;
            // Enlever un segment.
            case 4:
                Text case4text1 = new Text("1. Look on the list of segments on your right and click on the segment you want to remove.");
                Text case4text2 = new Text("2. A pop-up will appear and you have to click on the button 'Change'.");
                Text case4text3 = new Text("3. Write numbers in the 4 text fields and click on 'Change'.");
                Text case4text4 = new Text("A check will be done on background to see if you overlap another segment already in the Pane.");
                vBox.getChildren().addAll(case4text1, case4text2, case4text3, case4text4);
                break;
            // Ajuster un segment.
            case 5:
                Text case5text1 = new Text("1. Look on the list of segments on your right and click on the segment you want to change.");
                Text case5text2 = new Text("2. A pop-up will appear and you have to click on the button 'Change'.");
                Text case5text3 = new Text("3. Write numbers in the 4 text fields and click on 'Change'.");
                Text case5text4 = new Text("A check will be done on background to see if you overlap another segment already in the Pane.");
                vBox.getChildren().addAll(case5text1, case5text2, case5text3, case5text4);
                break;
            // Nettoyer la fenetre.
            case 6:
                Text case6text1 = new Text("1. Reach the menu titled 'Segment'.");
                Text case6text2 = new Text("2. Select clear.");
                Text case6text3 = new Text("Will restore all your window.");
                vBox.getChildren().addAll(case6text1, case6text2, case6text3);
                break;
            // Avoir les intersections.
            case 7:
                Text case7text1 = new Text("1. Reach the menu titled 'Run'.");
                Text case7text2 = new Text("2. Select Compile.");
                Text case7text3 = new Text("Will find all intersection points and display them in a pop-up.");
                vBox.getChildren().addAll(case7text1, case7text2, case7text3);
                break;
            // Avoir la sweep line.
            case 8:
                Text case8text1 = new Text("1. Reach the menu titled 'Run'.");
                Text case8text2 = new Text("2. Select Sweep Line.");
                Text case8text3 = new Text("Will find all intersection points and display them in a pop-up.");
                Text case8text4 = new Text("3. With the pop-up, 2 buttons will appear inside.");
                Text case8text5 = new Text(" '>' for one step // '>>>' to finish the compile and get all intersections.");
                vBox.getChildren().addAll(case8text1, case8text2, case8text3, case8text4, case8text5);
                break;
        }
 
        // Contenu de la fenetre
        Scene scene = new Scene(vBox, 600, 100);
         
        // Settings de la fenetre.
        stage.setTitle("Helper");
        stage.getIcons().add(new Image("question.jpg"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
}