package com.mattlan.quizapp.service;

import com.mattlan.quizapp.dao.QuestionDao;
import com.mattlan.quizapp.dao.QuizDao;
import com.mattlan.quizapp.models.Question;
import com.mattlan.quizapp.models.QuestionWrapper;
import com.mattlan.quizapp.models.Quiz;
import com.mattlan.quizapp.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numOfQuestions, String title) {
        try {
            List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numOfQuestions);
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDao.save(quiz);
            return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to create quiz", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        try {
            Quiz quiz = quizDao.findById(id).orElse(null);
            if (quiz == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            List<Question> questionsFromDB = quiz.getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for (Question question : questionsFromDB) {
                questionsForUser.add(
                        new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getRightAnswer(),
                        question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4()));
            }
            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        try {
            Quiz quiz = quizDao.findById(id).orElse(null);
            if (quiz == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            List<Question> questions = quiz.getQuestions();
            int score = 0;
            for (int i = 0; i < questions.size(); i++) {
                if (questions.get(i).getRightAnswer().equals(responses.get(i).getResponse())) {
                    score++;
                }
            }
            return new ResponseEntity<>(score, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
