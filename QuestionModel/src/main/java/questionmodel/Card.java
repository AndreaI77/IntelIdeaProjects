package questionmodel;

import java.io.Serializable;

public class Card implements Serializable {
    private String question;
    private String Answer;
    private String category;
    private String [] options;

    public Card(String question, String Answer, String category, String[] options) {
        this.question = question;
        this.Answer = Answer;
        this.category = category;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return Answer;
    }

    public String getCategory() {
        return category;
    }

    public String[] getOptions() {
        return options;
    }
}
