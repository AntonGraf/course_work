package pro.sky.coursework2.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.exception.MethodNotAllowedException;

import java.util.Collection;
import java.util.Random;

@Service("MathQuestionService")
public class MathQuestionService implements QuestionService{

    @Override
    public Question add(String question, String answer) {
        throw new MethodNotAllowedException("Method Not Allowed");
    }

    @Override
    public Question add(Question question) {
        throw new MethodNotAllowedException("Method Not Allowed");
    }

    @Override
    public Question remove(Question question) {
        throw new MethodNotAllowedException("Method Not Allowed");
    }

    @Override
    public Collection<Question> getAll() {
        throw new MethodNotAllowedException("Method Not Allowed");
    }

    @Override
    public Question getRandomQuestion() {

        Random random = new Random();
        String question = random.nextInt(20) + getMathOperation() + random.nextInt(20);
        String answer = getAnswer(question);
        return new Question(question, answer);

    }

    private String getMathOperation() {
        Random random = new Random();

        switch (random.nextInt(5)) {
            case 1: return " - ";
            case 2: return " * ";
            case 3: return " / ";
            case 4: return " ^ ";
            default: return " + ";
        }
    }

    private String getAnswer(String question) {

        String[] strings = question.split(" ");
        switch (strings[1]) {
            case "-": return String.valueOf(Integer.parseInt(strings[0]) - Integer.parseInt(strings[2]));
            case "*": return String.valueOf(Integer.parseInt(strings[0]) * Integer.parseInt(strings[2]));
            case "/": return String.valueOf(Integer.parseInt(strings[0]) / Integer.parseInt(strings[2]));
            case "^": return String.valueOf(Math.pow(Integer.parseInt(strings[0]),Integer.parseInt(strings[2])));
            default: return String.valueOf(Integer.parseInt(strings[0]) + Integer.parseInt(strings[2]));
        }
    }


}
