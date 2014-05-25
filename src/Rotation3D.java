import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arsenykogan on 24/05/14.
 */
public class Rotation3D extends PApplet {

    private Card focusCard;
    private int focusIndex;
    private List<Card> cards;

    @Override
    public void setup() {
        size(800, 400, P3D);
        textFont(createFont("MuseoSansCyrl-500", 40));
        noStroke();
        fill(255);
        rectMode(CENTER);

        /* Creating collection of cards. */
        cards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
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
        for (Card card : cards) {
            System.out.println(card);
        }
    }

    @Override
    public void keyPressed() {
        super.keyPressed();
        if (key == CODED) {
            if (keyCode == RIGHT) {
                for (Card card : cards) {
                    card.moveLeft();
                }
                focusCard = cards.get(++focusIndex);
            } else if (keyCode == LEFT) {
                for (Card card : cards) {
                    card.moveRight();
                }
                focusCard = cards.get(--focusIndex);
            } else if (keyCode == UP) {
                focusCard.turn();
            }
        }
    }
}
