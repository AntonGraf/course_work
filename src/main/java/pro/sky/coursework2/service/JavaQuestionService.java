package pro.sky.coursework2.service;

import org.springframework.stereotype.Service;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.exception.QuestionAddedException;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class JavaQuestionService implements QuestionService{

    Set<Question> questions;

    public JavaQuestionService() {
        questions = new HashSet<>();
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {

        if (questions.contains(question)) {
            throw new QuestionAddedException("Вопрос \"" + question.getQuestion() + "\" уже есть в списке");
        }

        questions.add(question);

        return questions.stream().findAny().orElseThrow(() -> new QuestionAddedException(
                "Не удалось добавить вопрос \"" + question.getQuestion() + "\"")
        );
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

        if (questions.size() == 0) {
            throw new QuestionNotFoundException("Список вопросов пуст.");
        }

        Random random = new Random();
        return (Question) questions.toArray()[random.nextInt(questions.size())];
    }

}
