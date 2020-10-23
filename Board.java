import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class Board
{
    // Array to hold board cards
    private FlippableCard cards[];

    // Action listener
    private ActionListener al;

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Shuffle cards
    public void shuffleCards(){
        ArrayList<FlippableCard> cardsShuffled = new ArrayList<FlippableCard>();
        for (FlippableCard c : cards){
            cardsShuffled.add(c);
            Collections.shuffle(cardsShuffled);
        }
        for (int i = 0; i < cardsShuffled.size(); i++){
            cards[i] = cardsShuffled.get(i);
        }
    }

    public Board(int size, ActionListener AL)
    {
        al = AL;

        // Allocate and configure the game board: an array of cards
        // Leave one extra space for the report button, added at the end
        cards = new FlippableCard[size];

        // Fill the Cards array
        int imageIdx = 1;
        for (int i = 0; i < (size-1); i += 2) {

            // Load the front image from the resources folder
            String imgPath = "images/AmongUs-" + imageIdx + ".png";
            ImageIcon img = new ImageIcon(loader.getResource(imgPath));
            imageIdx++;  // get ready for the next pair of cards

            // Setup two cards at a time
            FlippableCard c1 = new FlippableCard(img);
            FlippableCard c2 = new FlippableCard(img);

            // Add ids
            c1.setID(imageIdx);
            c2.setID(imageIdx);

            // Add them to the array
            cards[i] = c1;
            cards[i + 1] = c2;


        }
        // Add the report button image
        String imgPath = "images/AmongUs-report.png";
        ImageIcon img = new ImageIcon(loader.getResource(imgPath));
        FlippableCard c1 = new FlippableCard(img);
        c1.setID(15);
        cards[size-1] = c1;
        shuffleCards();

    }

    public void fillBoardView(JPanel view)
    {
        for (FlippableCard c : cards) {
                    view.add(c);
                    c.addActionListener(al);
        }
        view.revalidate();
        view.repaint();
    }

    public void resetBoard()
    {
        for (FlippableCard c : cards) {
            c.hideFront();
        }
        shuffleCards();

    }
}
