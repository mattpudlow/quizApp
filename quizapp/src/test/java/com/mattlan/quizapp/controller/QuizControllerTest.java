package com.mattlan.quizapp.controller;

import com.mattlan.quizapp.models.QuestionWrapper;
import com.mattlan.quizapp.models.Response;
import com.mattlan.quizapp.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuiz() {
        // Mocking service response
        String category = "Category A";
        int numOfQuestions = 5;
        String title = "Test Quiz";
        when(quizService.createQuiz(category, numOfQuestions, title)).thenReturn(ResponseEntity.ok("Quiz created successfully"));

        // Performing the controller method call
        ResponseEntity<String> responseEntity = quizController.createQuiz(category, numOfQuestions, title);

        // Verifying service method was called
        verify(quizService, times(1)).createQuiz(category, numOfQuestions, title);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Quiz created successfully", responseEntity.getBody());
    }

    @Test
    void testGetQuiz() {
        // Mocking service response
        int quizId = 1;
        List<QuestionWrapper> questions = new ArrayList<>();
        when(quizService.getQuizQuestions(quizId)).thenReturn(ResponseEntity.ok(questions));

        // Performing the controller method call
        ResponseEntity<List<QuestionWrapper>> responseEntity = quizController.getQuiz(quizId);

        // Verifying service method was called
        verify(quizService, times(1)).getQuizQuestions(quizId);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(questions, responseEntity.getBody());
    }

    @Test
    void testSubmitQuiz() {
        // Mocking service response
        int quizId = 1;
        List<Response> responses = new ArrayList<>();
        int result = 80; // Assuming 80% result
        when(quizService.calculateResult(quizId, responses)).thenReturn(ResponseEntity.ok(result));

        // Performing the controller method call
        ResponseEntity<Integer> responseEntity = quizController.submitQuiz(quizId, responses);

        // Verifying service method was called
        verify(quizService, times(1)).calculateResult(quizId, responses);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(result, responseEntity.getBody());
    }
}

