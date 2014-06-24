import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import processing.core.PApplet;
import processing.core.PImage;
import sojamo.drop.DropEvent;
import sojamo.drop.DropListener;
import sojamo.drop.SDrop;

import java.io.*;

/**
 * Created by arsenykogan on 24/05/14.
 * <p/>
 * TODO: shuffle / unsuffle
 * TODO: rounded scroll
 * TODO: learned collection
 */
public class Flipcard extends PApplet {

    private Gson gson;
    private SDrop drop;
    private PImage addIcon;
    private CardsCollection cards;

    /* Buttons */
    private Button button;
    private final ButtonArea goLeftButton = new ButtonArea(this, 0, 0, 200, 400);
    private final ButtonArea goRightButton = new ButtonArea(this, 600, 0, 800, 400);
    private final Button jumpButton = new Button(this, loadImage("jump.png"), 750, 200);
    private DropDownMenu menu;

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
                loadFromJSON(dropEvent.toString());
            }

            @Override
            public void dropEnter() {
                System.out.println("hello");
            }
        });
        addIcon = loadImage("add.png");
        button = new Button(this, addIcon, 400, 50);

        cards = new CardsCollection(this);
        menu = new DropDownMenu(this);

        /*float fov = (PI / 14);
        float cameraZ = (float) (height / 2.0) / tan(fov / 2);
        perspective(fov, (float) width / height, cameraZ / 10, cameraZ * 10);*/
    }

    @Override
    public void draw() {
        background(color(255, 255, 255));
        cards.display();
        button.display();
        menu.display();
        jumpButton.display();
    }

    @Override
    public void mouseClicked() {
        if (button.isPressed()) {
            cards.addCard();
        }
        if (goLeftButton.isPressed()) {
            cards.moveFocusLeft();
        }
        if (goRightButton.isPressed()) {
            cards.moveFocusRight();
        }
        cards.mouseClicked();
        menu.mouseClicked();
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
            } else if (keyCode == DOWN) {
                cards.moveToFirstCard();
            }
        }
    }

    public void saveToJSONCallback(final File selectedFile) {
        try {
            final FileWriter fw = new FileWriter(selectedFile.getAbsolutePath() + ".cards");
            gson.toJson(cards.getCardsCollectionExport(), fw);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToJSON() {
        selectOutput("Save", "saveToJSONCallback");
    }


    public void loadFromJSON(final String filename) {
        final FileReader fr;
        try {
            fr = new FileReader(filename);
            cards = new CardsCollection(this, gson.fromJson(fr, CardsCollection.CardsCollectionExport.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
