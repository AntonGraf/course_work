package pro.sky.coursework2.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.coursework2.entity.Question;
import pro.sky.coursework2.entity.QuestionRepository;
import pro.sky.coursework2.exception.QuestionAddedException;
import pro.sky.coursework2.exception.QuestionNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {

    private final String QUESTION_STRING = "2 + 2";
    private final String ANSWER = "4";

    @Mock
    private QuestionRepository questionRepository;
    @InjectMocks
    private MathQuestionService out;

    @Test
    public void addPositiveTest() {
        lenient().when(questionRepository.getAll()).thenReturn(Set.of());
        assertTrue(out.getAll().isEmpty());

        lenient().when(questionRepository.add(anyString(),anyString())).thenReturn(true);
        lenient().when(questionRepository.add(any())).thenReturn(true);
        assertEquals(out.add(QUESTION_STRING, ANSWER), new Question(QUESTION_STRING, ANSWER));

        lenient().when(questionRepository.getAll()).thenReturn(Set.of(
                new Question(QUESTION_STRING, ANSWER)
        ));
        assertFalse(out.getAll().isEmpty());
        assertEquals(out.getAll().size(), 1);
    }

    @Test
    public void addNegativeTest() {
        lenient().when(questionRepository.add(anyString(),anyString())).thenReturn(false);
        lenient().when(questionRepository.add(any())).thenReturn(false);
        assertThrows(QuestionAddedException.class, () ->out.add(QUESTION_STRING, ANSWER));
    }

    @Test
    public void removePositiveTest() {
        lenient().when(questionRepository.getAll()).thenReturn(Set.of());
        assertTrue(out.getAll().isEmpty());

        lenient().when(questionRepository.getAll()).thenReturn(Set.of(
                new Question(QUESTION_STRING, ANSWER)
        ));

        lenient().when(questionRepository.remove(any())).thenReturn(true);
        assertEquals(out.remove(new Question(QUESTION_STRING,ANSWER)),
                new Question(QUESTION_STRING, ANSWER));
    }

    @Test
    public void removeNegativeTest() {
        lenient().when(questionRepository.remove(any())).thenReturn(false);
        assertThrows(QuestionNotFoundException.class, () ->out.remove(new Question(QUESTION_STRING, ANSWER)));
    }

    @Test
    public void getRandomQuestionPositiveTest() {
        lenient().when(questionRepository.getAll()).thenReturn(getQuestionList());
        assertTrue(getQuestionList().contains(out.getRandomQuestion()));
    }

    @Test void getRandomQuestionNegativeTest() {
        lenient().when(questionRepository.getAll()).thenReturn(Set.of());
        assertThrows(QuestionNotFoundException.class, () -> out.getRandomQuestion());
    }

    private Collection<Question> getQuestionList() {

        Set<Question> questions = new HashSet<>();

        questions.add(new Question("2 + 2","4"));
        questions.add(new Question("2 * 2","4"));
        questions.add(new Question("2 ^ 2", "4"));

        return questions;
    }
}