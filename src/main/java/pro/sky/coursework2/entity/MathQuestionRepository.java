package pro.sky.coursework2.entity;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component("MathQuestionRepository")
public class MathQuestionRepository implements QuestionRepository{

    private final Set<Question> questions;

    public MathQuestionRepository() {
        questions = new HashSet<>();
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
