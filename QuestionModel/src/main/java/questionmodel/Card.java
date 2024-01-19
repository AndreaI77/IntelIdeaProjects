package questionmodel;

import java.io.Serializable;

/**
 * class to define a card
 */
public class Card implements Serializable {
    private String question;
    private String Answer;
    private String category;
    private String [] options;

    /**
     * Constructor creates a new card form the params
     * @param question String  that contains the question
     * @param Answer String that contains the correct answer
     * @param category String that contains the question category
     * @param options Strin array with the options to choose from
     */
    public Card(String question, String Answer, String category, String[] options) {
        this.question = question;
        this.Answer = Answer;
        this.category = category;
        this.options = options;
    }

    /**
     * method to get the card question
     * @return String question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * method to get the correct answer
     * @return String answer
     */
    public String getAnswer() {
        return Answer;
    }

    /**
     * method to get the question category
     * @return String category
     */
    public String getCategory() {
        return category;
    }

    /**
     * method  that gets the options
     * @return String array with the options
     */
    public String[] getOptions() {
        return options;
    }
}
