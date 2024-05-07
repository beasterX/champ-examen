package com.example.champ_examen;

import java.util.HashMap;
import java.util.LinkedList;

public class Exam {

    protected HashMap<Integer, Question> questions;
    protected HashMap<Integer, String> submittedAnswers;

    public Exam() {
        this.questions = new HashMap<>();
        this.submittedAnswers = new HashMap<>();
    }

    public Exam(HashMap<Integer, Question> questions, HashMap<Integer, String> submittedAnswers) {
        this.questions = new HashMap<>();
        this.questions.putAll(questions);
        this.submittedAnswers = new HashMap<>();
        this.submittedAnswers.putAll(submittedAnswers);
    }

    public Exam(LinkedList<Question> qList) {
        this.questions = new HashMap<>();
        for (int i = 0; i < qList.size(); i++) {
            this.questions.put(i, qList.get(i));
        }
        this.submittedAnswers = new HashMap<>();
    }

    public int getNumberOfQuestions() {
        return this.questions.size();
    }

    public void clearQuestions() {
        this.questions.clear();
    }

    public Question getQuestion(int i) {
        return this.questions.get(i);
    }

    public String getSubmittedAnswer(int i) {
        return this.submittedAnswers.get(i);
    }

    public void printAllQuestions() {
        for (Question question : this.questions.values()) {
            System.out.println(question);
        }
    }
    //    public void setSubmittedAnswer(int i, String answer) {
//        if (this.submittedAnswers.containsKey(i)) {
//            this.submittedAnswers.put(i, answer);
//        }
//    }
}
