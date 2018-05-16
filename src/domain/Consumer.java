/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Pablo Rojas Martínez
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author Pablo Rojas Martínez
 */
public class Consumer extends Thread {

    private SynchronizedBuffer sharedLocation; // reference to shared object
    private ArrayList<Image> sprite;
    private int x;
    private int y;
    private int imgNum;
    private Image image;

    // constructor
    public Consumer(SynchronizedBuffer shared) throws FileNotFoundException {
        super("Consumer");
        this.x = x;
        this.y = y;
        this.imgNum = imgNum;
        this.sprite = new ArrayList<Image>();
        sharedLocation = shared;
        //     sharedLocation = shared;
        setSprite();
    }

    public void setSprite() throws FileNotFoundException {
        ArrayList<Image> sprite = getSprite();
        for (int i = 1; i <= 16; i++) {
            sprite.add(new Image(new FileInputStream("src/assets/run" + i + ".png")));
        }
        setSprite(sprite);
    }

    // store values from 1 to 4 in sharedLocation
    @Override
    public void run() {
        ArrayList<Image> sprite = getSprite();
        setImage(sprite.get(1));
        int num = 7;
        int sum = 0;

        for (int a = 0; a < 8; a++) {
            try {

                setImage(sprite.get(8));
                for (int i = 1100; i > 700; i = i - 50) {

                    setImage(sprite.get(num));

                    num++;

                    setX(i);
                    Thread.sleep((int) (Math.random() * 300));

                }

                sum += sharedLocation.get();
                num = 0;
                for (int j = 750; j < 1150; j = j + 50) {

                    setImage(sprite.get(num));
                    if (num == 9) {
                        num = 9;
                    } else {
                        num++;
                    }
                    setX(j);
                    Thread.sleep((int) (Math.random() * 300));

                }

            } catch (InterruptedException ex) {
            }
        }
    } // end method run

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getImgNum() {
        return imgNum;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImgNum(int imgNum) {
        this.imgNum = imgNum;
    }

    public ArrayList<Image> getSprite() {
        return sprite;
    }

    public void setSprite(ArrayList<Image> sprite) {
        this.sprite = sprite;
    }

} // end class Consumer
