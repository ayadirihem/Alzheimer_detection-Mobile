package com.example.alzheimer_detection;

import java.util.ArrayList;
import java.util.List;

public class QuestionsData {

    private static List<QuestionsList> javaQuestions(){

        final List<QuestionsList> questionsLists = new ArrayList<>();

        final QuestionsList questions1 = new QuestionsList("question1 jjh","g",
                "ff","ddd","ddf","ddd","");

        final QuestionsList questions2 = new QuestionsList("question 2 cccccccccccch","g",
                "ff","ddd","ddf","ff","");

        questionsLists.add(questions1);
        questionsLists.add(questions2);

        return questionsLists;

    }

    public static List<QuestionsList> getQuestions(String selectedTopicName) {
        switch (selectedTopicName) {
            case "java":
                return javaQuestions();
            default:
                return javaQuestions();
        }
    }
}
