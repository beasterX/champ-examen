package com.example.champ_examen;

public class Question {
    private String correctAnswer;
    private String questionText;
    private QuestionType questionType;

    public boolean checkAnswer(String answer) {
        if (this.correctAnswer.equalsIgnoreCase(answer)) {
            return true;
        }
        return false;
    }

    public Question() {
        this.correctAnswer = "";
        this.questionText = "";
        this.questionType = null;
    }

    public Question(String correctAnswer, String questionText, QuestionType questionType) {
        this.correctAnswer = correctAnswer;
        this.questionText = questionText;
        this.questionType = questionType;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer.toLowerCase();
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return this.questionText;
    }


    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return this.questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        return "Question{" +
                "correctAnswer='" + correctAnswer + '\'' +
                ", questionText='" + questionText + '\'' +
                ", questionType=" + questionType +
                '}';
    }
}