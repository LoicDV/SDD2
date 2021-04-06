package GUI;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

public class OpenScript {

    public OpenScript() {
        openFile();
    }

    public static void openFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Scene");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file;
        if ((file = fileChooser.showOpenDialog(Main.getStage())) == null) {
            Exception.noFileOpen();
        }
        else {
            Main.setFile(file);
            DrawSegment.draw(file);
        }
    }
}