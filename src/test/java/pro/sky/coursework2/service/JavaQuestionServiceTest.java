package pro.sky.coursework2.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.coursework2.entity.JavaQuestionRepository;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.exception.QuestionAddedException;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    private static final String QUESTION_STRING = "Что такое объект";
    private static final String ANSWER = "Объект - это ";
    @Mock
    private JavaQuestionRepository questionRepository;
    @InjectMocks
    private JavaQuestionService out;

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

        lenient().when(questionRepository.getAll()).thenReturn(Set.of());
        assertTrue(out.getAll().isEmpty());

        lenient().when(questionRepository.add(anyString(),anyString())).thenReturn(true);
        lenient().when(questionRepository.add(any())).thenReturn(true);
        assertEquals(out.add(question, answer), excepted);

        lenient().when(questionRepository.getAll()).thenReturn(Set.of(
                new Question(question, answer)
        ));
        assertEquals(out.getAll().size(), 1);

        lenient().when(questionRepository.add(anyString(),anyString())).thenReturn(false);
        lenient().when(questionRepository.add(any())).thenReturn(false);
        assertThrows(QuestionAddedException.class, () -> out.add(question, answer));
        assertEquals(out.getAll().size(), 1);
    }

    @Test
    void remove() {

        lenient().when(questionRepository.getAll()).thenReturn(Set.of());
        assertTrue(out.getAll().isEmpty());

        lenient().when(questionRepository.getAll()).thenReturn(Set.of(
                new Question(QUESTION_STRING, ANSWER)
        ));

        lenient().when(questionRepository.remove(any())).thenReturn(true);
        assertEquals(out.remove(new Question(QUESTION_STRING,ANSWER)),
                new Question(QUESTION_STRING, ANSWER));

        lenient().when(questionRepository.remove(any())).thenReturn(false);
        assertThrows(QuestionNotFoundException.class, () ->
                out.remove(new Question(QUESTION_STRING, ANSWER))
        );
    }

    @Test
    void getRandomQuestion() {

        lenient().when(questionRepository.getAll()).thenReturn(Set.of());
        assertTrue(out.getAll().isEmpty());
        assertThrows(QuestionNotFoundException.class, out::getRandomQuestion);

        lenient().when(questionRepository.getAll()).thenReturn(getQuestionList());
        assertTrue(out.getAll().contains(out.getRandomQuestion()));
    }

    private Collection<Question> getQuestionList() {

        Set<Question> questions = new HashSet<>();

        questions.add(new Question("Что такое объект","Объект - это "));
        questions.add(new Question("Что такое класс","Класс - это "));
        questions.add(new Question("Что такое инкапсуляция", "Инкапсуляция - это "));

        return questions;
    }
}