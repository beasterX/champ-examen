package com.example.champ_examen;

public class TFQQuestion extends Question {

    public TFQQuestion() {
        super("", "", QuestionType.TFQ);
    }

    public TFQQuestion(String correctAnswer, String questionText) {
        super(correctAnswer, questionText, QuestionType.TFQ);
    }

    @Override
    public String toString() {
        return getQuestionText() + "\nTrue\nFalse";
    }
}
