import javax.swing.*;

public class FlippableCard extends JButton
{
    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Card front icon
    private Icon front;
    // Card back image
    private Icon back = new ImageIcon(loader.getResource("images/AmongUs-logo.png"));


    // ID + Name
    private int id;
    private String customName;

    // Default constructor
    public FlippableCard() { super(); }

    // Constructor with card front initialization
    public FlippableCard(ImageIcon frontImage)
    {
        super();
        setFrontImage(frontImage);
        super.setIcon(back);
    }

    // Set the image used as the front of the card
    public void setFrontImage(ImageIcon frontImage) { front = frontImage; }

    // Card flipping functions
    public void showFront() { this.setIcon(front); }
    public void hideFront() { this.setIcon(back); }

    //
    public boolean isHidden(){
        return (getIcon() == back);
    }

    // Metadata: ID number
    public int id() { return id; }
    public void setID(int i) { id = i; }

    // Metadata: Custom name
    public String customName() { return customName; }
    public void setCustomName(String s) { customName = s; }
}
