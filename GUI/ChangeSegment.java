package GUI;

import java.util.ArrayList;

import logique.Point;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeSegment {
    
    private static int zoom = 3;
    private static Stage stage;
    
    public ChangeSegment(String text) {
        start(text);
    }

    public static void start(String text) {
        stage = new Stage();

        Button btnChange = new Button("Change");
        Button btnRemove = new Button("Remove");
        Button btnNothing = new Button("Nothing");
        btnChange.setPrefSize(150, 50);
        btnRemove.setPrefSize(150, 50);
        btnNothing.setPrefSize(150, 50);

        String[] tabText = text.split(" ");
        Line line = new Line(Double.parseDouble(tabText[0]) * zoom,
                             Double.parseDouble(tabText[1]) * zoom,
                             Double.parseDouble(tabText[2]) * zoom,
                             Double.parseDouble(tabText[3]) * zoom);

        btnChange.setOnAction(event -> {
            stage.close();
            changeSegment(line);
        });

        btnRemove.setOnAction(event -> {
            stage.close();
            ClearPane.removeSegment(line);
        });

        btnNothing.setOnAction(event -> {
            stage.close();
        });

        HBox layout = new HBox(3);
        BorderPane layout1 = new BorderPane();
        layout1.setCenter(btnChange);
        BorderPane layout2 = new BorderPane();
        layout2.setCenter(btnRemove);
        BorderPane layout3 = new BorderPane();
        layout3.setCenter(btnNothing);

        layout.getChildren().addAll(layout1, layout2, layout3);
        layout.setSpacing(100);
        
        Scene scene = new Scene(layout, 400, 300);

        stage.setScene(scene);
        stage.setTitle("Effect on a segment");
        stage.setResizable(false);
        stage.getIcons().add(new Image("ressources/settings.jpg"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public static void changeSegment(Line line) {

        // Pour le x1.
        TextField textField_x1 = new TextField();
        textField_x1.setPromptText("x de p1");
        textField_x1.setFocusTraversable(false);
        textField_x1.setPrefSize(100, 50);


        // Pour le y1.
        TextField textField_y1 = new TextField();
        textField_y1.setPromptText("y de p1");
        textField_y1.setFocusTraversable(false);
        textField_y1.setPrefSize(100, 50);


        // Pour le x2.
        TextField textField_x2 = new TextField();
        textField_x2.setPromptText("x de p2");
        textField_x2.setFocusTraversable(false);
        textField_x2.setPrefSize(100, 50);


        // Pour le y2.
        TextField textField_y2 = new TextField();
        textField_y2.setPromptText("y de p2");
        textField_y2.setFocusTraversable(false);      
        textField_y2.setPrefSize(100, 50);

        Button button = new Button("Change");
        button.setOnAction(e -> {
            stage.close();
        });
        
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(button);
        borderPane.setPrefSize(100, 100);

        VBox layout = new VBox();
        layout.getChildren().addAll(textField_x1, textField_y1, textField_x2, textField_y2, borderPane);

        Scene scene = new Scene(layout, 400, 300);

        stage.setScene(scene);
        stage.setTitle("Change Segment");
        stage.setResizable(false);
        stage.getIcons().add(new Image("ressources/settings.jpg"));
        stage.showAndWait();

        try {
            Double point1X = Double.parseDouble(textField_x1.getText());
            Double point1Y = Double.parseDouble(textField_y1.getText());
            Double point2X = Double.parseDouble(textField_x2.getText());
            Double point2Y = Double.parseDouble(textField_y2.getText());
            if (DrawSegment.noDoubleLine(line, point1X, point1Y, point2X, point2Y, true)) {
                coordSegment(line, point1X, point1Y, point2X, point2Y);
            }
            else {
                Exception.doubleLineException();
            }
        } catch (NumberFormatException e) {
            Exception.noAddSegment();
        }
    }

    public static void coordSegment(Line line, Double point1X, Double point1Y, Double point2X, Double point2Y) {
        Line newLine = new Line(point1X * zoom, point1Y * zoom, point2X * zoom, point2Y * zoom);
        ArrayList<Line> set = Main.getSet();
        ArrayList<Point[]> containers = Main.getContainers();
        DisplayContainers grid = Main.getDisplayContainers();
        Pane pane = Main.getPane();
        for (int i = 0; i < set.size(); i++) {
            if (ClearPane.checkEquals(line, set.get(i))) {
                set.set(i, newLine);
                containers.set(i, new Point[]{new Point(point1X, point1Y), new Point(point2X, point2Y)});
                pane.getChildren().remove(i);
                pane.getChildren().add(i, newLine);;
                grid.displayContainers();
            }
        }
    }
}