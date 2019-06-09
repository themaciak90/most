

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;




import javafx.scene.input.MouseEvent;



public class Controller {
    static public boolean isbridgefree = true;


    @FXML
    private Label cord;


    @FXML
    private Pane tlo;


    public Controller(){

    }
    @FXML
    void initialize() {
        Circle car = new Circle(30, Color.DODGERBLUE);
        Circle car2 = new Circle(30, Color.RED);
        Circle car3 = new Circle(30, Color.WHITE);
        Circle car4 = new Circle(30, Color.VIOLET);
        car.setOpacity(0);
        car2.setOpacity(0);
        car3.setOpacity(0);
        car4.setOpacity(0);

        tlo.getChildren().addAll(car, car2, car3, car4);

        Watek w1 = new Watek(car,1,1000);
        Watek w3 = new Watek(car3, 1, 2000);
        Watek w2 = new Watek(car2,2,0);
        Watek w4 = new Watek(car4,2,3000);
        w1.start();
        w2.start();
        w3.start();
        w4.start();


    }


    @FXML
    private void displayPosition(MouseEvent event){
        cord.setText("X = " + event.getX() + "        Y = " + event.getY());
    }






}
