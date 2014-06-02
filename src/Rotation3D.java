import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import processing.core.PApplet;
import sojamo.drop.DropEvent;
import sojamo.drop.DropListener;
import sojamo.drop.SDrop;
import sun.java2d.pipe.SpanIterator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arsenykogan on 24/05/14.
 */
public class Rotation3D extends PApplet {

    private Card focusCard;
    private int focusIndex;
    private List<Card> cards;
    private Gson gson;
    private SDrop drop;

    @Override
    public void setup() {
        size(800, 400, P3D);
        textFont(createFont("MuseoSansCyrl-500", 40));
        noStroke();
        fill(255);
        rectMode(CENTER);

        gson = new GsonBuilder().create();
        drop = new SDrop(this);
        drop.addDropListener(new DropListener() {

            {
                setTargetRect(0,0, width, height );
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

        /* Creating collection of cards. */
        cards = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            final Card newCard = new Card(this);
            for (int j = 0; j < i; j++) {
                newCard.moveRight();
            }
            cards.add(newCard);
        }
        focusIndex = 0;
        focusCard = cards.get(focusIndex);

        /*float fov = (PI / 14);
        float cameraZ = (float) (height / 2.0) / tan(fov / 2);
        perspective(fov, (float) width / height, cameraZ / 10, cameraZ * 10);*/
    }

    @Override
    public void draw() {
        background(color(149, 180, 240));
        for (Card card : cards) {
            card.display();
        }
    }

    @Override
    public void mouseClicked() {
        super.mouseClicked();
        focusCard.turn();
    }

    @Override
    public void keyPressed() {
        super.keyPressed();
        if (key == CODED) {
            if (keyCode == RIGHT) {
                if (focusIndex < cards.size() - 1) {
                    for (Card card : cards) {
                        card.moveLeft();
                    }
                    focusCard = cards.get(++focusIndex);
                }
            } else if (keyCode == LEFT) {
                if (focusIndex > 0) {
                    for (Card card : cards) {
                        card.moveRight();
                    }
                    focusCard = cards.get(--focusIndex);
                }
            } else if (keyCode == UP) {
                focusCard.turn();
            } else if (keyCode == SHIFT) {
                try {
                    final FileWriter fw = new FileWriter("cards.json");
                    gson.toJson(cards, fw);
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadFromJSON(final String filename) throws FileNotFoundException {
        final FileReader fr = new FileReader(filename);
        cards = gson.fromJson(fr, List.class);
    }


}
