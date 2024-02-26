package com.mattlan.quizapp.service;

import com.mattlan.quizapp.dao.QuestionDao;
import com.mattlan.quizapp.models.Question;
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

class QuestionServiceTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionService questionService;

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

    @Test
    void testGetAllQuestions() {
        // Mocking repository response
        List<Question> questions = getQuestionsByCount(5);
        when(questionDao.findAll()).thenReturn(questions);

        // Performing the service method call
        ResponseEntity<List<Question>> responseEntity = questionService.getAllQuestions();

        // Verifying repository method was called
        verify(questionDao, times(1)).findAll();

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

    @Test
    void testGetQuestionsByCategory() {
        // Mocking repository response
        String category = "Category A";
        List<Question> questions = getQuestionsByCategory(category);
        when(questionDao.findByCategory(category)).thenReturn(questions);

        // Performing the service method call
        ResponseEntity<List<Question>> responseEntity = questionService.getQuestionsByCategory(category);

        // Verifying repository method was called
        verify(questionDao, times(1)).findByCategory(category);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(questions, responseEntity.getBody());
    }

    @Test
    void testAddQuestion() {
        // Mocking repository response
        Question question = getQuestionsByCount(1).get(0);
        when(questionDao.save(question)).thenReturn(question);

        // Performing the service method call
        ResponseEntity<String> responseEntity = questionService.addQuestion(question);

        // Verifying repository method was called
        verify(questionDao, times(1)).save(question);

        // Verifying the response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Question added successfully", responseEntity.getBody());
    }

    @Test
    void testUpdateQuestion() {
        // Mocking repository response
        Question question = getQuestionsByCount(1).get(0);
        when(questionDao.save(question)).thenReturn(question);

        // Performing the service method call
        ResponseEntity<String> responseEntity = questionService.updateQuestion(question);

        // Verifying repository method was called
        verify(questionDao, times(1)).save(question);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question updated successfully", responseEntity.getBody());
    }

    @Test
    void testDeleteQuestion() {
        // Mocking repository response
        int questionId = 1;
        doNothing().when(questionDao).deleteById(questionId);

        // Performing the service method call
        ResponseEntity<String> responseEntity = questionService.deleteQuestion(questionId);

        // Verifying repository method was called
        verify(questionDao, times(1)).deleteById(questionId);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question deleted successfully", responseEntity.getBody());
    }
}

