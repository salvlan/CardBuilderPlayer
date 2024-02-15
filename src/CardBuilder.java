import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

// Main class for CardBuilder
public class CardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<Card> cardList;
    private JFrame frame;

    // Main method to create an instance of CardBuilder and start the application
    public static void main (String[] args) {
        new CardBuilder().go();
    }

    // Method to set up the GUI and initialize components
    public void go(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel ();

        Font bigFont = new Font("sanserif", Font.BOLD,24);

        // Set up the question text area
        question = new JTextArea(6,20);
        question.setLineWrap(true); // text that reaches the end of a line will automatically wrap to the next line
        question.setWrapStyleWord(true); // the text will wrap at spaces between words, ensuring that a word is not split across two lines
        question.setFont(bigFont);

        // Set up a scroll pane for the question text area
        JScrollPane qScroller = new JScrollPane (question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Set up the answer text area
        answer = new JTextArea(6,20);
        answer.setLineWrap(true); // text that reaches the end of a line will automatically wrap to the next line
        answer.setWrapStyleWord(true); // the text will wrap at spaces between words, ensuring that a word is not split across two lines
        answer.setFont(bigFont);

        // Set up a scroll pane for the answer text area
        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Set up the "Next Card" button
        JButton nextButton = new JButton("Next Card");

        // Initialize the cardList to store Card objects
        cardList = new ArrayList<>();

        JLabel qLabel = new JLabel("Question:");
        JLabel aLabel = new JLabel("Answer:");

        // Add components to the main panel
        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);

        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());

        // Set up menu bar with "File" menu containing "New" and "Save" options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");

        newMenuItem.addActionListener(new NewMenuListener());
        saveMenuItem.addActionListener(new SaveMenuListener());

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        // Set up frame with menu bar and main panel
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(600,600);
        frame.setVisible(true);

    }

    // ActionListener for the "Next Card" button
    public class NextCardListener implements ActionListener {
        public void actionPerformed (ActionEvent ev) {
            // Create a new Card with the current question and answer, add it to cardList, and clear the card
            Card card = new Card (question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }
    }

    // ActionListener for the "Save" option in the "File" menu
    public class SaveMenuListener implements ActionListener {
        public void actionPerformed (ActionEvent ev){
            // Create a new Card with the current question and answer, add it to cardList
            Card card = new Card (question.getText(), answer.getText());
            cardList.add(card);
            // Open a file chooser dialog for saving the file and call saveFile method
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile (fileSave.getSelectedFile());
        }
    }

    // ActionListener for the "New" option in the "File" menu
    public class NewMenuListener implements ActionListener {
        public void actionPerformed (ActionEvent ev) {
            // Clear the cardList and the current card
            cardList.clear();
            clearCard();
        }
    }

    // Method to clear the question and answer text areas
    public void clearCard () {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    // Method to save the cardList to a file
    private void saveFile (File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            // Write each card's question and answer to the file
            for (Card card: cardList){
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("couldn't write the card list out");
            ex.printStackTrace();
        }
    }
}
