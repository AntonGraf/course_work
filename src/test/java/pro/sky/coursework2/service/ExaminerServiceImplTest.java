package pro.sky.coursework2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    private final Set<Question> questions = new HashSet<>();

    @Mock
    private JavaQuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl out;

    public static Stream<Arguments> provideParamsTest() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(3),
                Arguments.of(4)
        );
    }

    public static Stream<Arguments> provideParamsNegativeTest() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(89),
                Arguments.of(5)
        );
    }

    @BeforeEach
    public void beforeEach() {
        questions.clear();
        fillTestQuestions();

    }

    @ParameterizedTest
    @MethodSource("provideParamsTest")
    void getQuestions(int amount) {

        when(questionService.getAll()).thenReturn(questions);
        doAnswer(getTestRandomQuestion()).when(questionService).getRandomQuestion();
        assertTrue(questions.containsAll(out.getQuestions(amount)));
        assertEquals(out.getQuestions(amount).size(), amount);

    }

    @ParameterizedTest
    @MethodSource("provideParamsNegativeTest")
    void getQuestionsNegative(int amount) {
        assertThrows(QuestionNotFoundException.class, () -> out.getQuestions(amount));
    }


    public void fillTestQuestions() {
        questions.addAll( Set.of(
                new Question("Что такое объект","Объект - это "),
                new Question("Что такое класс","Класс - это "),
                new Question("Что такое интерфейс","Интерфейс - это "),
                new Question("Что такое инкапсуляция","Инкапсуляция - это ")
        ));
    }

    public Answer<Question> getTestRandomQuestion() {
        Random random = new Random();
        return invocationOnMock -> questions.toArray(new Question[0])[random.nextInt(questions.size())];
    }
}