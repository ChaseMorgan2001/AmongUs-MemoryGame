import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class MemoryGame extends JFrame implements ActionListener
{
    // Core game play objects
    private Board gameBoard;
    private FlippableCard prevCard1;
    private FlippableCard prevCard2;

    // Labels to display game info
    private JLabel errorLabel, guessLabel, matchedLabel;

    // layout objects: Views of the board and the label area
    private JPanel boardView, labelView;

    // Record keeping counts and times
    private int clickCount = 0, guesses = 0, errorCount = 0;
    private int pairsFound = 0;


    public MemoryGame()
    {
        // Call the base class constructor
        super("Among Us Memory Game");

        // Allocate the interface elements
        JButton restart = new JButton("Restart");
        JButton quit = new JButton("Quit");
        guessLabel = new JLabel("Guesses: 0");
        errorLabel = new JLabel("Errors: 0");
        matchedLabel = new JLabel("Matches: 0");
        prevCard1 = new FlippableCard();
        prevCard2 = new FlippableCard();
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                restartGame();
            }
        });
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        // Allocate two major panels to hold interface
        labelView = new JPanel();  // used to hold labels
        boardView = new JPanel();  // used to hold game board

        // get the content pane, onto which everything is eventually added
        Container c = getContentPane();

        // Setup the game board with cards
        gameBoard = new Board(25, this);

        // Add the game board to the board layout area
        boardView.setLayout(new GridLayout(5, 5, 2, 0));
        gameBoard.fillBoardView(boardView);

        // Add required interface elements to the "label" JPanel
        labelView.setLayout(new GridLayout(1, 4, 2, 2));
        labelView.add(quit);
        labelView.add(restart);
        labelView.add(guessLabel);
        labelView.add(errorLabel);
        labelView.add(matchedLabel);

        // Both panels should now be individually layed out
        // Add both panels to the container
        c.add(labelView, BorderLayout.NORTH);
        c.add(boardView, BorderLayout.SOUTH);

        setSize(745, 875);
        setVisible(true);


    }

    /* Handle anything that gets clicked and that uses MemoryGame as an
     * ActionListener */
    public void actionPerformed(ActionEvent e)
    {
        // Get the currently clicked card from a click event
        FlippableCard currCard = (FlippableCard)e.getSource();

        if(currCard.isHidden()){

            currCard.showFront();

            clickCount++;

            if(clickCount % 2 == 0){ // 2nd card of pair

                FlippableCard temp = null;

                if (currCard.id() != 15) {guesses++;}
                guessLabel.setText("Guesses: " + guesses);
                if (currCard.id() == 15) { // if wildcard
                    currCard = prevCard1; // sets current card to previous card, ignoring wildcard
                    clickCount--; // sets click count back as if wildcard was not chosen
                    temp = prevCard1; // variable to hold previous card
                }
                if (prevCard1.id() == 15) {
                    prevCard1 = temp; // if the previous card was wild, assign prevCard to card before the wild
                }
                if(currCard.id() == prevCard1.id()){ // matched pair
                    pairsFound++;
                    matchedLabel.setText("Matches: " + pairsFound);
                    currCard.removeActionListener(this);
                    prevCard1.removeActionListener(this);
                }
                else { // not  a pair
                    class hideCards extends TimerTask {

                        private final FlippableCard c1;
                        private final FlippableCard c2;

                        hideCards ( FlippableCard c1, FlippableCard c2 )
                        {
                            this.c1 = c1;
                            this.c2 = c2;
                        }

                        public void run() {
                            c1.hideFront();
                            c2.hideFront();
                        }
                    }
                    new java.util.Timer().schedule(new hideCards(currCard,prevCard1),500);
                    errorCount++;
                    errorLabel.setText("Errors: " + errorCount);
                    if(currCard.id() == 15){
                        currCard.showFront();
                    }
                }
                prevCard1 = prevCard2;
            }
            else if (clickCount % 2 == 1) { // 1st card of pair
               if (currCard.id() == 15){ // wildcard
                   clickCount--; // sets click count back as if wildcard was not chosen
                   prevCard1 = prevCard2;
               }
               else { prevCard1 = currCard; }

            }
        }
        // dialog box for if win
        JDialog win = new JDialog();
        win.setSize(new Dimension(300, 200));
        JPanel dialog = new JPanel();
        dialog.setLayout(new BorderLayout());
        JLabel youWon = new JLabel("You Won!");
        youWon.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(youWon, BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        JButton q = new JButton("Quit");
        JButton r = new JButton("Restart");
        q.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        r.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                restartGame();
                win.setVisible(false);
            }
        });
        buttons.add(q);
        buttons.add(r);
        dialog.add(buttons, BorderLayout.SOUTH);
        win.add(dialog);
        win.setVisible(false);
        // set visible if all matches made
        if (pairsFound == 12) {
            win.setVisible(true);
        }
    }

    private void restartGame()
    {
        pairsFound = 0;
        guesses = 0;
        clickCount = 0;
        errorCount = 0;
        guessLabel.setText("Guesses: 0");
        errorLabel.setText("Errors: 0");
        matchedLabel.setText("Matches: 0");

        // Clear the boardView and have the gameBoard generate a new layout
        boardView.removeAll();
        gameBoard.resetBoard();
        gameBoard.fillBoardView(boardView);
    }


    public static void main(String args[])
    {
        MemoryGame M = new MemoryGame();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
}
