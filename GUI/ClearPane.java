package GUI;

import java.util.ArrayList;

import javafx.scene.shape.Line;

public class ClearPane {

    public ClearPane() {
        
    }

    public static void removeSegment(Line line) {
        ArrayList<Line> set = Main.getSet();
        for (int i = 0; i < set.size(); i++) {
            System.out.println("set.get(i) : " + set.get(i));
            System.out.println("line : " + line);
            System.out.println("boolean : " + checkEquals(set.get(i), line));
            if (checkEquals(set.get(i), line)) {
                Main.getContainers().remove(i);
                Main.getPane().getChildren().remove(line);
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