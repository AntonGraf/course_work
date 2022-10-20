package pro.sky.coursework2.service;

import org.springframework.stereotype.Service;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import java.util.Collection;
import java.util.HashSet;

@Service
public class ExaminerServiceImpl implements ExaminerService{

    QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {

        if (amount > questionService.getAll().size()) {
            throw new QuestionNotFoundException("Количество запрашиваемых вопросов больше общего количества вопросов");
        }

        Collection<Question> questions = new HashSet<>();

        while (questions.size() < amount) {
            questions.add(questionService.getRandomQuestion());
        }

        return questions;
    }
}
