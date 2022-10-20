package pro.sky.coursework2.entity;

import java.util.Collection;

public interface QuestionRepository {

    boolean add(String question, String answer);
    boolean add(Question question);
    boolean remove(Question question);
    Collection<Question> getAll();

}
