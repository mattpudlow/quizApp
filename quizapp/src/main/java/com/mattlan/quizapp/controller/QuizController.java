package com.mattlan.quizapp.controller;

import com.mattlan.quizapp.models.QuestionWrapper;
import com.mattlan.quizapp.models.Response;
import com.mattlan.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(
            @RequestParam String category,
            @RequestParam Integer numOfQuestions,
            @RequestParam String title) {
        return quizService.createQuiz(category, numOfQuestions, title);
    }

    @GetMapping("get")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(
            @RequestParam Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(
            @PathVariable Integer id,
            @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}
