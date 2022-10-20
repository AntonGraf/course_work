package pro.sky.coursework2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.exception.QuestionAddedException;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionServiceTest {

    private static final QuestionService out = new JavaQuestionService();

    @BeforeEach
    void clear() {
        out.getAll().clear();
    }

    public static Stream<Arguments> provideParamsTest() {
        return Stream.of(
                Arguments.of("Что такое объект", "Объект - это ",
                        new Question("Что такое объект","Объект - это ")),
                Arguments.of("Что такое класс", "Класс - это ",
                        new Question("Что такое класс","Класс - это ")),
                Arguments.of("Что такое интерфейс", "Интерфейс - это ",
                        new Question("Что такое интерфейс","Интерфейс - это ")),
                Arguments.of("Что такое инкапсуляция", "Инкапсуляция - это ",
                        new Question("Что такое инкапсуляция","Инкапсуляция - это "))
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsTest")
    void add(String question, String answer, Question excepted) {

        assertTrue(out.getAll().isEmpty());
        assertEquals(out.add(question, answer), excepted);
        assertEquals(out.getRandomQuestion(), excepted);
        assertEquals(out.getAll().size(), 1);

        assertThrows(QuestionAddedException.class, () -> out.add(question, answer));
        assertEquals(out.getAll().size(), 1);
    }

    @Test
    void remove() {

        assertTrue(out.getAll().isEmpty());

        Question question1 = new Question("Что такое объект","Объект - это ");
        Question question2 = new Question("Что такое класс","Класс - это ");
        Question question3 = new Question("Что такое инкапсуляция", "Инкапсуляция - это ");

        assertThrows(QuestionNotFoundException.class, () -> out.remove(question2));

        out.add(question1);
        out.add(question2);
        out.add(question3);

        assertEquals(out.getAll().size(), 3);
        assertEquals(out.remove(new Question("Что такое объект","Объект - это ")), question1);
        assertEquals(out.getAll().size(), 2);

        assertFalse(out.getAll().contains(question1));
        assertTrue(out.getAll().contains(question2));
        assertTrue(out.getAll().contains(question3));

        assertThrows(QuestionNotFoundException.class, () -> out.remove(question1));
    }

    @Test
    void getRandomQuestion() {

        assertTrue(out.getAll().isEmpty());

        assertThrows(QuestionNotFoundException.class, out::getRandomQuestion);

        Question question1 = new Question("Что такое объект","Объект - это ");
        Question question2 = new Question("Что такое класс","Класс - это ");
        Question question3 = new Question("Что такое инкапсуляция", "Инкапсуляция - это ");

        out.add(question1);
        out.add(question2);
        out.add(question3);

        assertTrue(out.getAll().contains(out.getRandomQuestion()));
    }
}