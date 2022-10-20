package pro.sky.coursework2.service;

import pro.sky.coursework2.entity.Question;

import java.util.Collection;

public interface QuestionService {

    Question add(String question, String answer);
    Question add(Question question);
    Question remove(Question question);
    Collection<Question> getAll();
    Question getRandomQuestion();
}
