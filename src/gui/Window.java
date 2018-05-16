/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Pablo Rojas Martínez
 */
import domain.Consumer;
import domain.Producer;
import domain.SynchronizedBuffer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.BufferOverflowException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Window extends Application implements Runnable {

    private Thread thread;
    private Scene scene;
    private Pane pane;
    private Canvas canvas;
    private Image image, image2, image3;

    private SynchronizedBuffer sharedLocation;
    private Producer producer;
    private Consumer consumer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Graphics and Threads");
        initComponents(primaryStage);
        primaryStage.setOnCloseRequest(exit);
        primaryStage.show();
    }

    @Override
    public void run() {

        long start;
        long elapsed;
        long wait;
        int fps = 30;
        long time = 1000 / fps;

        while (true) {
            try {
                start = System.nanoTime();
                elapsed = System.nanoTime() - start;
                wait = time - elapsed / 1000000;
                Thread.sleep(wait);
                GraphicsContext gc = this.canvas.getGraphicsContext2D();
                draw(gc);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void initComponents(Stage primaryStage) {
        try {
            this.pane = new Pane();
            this.scene = new Scene(this.pane, 1400, 700);
            this.canvas = new Canvas(1400, 700);
            this.image = new Image(new FileInputStream("src/assets/field1.png"));
            this.image2 = new Image(new FileInputStream("src/assets/ball.png"));
            this.image3 = new Image(new FileInputStream("src/assets/bag.png"));
            this.sharedLocation = new SynchronizedBuffer();
            this.pane.getChildren().add(this.canvas);

            primaryStage.setScene(this.scene);

            //Inicializamos cada hilo (personaje) y lo iniciamos
            // SynchronizedBuffer sharedLocation = new SynchronizedBuffer();
            this.producer = new Producer(sharedLocation);
            this.producer.start();

            this.consumer = new Consumer(sharedLocation);
            this.consumer.start();

            this.thread = new Thread(this);
            this.thread.start();
        } catch (FileNotFoundException ex) {
        } catch (BufferOverflowException ex) {
        }
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 1400, 700);
        gc.drawImage(this.image, 0, 0);
        gc.drawImage(this.producer.getImage(), this.producer.getX(), this.producer.getY());
        gc.drawImage(this.consumer.getImage(), this.consumer.getX(), this.consumer.getY());
        gc.drawImage(this.image3, 70, 25);
        gc.drawImage(this.image3, 1140, 25);
        if (sharedLocation.ball() == 1) {
            gc.drawImage(this.image2, 700, 70);
        }
    }

    EventHandler<WindowEvent> exit = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            System.exit(0);
        }
    };
}
