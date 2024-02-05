import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class CardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<Card> cardList;
    private JFrame frame;

    public static void main (String[] args) {
        CardBuilder builder = new CardBuilder();
        builder.go();
    }

    public void go(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel ();

        Font bigFont = new Font("sanserif", Font.BOLD,24);

        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        JScrollPane qScroller = new JScrollPane (question);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next Card");

        cardList = new ArrayList<Card>();

        JLabel qLabel = new JLabel("Question:");
        JLabel aLabel = new JLabel("Answer:");

        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);

        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");

        newMenuItem.addActionListener(new NewMenuListener());
        saveMenuItem.addActionListener(new SaveMenuListener());

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(500,600);
        frame.setVisible(true);

    }

    public class NextCardListener implements ActionListener {
        public void actionPerformed (ActionEvent ev) {

            Card card = new Card (question.getText(), answer.getText());
            cardList.add(card);
            clearCard();

        }
    }

    public class SaveMenuListener implements ActionListener {
        public void actionPerformed (ActionEvent ev){

            Card card = new Card (question.getText(), answer.getText());
            cardList.add(card);

            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile (fileSave.getSelectedFile());

        }
    }

    public class NewMenuListener implements ActionListener {
        public void actionPerformed (ActionEvent ev) {

            cardList.clear();
            clearCard();

        }
    }

    public void clearCard () {

        question.setText("");
        answer.setText("");
        question.requestFocus();

    }

    private void saveFile (File file){

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

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
