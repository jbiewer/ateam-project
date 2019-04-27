package application.main;

import application.util.Question;
import application.util.QuestionBank;

import java.util.Arrays;

import static application.main.Main.questionBank;

public class General {

    public static void main(String[] args) {
        questionBank.addQuestion(
                new Question("Math", "What is 2 + 2?", "4",
                        new String[]{"1", "2", "3"})
        );
        questionBank.addQuestion(
                new Question("Math", "What is 4 - 1?", "3",
                        new String[]{"1", "2", "5", "7"})
        );
        questionBank.addQuestion(
                new Question("Math", "What is the curl of the total magetic field of the Earth?", "O_o",
                        new String[]{"curl(lol u thought)", "nope", ":)"})
        );
        questionBank.addQuestion(
                new Question("Math", "Am I too lazy to think of questions?", "yes",
                        new String[]{"y3s, but with a 3", "no", "maybe"})
        );

        System.out.println(Arrays.toString(questionBank.getAllTopics()));
    }

}
