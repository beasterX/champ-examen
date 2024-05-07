package com.example.champ_examen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class QuestionBank {
    protected LinkedList<Question> questions;

    public QuestionBank() {
        this.questions = new LinkedList<>();

    }

    public QuestionBank(LinkedList<Question> questions) {
        this.questions = questions;
    }

    public QuestionBank(String correctAnswer, String questionText, QuestionType questionType, LinkedList<Question> questions) {
        this.questions = questions;
    }

    public Question getQuestions(int i) {
        return questions.get(i);
    }

    public void clearQuestions() {
        this.questions.clear();
    }

    public void printAllQuestions() {
        Iterator iterator = questions.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void setQuestions(LinkedList<Question> questions) {
        this.questions = questions;
    }


    public void readMCQ(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Question question;
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String questionText = line.substring(3);
            LinkedList<String> options = new LinkedList<>();
            String optionA = scanner.nextLine().substring(3);
            System.out.println(questionText);
            options.add(optionA);
            System.out.println(optionA);
            String optionB = scanner.nextLine().substring(3);
            options.add(optionB);
            System.out.println(optionB);
            String optionC = scanner.nextLine().substring(3);
            options.add(optionC);
            System.out.println(optionC);
            String optionD = scanner.nextLine().substring(3);
            options.add(optionD);
            System.out.println(optionD);
            String correctAnswer = scanner.nextLine();
            String[] parts = correctAnswer.split(":");
            correctAnswer = parts[1].trim().toLowerCase();
            System.out.println(correctAnswer);

            if (scanner.hasNext()) {
                scanner.nextLine();
            }

            question = new MCQQuestion(correctAnswer, questionText, options);
            questions.add(question);

        }
    }

    public void readTFQ(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Question question;
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String questionText = line.substring(3);
            System.out.println(questionText);

            String correctAnswer = scanner.nextLine();
            String[] parts = correctAnswer.split(":");
            correctAnswer = parts[1].trim().toLowerCase();
            System.out.println(correctAnswer);

            question = new TFQQuestion(correctAnswer, questionText);
            questions.add(question);
        }

    }

    public LinkedList<Question> selectRandQuestions(int[] indices) {
        LinkedList<Question> selectedQuestions = new LinkedList<>();
        for (int index : indices) {
            selectedQuestions.add(this.questions.get(index));
        }
        return selectedQuestions;
    }
}
