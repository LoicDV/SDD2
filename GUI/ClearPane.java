package GUI;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import logique.Point;

public class ClearPane {

    public ClearPane() {
        ArrayList<Point[]> containers = Main.getContainers();
        Main.getPane().getChildren().clear();
        ArrayList<Circle> setCircle = Main.getSetCircle();
        int lengthCircle = setCircle.size();
        for (int i = 0; i < lengthCircle; i++) {
            setCircle.remove(i);
        }
        int lengthSegment = containers.size();
        for (int i = 0; i < lengthSegment; i++) {
            containers.remove(0);
            Main.getSet().remove(0);
        }
        Main.setFile(null);
        DisplayContainers grid = Main.getDisplayContainers();
        grid.displayContainers();
    }

    public static void removeSegment(Line line) {
        ArrayList<Line> set = Main.getSet();
        for (int i = 0; i < set.size(); i++) {
            if (checkEquals(set.get(i), line)) {
                Main.getContainers().remove(i);
                Node object = Main.getPane().getChildren().get(i);
                Main.getPane().getChildren().remove(object);
                set.remove(i);
                DisplayContainers grid = Main.getDisplayContainers();
                grid.displayContainers();
            }
        }
    }

    public static boolean checkEquals(Line line1, Line line2) {
        if (((Math.abs(line1.getStartX() - line2.getStartX()) <= 0.00000001) &&
            (Math.abs(line1.getStartY() - line2.getStartY()) <= 0.00000001) &&
            (Math.abs(line1.getEndX() - line2.getEndX()) <= 0.00000001) &&
            (Math.abs(line1.getEndY() - line2.getEndY()) <= 0.00000001)) ||
            ((Math.abs(line1.getStartX() - line2.getEndX()) <= 0.00000001) &&
            (Math.abs(line1.getStartY() - line2.getEndY()) <= 0.00000001) &&
            (Math.abs(line1.getEndX() - line2.getStartX()) <= 0.00000001) &&
            (Math.abs(line1.getEndY() - line2.getStartY()) <= 0.00000001))) {
            return true;
        }
        else {
            return false;
        }
    }
}