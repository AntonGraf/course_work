package pro.sky.coursework2.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.exception.QuestionAddedException;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@Component("MathQuestionService")
public class MathQuestionService implements QuestionService{

    Set<Question> questions;

    public MathQuestionService() {
        questions = new HashSet<>();
    }

    @PostConstruct
    void init() {
        questions.add(new Question("2 + 2","4"));
        questions.add(new Question("2 * 2","4"));
        questions.add(new Question("2 ^ 2", "4"));
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {

        if (questions.add(question)) {
            return question;
        } else {
            throw new QuestionAddedException("Не удалось добавить вопрос \"" + question.getQuestion() + "\"");
        }
    }

    @Override
    public Question remove(Question question) {

        if (questions.remove(question)) {
            return question;
        } else {
            throw new QuestionNotFoundException("Не удалось удалить вопрос \"" + question.getQuestion() + "\"");
        }

    }

    @Override
    public Collection<Question> getAll() {
        return questions;
    }

    @Override
    public Question getRandomQuestion() {

        if (getAll().size() == 0) {
            throw new QuestionNotFoundException("Список вопросов пуст.");
        }

        Random random = new Random();

        return (Question) getAll().toArray()[random.nextInt(getAll().size())];

    }
}
