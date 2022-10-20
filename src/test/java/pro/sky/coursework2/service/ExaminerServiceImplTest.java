package pro.sky.coursework2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    private final Set<Question> javaQuestions = new HashSet<>();
    private final Set<Question> mathQuestions = new HashSet<>();

    @Mock
    private JavaQuestionService javaQuestionService;
    @Mock
    private MathQuestionService mathQuestionService;

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
                Arguments.of(8)
        );
    }

    @BeforeEach
    public void beforeEach() {
        javaQuestions.clear();
        fillTestJavaQuestions();

        mathQuestions.clear();
        fillTestMathQuestions();

    }

    @ParameterizedTest
    @MethodSource("provideParamsTest")
    void getQuestions(int amount) {

        when(javaQuestionService.getAll()).thenReturn(javaQuestions);
        when(mathQuestionService.getAll()).thenReturn(mathQuestions);
        doAnswer(getTestRandomJavaQuestion()).when(javaQuestionService).getRandomQuestion();
        doAnswer(getTestRandomMathQuestion()).when(mathQuestionService).getRandomQuestion();

        for (Question question : out.getQuestions(amount)) {
            assertTrue(javaQuestions.contains(question) || mathQuestions.contains(question));
        }

        assertEquals(out.getQuestions(amount).size(), amount);

    }

    @ParameterizedTest
    @MethodSource("provideParamsNegativeTest")
    void getQuestionsNegative(int amount) {
        assertThrows(QuestionNotFoundException.class, () -> out.getQuestions(amount));
    }


    public void fillTestJavaQuestions() {
        javaQuestions.addAll( Set.of(
                new Question("Что такое объект","Объект - это "),
                new Question("Что такое класс","Класс - это "),
                new Question("Что такое интерфейс","Интерфейс - это "),
                new Question("Что такое инкапсуляция","Инкапсуляция - это ")

        ));
    }

    public void fillTestMathQuestions() {
        mathQuestions.addAll( Set.of(
                new Question("2 + 2","4"),
                new Question("2 * 2","4"),
                new Question("2 ^ 2","4"),
                new Question("2 / 2","1")

        ));
    }

    public Answer<Question> getTestRandomJavaQuestion() {
        Random random = new Random();
        return invocationOnMock -> javaQuestions.toArray(new Question[0])[random.nextInt(javaQuestions.size())];

    }

    public Answer<Question> getTestRandomMathQuestion() {
        Random random = new Random();
        return invocationOnMock -> mathQuestions.toArray(new Question[0])[random.nextInt(mathQuestions.size())];

    }
}