import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import processing.core.PApplet;
import processing.core.PImage;
import sojamo.drop.DropEvent;
import sojamo.drop.DropListener;
import sojamo.drop.SDrop;
import sun.text.resources.FormatData_in;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arsenykogan on 24/05/14.
 *
 * TODO: shuffle / unsuffle
 * TODO: rounded scroll
 * TODO: learned collection
 */
public class Rotation3D extends PApplet {

//    private Card focusCard;
//    private int focusIndex;
//    private List<Card> cards;
    private Gson gson;
    private SDrop drop;
    private PImage addIcon;
    private Button button;
    private CardsCollection cards;

    @Override
    public void setup() {
        size(800, 400, P3D);
        textFont(createFont("Helvetica-Bold", 30));
        noStroke();
        fill(255);
        rectMode(CENTER);

        gson = new GsonBuilder().create();
        drop = new SDrop(this);
        drop.addDropListener(new DropListener() {

            {
                setTargetRect(0, 0, width, height);
            }

            @Override
            public void dropEvent(final DropEvent dropEvent) {
                try {
                    loadFromJSON(dropEvent.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void dropEnter() {
                System.out.println("hello");
            }
        });
        addIcon = loadImage("resources/add.png");
        button = new Button(this, addIcon, 400 - 13, 50 - 13);

        cards = new CardsCollection(this);

        /*float fov = (PI / 14);
        float cameraZ = (float) (height / 2.0) / tan(fov / 2);
        perspective(fov, (float) width / height, cameraZ / 10, cameraZ * 10);*/
    }

    @Override
    public void draw() {
        background(color(255, 255, 255));
        cards.display();
        button.display();
    }

    @Override
    public void mouseClicked() {
        if (button.isPressed()) {
            cards.addCard();
        }
    }

    @Override
    public void keyPressed() {
        cards.editFocusCard(key);
        if (key == CODED) {
            if (keyCode == RIGHT) {
                cards.moveFocusRight();
            } else if (keyCode == LEFT) {
                cards.moveFocusLeft();
            } else if (keyCode == UP) {
                cards.flipFocusCard();
            } else if (keyCode == SHIFT) {
                try {
                    final FileWriter fw = new FileWriter("cards.json");
                    gson.toJson(cards.getCardsCollectionExport(), fw);
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (keyCode == DOWN) {
                try {
                    loadFromJSON("cards.json");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void loadFromJSON(final String filename) throws FileNotFoundException {
        final FileReader fr = new FileReader(filename);
        cards = new CardsCollection(this, gson.fromJson(fr, CardsCollection.CardsCollectionExport.class));
    }


}
