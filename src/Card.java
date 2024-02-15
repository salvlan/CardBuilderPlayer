// Class representing a flashcard with a question and an answer
public class Card {

    // Instance variables to store the question and answer
    private String question;
    private String answer;

    // Constructor to initialize a Card with a given question and answer
    public Card (String q, String a){
        question = q;
        answer = a;
    }

    // Getter method to retrieve the question of the card
    public String getQuestion (){
        return question;
    }

    // Getter method to retrieve the answer of the card
    public String getAnswer (){
        return answer;
    }

}
