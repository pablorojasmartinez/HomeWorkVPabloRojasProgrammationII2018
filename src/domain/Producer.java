/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author Pablo Rojas Martínez
 */
public class Producer extends Thread {

    SynchronizedBuffer sharedLocation;
    private ArrayList<Image> sprite;
    private int x;
    private int y;
    private int imgNum;
    private Image image;

    // constructor
    public Producer(SynchronizedBuffer shared) throws FileNotFoundException {
        super("Producer");
        this.x = x;
        this.y = y;
        this.imgNum = imgNum;
        this.sprite = new ArrayList<Image>();
        sharedLocation = shared;
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
        int num = 0;
        int value = 0;
        for (int a = 0; a < 8; a++) {
            try {

                for (int i = 200; i < 600; i = i + 50) {

                    setImage(sprite.get(num));

                    num++;

                    setX(i);
                    Thread.sleep((int) (Math.random() * 200));
                    //           super.setX(700);
                    //           Thread.sleep(800);
                }

                sharedLocation.set(value);
                //  value++;
                for (int j = 550; j > 200; j = j - 50) {

                    setImage(sprite.get(num));
                    if (num == 14) {
                        num = 0;
                    } else {
                        num++;
                    }
                    setX(j);
                    Thread.sleep((int) (Math.random() * 200));

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

} // end class Producer

