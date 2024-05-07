package com.example.champ_examen;

import java.util.LinkedList;

public class MCQQuestion extends Question {
    private LinkedList<String> options;

    public MCQQuestion() {
        super("", "", QuestionType.MCQ);
        this.options = new LinkedList<>();

    }

    public MCQQuestion(LinkedList<String> options) {
        super("", "", QuestionType.MCQ);
        this.options = new LinkedList<>();
        this.options.addAll(options);
    }

    public MCQQuestion(String correctAnswer, String questionText, LinkedList<String> options) {
        super(correctAnswer, questionText, QuestionType.MCQ);
        this.options = new LinkedList<>();
        this.options.addAll(options);

        this.setQuestionType(QuestionType.MCQ);
    }

    public LinkedList<String> getOptions() {
        return this.options;
    }

    public void setOptions(LinkedList<String> options) {
        this.options.addAll(options);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getQuestionText()).append("\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append((char) ('A' + 1)).append(". ").append(options.get(i)).append("\n");
        }
        sb.append("ANSWER: " + this.getCorrectAnswer());
        return sb.toString();
    }
}
