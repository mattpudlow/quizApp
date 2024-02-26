package com.mattlan.quizapp.controller;

import com.mattlan.quizapp.models.Question;
import com.mattlan.quizapp.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllQuestions() {
        // Mocking service response
        List<Question> questions = getQuestionsByCount(5);
        when(questionService.getAllQuestions()).thenReturn(ResponseEntity.ok(questions));

        // Performing the controller method call
        ResponseEntity<List<Question>> responseEntity = questionController.getAllQuestions();

        // Verifying service method was called
        verify(questionService, times(1)).getAllQuestions();

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(questions, responseEntity.getBody());
    }

    @Test
    void testGetQuestionsByCategory() {
        // Mocking service response
        String category = "Category A";
        List<Question> questions = getQuestionsByCategory(category);
        when(questionService.getQuestionsByCategory(category)).thenReturn(ResponseEntity.ok(questions));

        // Performing the controller method call
        ResponseEntity<List<Question>> responseEntity = questionController.getQuestionsByCategory(category);

        // Verifying service method was called
        verify(questionService, times(1)).getQuestionsByCategory(category);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(questions, responseEntity.getBody());
    }

    private static List<Question> getQuestionsByCategory(String category) {
        Question question = new Question();
        question.setId(1);
        question.setQuestionTitle("Question 1");
        question.setRightAnswer("Answer A");
        question.setOption1("Option A");
        question.setOption2("Option B");
        question.setOption3("Option C");
        question.setOption4("Option D");
        question.setDifficultylevel("Easy");
        question.setCategory(category);
        Question question1 = new Question();
        question1.setId(2);
        question1.setQuestionTitle("Question 1");
        question1.setRightAnswer("Answer A");
        question1.setOption1("Option A");
        question1.setOption2("Option B");
        question1.setOption3("Option C");
        question1.setOption4("Option D");
        question1.setDifficultylevel("Easy");
        question1.setCategory(category);
        List<Question> questions = Arrays.asList(
                question1, question
        );
        return questions;
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

    @Test
    void testAddQuestion() {
        // Mocking service response
        Question question = new Question();
        question.setId(1);
        question.setQuestionTitle("Question 1");
        question.setRightAnswer("Answer A");
        question.setOption1("Option A");
        question.setOption2("Option B");
        question.setOption3("Option C");
        question.setOption4("Option D");
        question.setDifficultylevel("Easy");
        question.setCategory("Category A");
        when(questionService.addQuestion(question)).thenReturn(ResponseEntity.ok("Question added successfully"));

        // Performing the controller method call
        ResponseEntity<String> responseEntity = questionController.addQuestion(question);

        // Verifying service method was called
        verify(questionService, times(1)).addQuestion(question);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question added successfully", responseEntity.getBody());
    }

    @Test
    void testUpdateQuestion() {
        // Mocking service response
        Question question = new Question();
        question.setId(1);
        question.setQuestionTitle("Updated Question");
        question.setRightAnswer("Answer A");
        question.setOption1("Option A");
        question.setOption2("Option B");
        question.setOption3("Option C");
        question.setOption4("Option D");
        question.setDifficultylevel("Easy");
        question.setCategory("Category A");
        when(questionService.updateQuestion(question)).thenReturn(ResponseEntity.ok("Question updated successfully"));

        // Performing the controller method call
        ResponseEntity<String> responseEntity = questionController.updateQuestion(question);

        // Verifying service method was called
        verify(questionService, times(1)).updateQuestion(question);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question updated successfully", responseEntity.getBody());
    }

    @Test
    void testDeleteQuestion() {
        // Mocking service response
        int questionId = 1;
        when(questionService.deleteQuestion(questionId)).thenReturn(ResponseEntity.ok("Question deleted successfully"));

        // Performing the controller method call
        ResponseEntity<String> responseEntity = questionController.deleteQuestion(questionId);

        // Verifying service method was called
        verify(questionService, times(1)).deleteQuestion(questionId);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question deleted successfully", responseEntity.getBody());
    }

}
