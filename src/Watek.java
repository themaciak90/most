import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;


import java.util.concurrent.Semaphore;


public class Watek extends Thread {

    volatile private Circle circle;
    volatile static Semaphore wjazd = new Semaphore(1);
    volatile static Semaphore wjazd2 = new Semaphore(1);
    volatile private PathTransition animate = new PathTransition();
    volatile int ktora_strona;
    volatile private long sleep;


    public Watek(Circle circle, int ktora_strona, long sleep) {
        this.circle = circle;
        this.ktora_strona = ktora_strona;
        this.sleep = sleep;
    }


    private void checkiffree(){

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(!Controller.isbridgefree){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if(ktora_strona == 1){
                    Controller.isbridgefree = false;
                    wjazd.release();
                    Line line = new Line();
                    circle.setCenterX(550);
                    circle.setCenterY(300);
                    line.setStartX(circle.getCenterX());
                    line.setStartY(circle.getCenterY());
                    line.setEndX(375);
                    line.setEndY(300);
                    PathTransition animate = new PathTransition();
                    animate.setNode(circle);
                    animate.setDuration(Duration.seconds(2));
                    animate.setPath(line);
                    animate.setOnFinished(event1 -> zjazdzmostunalewastrone());
                    animate.play();
                }
                else if(ktora_strona == 2) {
                    Controller.isbridgefree = false;
                    Line line = new Line();
                    wjazd2.release();
                    circle.setCenterX(375);
                    circle.setCenterY(300);
                    line.setStartX(circle.getCenterX());
                    line.setStartY(circle.getCenterY());
                    line.setEndX(550);
                    line.setEndY(300);
                    PathTransition animate = new PathTransition();
                    animate.setNode(circle);
                    animate.setDuration(Duration.seconds(2));
                    animate.setPath(line);
                    animate.setOnFinished(event2 -> zjazdzmostunaprawo());
                    animate.play();
                }

            }
        });
        new Thread(sleeper).start();
    }

    @Override
    public void run() {
        //for (int i = 0; i < 10; i++) {
            if (ktora_strona == 1) {
                try{
                    Thread.sleep(this.sleep);
                    wjazdzprawejstrony();
                }
                    catch(InterruptedException e ){}

            } else if (ktora_strona == 2) {
                try{
                    Thread.sleep(this.sleep);
                    wjazdzlewejstrony();
                }
                catch(InterruptedException e ){}
            }
       // }

    }


    private void wjazdzprawejstrony() {
        try {
            wjazd.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        circle.setOpacity(1);
        circle.setCenterX(870);
        circle.setCenterY(240);
        Line line = new Line();
        line.setStartX(circle.getCenterX());
        line.setStartY(circle.getCenterY());
        line.setEndX(640);
        line.setEndY(240);
        PathTransition animate = new PathTransition();
        animate.setNode(circle);
        animate.setDuration(Duration.seconds(3));
        animate.setPath(line);
        animate.setOnFinished(event -> checkiffree());
        animate.play();

    }

    /*private void wjazdzprawejnamost() {

        checkiffree();


    }*/

    private void zjazdzmostunalewastrone() {
        Line line = new Line();
        circle.setCenterX(290);
        circle.setCenterY(240);
        line.setStartX(circle.getCenterX());
        line.setStartY(circle.getCenterY());
        line.setEndX(30);
        line.setEndY(240);
        PathTransition animate = new PathTransition();
        animate.setNode(circle);
        animate.setDuration(Duration.seconds(3));
        animate.setPath(line);
        Controller.isbridgefree = true;
        animate.play();
        animate.setOnFinished(event -> circle.setOpacity(0));


    }

    private void wjazdzlewejstrony() {
        circle.setOpacity(1);
        try {
            wjazd2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        circle.setCenterX(30);
        circle.setCenterY(360);
        Line line = new Line();
        line.setStartX(circle.getCenterX());
        line.setStartY(circle.getCenterY());
        line.setEndX(290);
        line.setEndY(360);
        PathTransition animate = new PathTransition();
        animate.setNode(circle);
        animate.setDuration(Duration.seconds(3));
        animate.setPath(line);
        animate.setOnFinished(event -> checkiffree());
        animate.play();


    }


    private void zjazdzmostunaprawo() {
        Line line = new Line();
        circle.setCenterX(375);
        circle.setCenterY(300);
        line.setStartX(640);
        line.setStartY(360);
        line.setEndX(870);
        line.setEndY(360);
        PathTransition animate = new PathTransition();
        animate.setNode(circle);
        animate.setDuration(Duration.seconds(3));
        animate.setPath(line);
        Controller.isbridgefree = true;
        animate.play();
        animate.setOnFinished(event -> circle.setOpacity(0));
    }


}
