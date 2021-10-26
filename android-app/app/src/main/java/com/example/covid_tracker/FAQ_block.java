package com.example.covid_tracker;

public class FAQ_block {

    private String Question, Answer;
    private Boolean expandable;


    public FAQ_block(String Question, String Answer) {
        this.Question = Question;
        this.Answer = Answer;

        this.expandable = false;
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
                '}';
    }
}
