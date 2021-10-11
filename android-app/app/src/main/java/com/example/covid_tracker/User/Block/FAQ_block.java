package com.example.covid_tracker.User.Block;

public class FAQ_block {

    private String Question, Answer, Source;
    private Boolean expandable;


    public FAQ_block(String Question, String Answer, String Source) {
        this.Question = Question;
        this.Answer = Answer;
        this.Source = Source;

        this.expandable = false;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }



    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    @Override
    public String toString() {
        return "FAQ_block{" +
                "Question='" + Question + '\'' +
                ", Answer='" + Answer + '\'' +
                ", Source='" + Source + '\'' +
                '}';
    }
}
