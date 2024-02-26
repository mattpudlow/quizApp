package com.mattlan.quizapp.service;

import com.mattlan.quizapp.dao.QuestionDao;
import com.mattlan.quizapp.dao.QuizDao;
import com.mattlan.quizapp.models.Question;
import com.mattlan.quizapp.models.Quiz;
import com.mattlan.quizapp.models.QuestionWrapper;
import com.mattlan.quizapp.models.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @Mock
    private QuizDao quizDao;

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static List<Question> getQuestionsByCount(int count) {
        List<Question> questions = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Question question = new Question();
            question.setId(i);
            question.setQuestionTitle("Question " + i);
            question.setRightAnswer("Answer A");
            question.setOption1("Option A");
            question.setOption2("Option B");
            question.setOption3("Option C");
            question.setOption4("Option D");
            question.setDifficultylevel("Easy");
            question.setCategory("Category A");
            questions.add(question);
        }
        return questions;
    }

    private static List<Response> getResponsesByCount(int count) {
        List<Response> responses = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Response response = new Response();
            response.setId(i);
            response.setResponse("Answer A");
            responses.add(response);
        }
        return responses;
    }

    @Test
    void testCreateQuiz() {
        // Mocking repository responses
        String category = "Category A";
        int numOfQuestions = 5;
        String title = "Test Quiz";
        List<Question> questions = getQuestionsByCount(numOfQuestions);
        when(questionDao.findRandomQuestionsByCategory(category, numOfQuestions)).thenReturn(questions);
        when(quizDao.save(any(Quiz.class))).thenReturn(new Quiz());

        // Performing the service method call
        ResponseEntity<String> responseEntity = quizService.createQuiz(category, numOfQuestions, title);

        // Verifying repository method calls
        verify(questionDao, times(1)).findRandomQuestionsByCategory(category, numOfQuestions);
        verify(quizDao, times(1)).save(any(Quiz.class));

        // Verifying the response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Quiz created successfully", responseEntity.getBody());
    }

    @Test
    void testGetQuizQuestions() {
        // Mocking repository response
        int quizId = 1;
        Quiz quiz = new Quiz();
        List<Question> questions = getQuestionsByCount(2);
        quiz.setQuestions(questions);
        when(quizDao.findById(quizId)).thenReturn(Optional.of(quiz));

        // Performing the service method call
        ResponseEntity<List<QuestionWrapper>> responseEntity = quizService.getQuizQuestions(quizId);

        // Verifying repository method call
        verify(quizDao, times(1)).findById(quizId);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size()); // Assuming 2 questions in the quiz
    }

    @Test
    void testCalculateResult() {
        // Mocking repository response
        int quizId = 1;
        List<Response> responses = getResponsesByCount(2);
        Quiz quiz = new Quiz();
        List<Question> questions = getQuestionsByCount(2);
        quiz.setQuestions(questions);
        when(quizDao.findById(quizId)).thenReturn(Optional.of(quiz));

        // Performing the service method call
        ResponseEntity<Integer> responseEntity = quizService.calculateResult(quizId, responses);

        // Verifying repository method call
        verify(quizDao, times(1)).findById(quizId);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody()); // Assuming both responses are correct
    }
}

