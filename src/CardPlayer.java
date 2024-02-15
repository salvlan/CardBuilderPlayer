import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

// Main class for CardPlayer
public class CardPlayer {

    private JTextArea display;

    private ArrayList<Card> cardList;
    private Card currentCard;
    private int currentCardIndex;
    private JFrame frame;
    private JButton nextButton;
    private boolean isShowAnswer;

    // Main method to create an instance of CardPlayer and start the application
    public static void main(String[] args) {
        new CardPlayer().go();
    }

    // Method to set up the GUI and initialize components
    public void go () {

        frame = new JFrame("Card Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();

        Font bigFont = new Font("sanserif",Font.BOLD,24);

        // Set up the display text area
        display = new JTextArea(10,20);
        display.setFont(bigFont);
        display.setLineWrap(true); // text that reaches the end of a line will automatically wrap to the next line
        display.setEditable(false);

        // Set up a scroll pane for the display text area
        JScrollPane qScroller = new JScrollPane(display);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Set up the "Show question" button
        nextButton = new JButton("Show question");

        mainPanel.add(qScroller);
        mainPanel.add(nextButton);

        // Add an action listener to the "Show question" button
        nextButton.addActionListener((e)->{
            if (isShowAnswer) {
                // Show the answer and change button text to "Next Card"
                display.setText(currentCard.getAnswer());
                nextButton.setText("Next Card");
                isShowAnswer = false;
            } else {
                if (currentCardIndex < cardList.size()) {
                    // Show the next card
                    showNextCard();
                } else {
                    // Display message when all cards are shown
                    display.setText("That was last card");
                    nextButton.setEnabled(false);
                }
            }
        });

        // Set up menu bar with "File" menu containing "Load card set" option
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load card set");
        // ActionListener for the "Load card set" option in the "File" menu
        loadMenuItem.addActionListener((e)->{
            // Open a file chooser dialog for loading the card set and call loadFile method
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile (fileOpen.getSelectedFile());
        });
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);

        // Set up frame with menu bar and main panel
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(550,500);
        frame.setVisible(true);

    }

    // Method to load a card set from a file
    private void loadFile (File file){
        cardList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader (file));
            String line = null;
            while ((line = reader.readLine()) != null){
                makeCard(line);
            }
            reader.close();
        } catch ( Exception ex) {
            System.out.println("couldn't read the card file");
            ex.printStackTrace();
        }

        // Show the first card
        showNextCard();
    }

    // Method to create a Card object from a line of text
    private void makeCard (String lineToParse){
        String[] result = lineToParse.split("/");
        Card card = new Card(result[0],result[1]);
        cardList.add(card);
        System.out.println("Made a card");
    }

    // Method to display the next card
    private void showNextCard (){
        currentCard = cardList.get(currentCardIndex);
        currentCardIndex++;
        display.setText(currentCard.getQuestion());
        nextButton.setText("Show Answer");
        isShowAnswer = true;
    }

}

