package model;

import controller.SpriteAnimation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.Main;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Runner extends Pane {
    File file = new File("runner.png");
    String localUrl = file.toURI().toURL().toString();
    Image image = new Image(localUrl);
    ImageView imageView = new ImageView(image);
    public Point2D location;
    int count = 5;
    int offsetX = 0;
    int width = 227;
    int height = 493;
    int counter = 0;
    public static ArrayList<Let> lets = Main.getLets();
    public SpriteAnimation animation;
    public Runner() throws MalformedURLException {
        location = new Point2D(0,0);
        setTranslateX(100);
        setTranslateY(300);
        imageView.setFitHeight(123);
        imageView.setFitWidth(57);
        imageView.setViewport(new Rectangle2D(offsetX,0,width,height));
        animation = new SpriteAnimation(this.imageView, Duration.millis(400),count,offsetX,width,height);
        getChildren().addAll(this.imageView);
    }

    public boolean moveX(double x, double speedIncrease) {
        for (int i = 0; i < Math.abs(x); i++) {
            setTranslateX(getTranslateX() + 1 + speedIncrease);
            for (int j = counter; j < lets.size(); j++) {
                //проверяем соприкосновение с препятствием
                if (this.getBoundsInParent().intersects(lets.get(j).getBoundsInParent())) {
                    setTranslateX(100);
                    setTranslateY(400);
                    counter = 0;
                    return true;
                }
                //в случае успешного прохождения препятствия увеличиваем счет
                if(getTranslateX() >= lets.get(j).getTranslateX() + 70) {
                    Main.score++;
                    counter += 1;
                }
            }
        }
        return false;
    }

    public boolean moveY(double y) {
        for (int i = 0; i < Math.abs(y); i++) {
            for (Let let : lets) {
                //проверяем соприкосновение с препятствием
                if (this.getBoundsInParent().intersects(let.getBoundsInParent())) {
                    setTranslateX(100);
                    setTranslateY(400);
                    counter = 0;
                    return true;
                }
            }
        }
        //не даем бегуну провалиться сквозь землю
        if (getTranslateY() > 460) {
            setTranslateY(460);
        }
        setTranslateY(getTranslateY() + y);
        return false;
    }

    public void jump() {
        if (getTranslateY() == 465) {
            location = new Point2D(6, -20);
        }
    }
}
