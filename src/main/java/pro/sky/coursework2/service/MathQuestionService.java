package pro.sky.coursework2.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.entity.QuestionRepository;
import pro.sky.coursework2.exception.QuestionAddedException;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Random;

@Service
@Component("MathQuestionService")
public class MathQuestionService implements QuestionService{

    QuestionRepository questionRepository;

    public MathQuestionService(@Qualifier("MathQuestionRepository") QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    void init() {
        questionRepository.add(new Question("2 + 2","4"));
        questionRepository.add(new Question("2 * 2","4"));
        questionRepository.add(new Question("2 ^ 2", "4"));
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {

        if (questionRepository.add(question)) {
            return question;
        } else {
            throw new QuestionAddedException("Не удалось добавить вопрос \"" + question.getQuestion() + "\"");
        }
    }

    @Override
    public Question remove(Question question) {

        if (questionRepository.remove(question)) {
            return question;
        } else {
            throw new QuestionNotFoundException("Не удалось удалить вопрос \"" + question.getQuestion() + "\"");
        }

    }

    @Override
    public Collection<Question> getAll() {
        return questionRepository.getAll();
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
