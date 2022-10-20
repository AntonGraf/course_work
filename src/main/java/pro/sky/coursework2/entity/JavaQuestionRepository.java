package pro.sky.coursework2.entity;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component("JavaQuestionRepository")
public class JavaQuestionRepository implements QuestionRepository{

    Set<Question> questions;

    public JavaQuestionRepository() {
        questions = new HashSet<>();
    }

    @PostConstruct
    void init() {
        questions.add(new Question("Что такое объект","Объект - это "));
        questions.add(new Question("Что такое класс","Класс - это "));
        questions.add(new Question("Что такое инкапсуляция", "Инкапсуляция - это "));
    }

    @Override
    public boolean add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public boolean add(Question question) {
        return getAll().add(question);
    }

    @Override
    public boolean remove(Question question) {
        return getAll().remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return questions;
    }
}
