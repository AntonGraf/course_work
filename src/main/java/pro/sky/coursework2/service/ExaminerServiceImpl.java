package pro.sky.coursework2.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

@Service
public class ExaminerServiceImpl implements ExaminerService{

    Map<String, QuestionService> questionServiceMap;

    public ExaminerServiceImpl(Map<String, QuestionService> questionServiceMap) {
        this.questionServiceMap = questionServiceMap;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {

        Random random = new Random();

        if (amount == 0) {
            throw new QuestionNotFoundException("Количество запрашиваемых вопросов 0");
        }

        Collection<Question> questions = new HashSet<>();

        while (questions.size() < amount) {
            questions.add(questionServiceMap.values().toArray(
                    new QuestionService[0])[random.nextInt(questionServiceMap.size())].getRandomQuestion());
        }

        return questions;
    }
}
