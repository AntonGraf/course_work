package pro.sky.coursework2.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

@Service
public class ExaminerServiceImpl implements ExaminerService{

    QuestionService javaQuestionService;
    QuestionService mathQuestionService;

    public ExaminerServiceImpl(@Qualifier("JavaQuestionService") QuestionService javaQuestionService,
                               @Qualifier("MathQuestionService") QuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        Random random = new Random();

        if (amount == 0) {
            throw new QuestionNotFoundException("Количество запрашиваемых вопросов 0");
        }

        if (amount > (javaQuestionService.getAll().size() + mathQuestionService.getAll().size())) {
            throw new QuestionNotFoundException("Количество запрашиваемых вопросов больше общего количества вопросов");
        }

        Collection<Question> questions = new HashSet<>();

        while (questions.size() < amount) {

            if (random.nextBoolean()) {
                questions.add(javaQuestionService.getRandomQuestion());
            } else {
                questions.add(mathQuestionService.getRandomQuestion());
            }

        }

        return questions;
    }
}
