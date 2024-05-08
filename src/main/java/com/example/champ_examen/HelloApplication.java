package com.example.champ_examen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class HelloApplication extends Application {
    private Exam exam;
    private Label labelShowingGrade;
    private VBox[] questionVboxes;
    private VBox root;

    public void start(Stage stage) throws IOException {
        root = new VBox();
        Random random = new Random();
        QuestionBank myBank = new QuestionBank();
        myBank.readMCQ("C:\\Users\\mirfa\\IdeaProjects\\Champ_Examen\\src\\main\\resources\\mcq.txt");
        myBank.readTFQ("C:\\Users\\mirfa\\IdeaProjects\\Champ_Examen\\src\\main\\resources\\tfq.txt");

//        myBank.printAllQuestions();
        int[] rand = new int[10];
        for (int i = 0; i < rand.length; i++) {
            rand[i] = random.nextInt(65);
        }

        LinkedList<Question> questions = myBank.selectRandQuestions(rand);

        this.exam = new Exam(questions);


        root.getChildren().clear();

        MenuBar menuBar = buildMenuBar();
        HBox topBanner = buildTopBanner();
        labelShowingGrade = new Label("Grade:");
        labelShowingGrade.setFont(Font.font(14));
        labelShowingGrade.setAlignment(Pos.CENTER);
        HBox hgrade = new HBox(labelShowingGrade);
        hgrade.setPadding(new Insets(30));
        hgrade.setAlignment(Pos.CENTER);
        HBox navigationBar = buildNavigationBar();

        root.getChildren().addAll(menuBar, topBanner, hgrade, navigationBar);

        questionVboxes = createExamPage(exam);
        VBox container = new VBox();
        container.getChildren().addAll(questionVboxes);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(container);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 900, 650);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("ChampExamen: Champs Testing App");
        stage.show();

    }

    public MenuBar buildMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem openMenuItem = new MenuItem("Open");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(openMenuItem, saveMenuItem, exitMenuItem);

        Menu editMenu = new Menu("Edit");
        MenuItem cutMenuItem = new MenuItem("Cut");
        MenuItem copyMenuItem = new MenuItem("Copy");
        MenuItem pasteMenuItem = new MenuItem("Paste");
        editMenu.getItems().addAll(cutMenuItem, copyMenuItem, pasteMenuItem);

        Menu quizMenu = new Menu("Quiz");
        MenuItem startQuizMenuItem = new MenuItem("Start Quiz");
        MenuItem viewResultsMenuItem = new MenuItem("View Results");
        quizMenu.getItems().addAll(startQuizMenuItem, viewResultsMenuItem);

        Menu extrasMenu = new Menu("Extras");
        MenuItem settingsMenuItem = new MenuItem("Settings");
        MenuItem aboutMenuItem = new MenuItem("About App");
        extrasMenu.getItems().addAll(settingsMenuItem, aboutMenuItem);

        Menu helpMenu = new Menu("Help");
        MenuItem helpContentItem = new MenuItem("Help Content");
        MenuItem aboutAppItem = new MenuItem("About App");
        helpMenu.getItems().addAll(helpContentItem, aboutAppItem);


        menuBar.getMenus().addAll(fileMenu, editMenu, quizMenu, extrasMenu, helpMenu);

        return menuBar;
    }

    private HBox buildTopBanner() {
        HBox topBanner = new HBox();

        Image logoImage = new Image("ChampExamenLogo.jpg");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(330);
        logoImageView.setFitHeight(250);

        File file = new File("C:\\Users\\mirfa\\IdeaProjects\\Champ_Examen\\src\\main\\resources\\standard.gif");
        Image bannerImage = new Image(file.toURI().toString());
        ImageView bannerImageView = new ImageView(bannerImage);
        bannerImageView.setFitWidth(570);
        bannerImageView.setFitHeight(250);

        topBanner.getChildren().addAll(logoImageView, bannerImageView);
        topBanner.setAlignment(Pos.CENTER);
        return topBanner;
    }

    public HBox buildNavigationBar() {
        HBox navigationBar = new HBox();
        navigationBar.setSpacing(10);
        navigationBar.setPadding(new Insets(10));
        navigationBar.setAlignment(Pos.CENTER);

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clearExamAnswers());

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> saveExamAnswers());

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(new SubmitButtonHandler());

        navigationBar.getChildren().addAll(clearButton, saveButton, submitButton);
        navigationBar.setAlignment(Pos.CENTER);

        return navigationBar;
    }

    private void clearExamAnswers() {
        exam.submittedAnswers.clear();
    }

    private void saveExamAnswers() {
    }

    private class SubmitButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            int counter = 0;

            for (int i = 0; i < exam.getNumberOfQuestions(); i++) {
                String correctAnswer = exam.getQuestion(i).getCorrectAnswer().toLowerCase();
                String submittedAnswer = exam.getSubmittedAnswer(i);
                System.out.println("Correct Answer for Question " + (i+1) + ": " + correctAnswer);
                if (correctAnswer.equalsIgnoreCase(submittedAnswer)) {
                    counter++;
                }
            }
            labelShowingGrade.setText(String.format("Grade: %d / %d", counter, exam.getNumberOfQuestions()));
            System.out.printf("Grade: %d / %d\n", counter, exam.questions.size());

            if (counter == exam.getNumberOfQuestions()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congratulations!");
                alert.setHeaderText(null);
                alert.setContentText("Congratulations! You got 10/10!");
                alert.showAndWait();
                }
        }
    }


    public VBox buildTrueFalseQ(int questionNumber, TFQQuestion tfQuestion1) {
        String str = String.format("%d) %s", questionNumber + 1, tfQuestion1.getQuestionText());

        Label questionLabel = new Label(str);

        ToggleGroup mcqGroup = new ToggleGroup();
        RadioButton tfqOptionA = new RadioButton("True");
        RadioButton tfqOptionB = new RadioButton("False");

        tfqOptionA.setToggleGroup(mcqGroup);
        tfqOptionA.setOnAction(e -> setQuestionAnswer(questionNumber, "true"));
        tfqOptionB.setToggleGroup(mcqGroup);
        tfqOptionB.setOnAction(e -> setQuestionAnswer(questionNumber, "false"));


        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(questionLabel, new HBox(10, tfqOptionA, tfqOptionB));

        vBox.getChildren().addAll(tfqOptionA, tfqOptionB);

        return vBox;
    }

    public VBox buildMCQ(int questionNumber, MCQQuestion mcqQuestion) {
        String str = String.format("%d) %s", questionNumber + 1, mcqQuestion.getQuestionText());

        Label questionLabel = new Label(str);

        ToggleGroup mcqGroup = new ToggleGroup();
        RadioButton mcqOptionA = new RadioButton("A. " + mcqQuestion.getOptions().get(0));
        RadioButton mcqOptionB = new RadioButton("B. " + mcqQuestion.getOptions().get(1));
        RadioButton mcqOptionC = new RadioButton("C. " + mcqQuestion.getOptions().get(2));
        RadioButton mcqOptionD = new RadioButton("D. " + mcqQuestion.getOptions().get(3));

        mcqOptionA.setToggleGroup(mcqGroup);
        mcqOptionA.setOnAction(e -> setQuestionAnswer(questionNumber, "a"));
        mcqOptionB.setToggleGroup(mcqGroup);
        mcqOptionB.setOnAction(e -> setQuestionAnswer(questionNumber, "b"));
        mcqOptionC.setToggleGroup(mcqGroup);
        mcqOptionC.setOnAction(e -> setQuestionAnswer(questionNumber, "c"));
        mcqOptionD.setToggleGroup(mcqGroup);
        mcqOptionD.setOnAction(e -> setQuestionAnswer(questionNumber, "d"));

        VBox vboxQuestions = new VBox(10);
        vboxQuestions.setPadding(new Insets(20));
        vboxQuestions.getChildren().addAll(questionLabel, mcqOptionA, mcqOptionB, mcqOptionC, mcqOptionD);

        return vboxQuestions;
    }

    public void setQuestionAnswer(int questionNumber, String answer) {
        this.exam.submittedAnswers.put(questionNumber, answer);
        System.out.println("Submitted Answer for Question " + questionNumber + ": " + answer);
    }


    public String convertBooltoString(boolean b) {
        String result;
        if (b)
            result = "true";
        else result = "false";
        return result;
    }

    private VBox[] createExamPage(Exam exam) {
        VBox[] questionVboxes = new VBox[exam.questions.size()];
        for (int i = 0; i < questionVboxes.length; i++) {
            Question question = this.exam.getQuestion(i);
            questionVboxes[i] = new VBox();
            if (question.getQuestionType() == QuestionType.TFQ) {
                TFQQuestion tfq = (TFQQuestion) question;
                questionVboxes[i] = buildTrueFalseQ(i, tfq);
            } else {
                MCQQuestion mcq = (MCQQuestion) question;
                questionVboxes[i] = buildMCQ(i, mcq);
            }
        }
        return questionVboxes;
    }

//    private VBox createQuestionVbox(int questionNumber, Question question) {
//        VBox vbox;
//        if (question.getQuestionType() == QuestionType.TFQ) {
//            vbox = buildTrueFalseQ(questionNumber, (TFQQuestion) question);
//        } else {
//            vbox = buildMCQ(questionNumber, (MCQQuestion) question);
//        }
//        return vbox;
//    }


    public static void main(String[] args) {
        launch();
    }


}
