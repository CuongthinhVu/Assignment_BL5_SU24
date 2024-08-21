package controller;

import com.google.gson.Gson;
import data_access.CourseDataAccess;
import data_access.EnrollDataAccess;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.CourseContent;
import model.QuizData;
import model.User;

public class QuizController extends HttpServlet {
    private CourseDataAccess courseDAO;
    private EnrollDataAccess enrollDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = CourseDataAccess.getInstance();
        enrollDAO = EnrollDataAccess.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || !idStr.matches("^[0-9]+$")) {
            response.sendError(404);
            return;
        }
        int id = Integer.parseInt(idStr);
        CourseContent content = courseDAO.getContentById(id);
        if (content.getType() != CourseContent.Type.Quiz) {
            response.sendError(404);
            return;
        }
        Gson gson = new Gson();
        QuizData data = gson.fromJson(content.getContent(), QuizData.class);
        request.setAttribute("quiz", content);
        request.setAttribute("pass_grade", data.getPassGrade());
        request.setAttribute("question_list", data.getQuestions());
        request.getRequestDispatcher("quiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(404);
            return;
        }
        if (action.equals("grade_quiz")) {
            String idStr = request.getParameter("id");
            if (idStr == null || !idStr.matches("^[0-9]+$")) {
                response.sendError(404);
                return;
            }
            int id = Integer.parseInt(idStr);
            CourseContent quiz = courseDAO.getContentById(id);
            if (quiz == null || quiz.getType() != CourseContent.Type.Quiz) {
                response.sendError(404);
                return;
            }
            Gson gson = new Gson();
            QuizData data = gson.fromJson(quiz.getContent(), QuizData.class);
            int correctAnswer = 0;
            List<QuizData.Question> questions = data.getQuestions();
            int totalQuestion = questions.size();
            for (int i = 0; i < questions.size(); i++) {
                String[] answers = request.getParameterValues("question_" + questions.get(i).getId());
                if (answers == null) {
                    continue;
                }
                boolean isCorrect = true;
                List<QuizData.Question.Option> options = questions.get(i).getOptions();
                int totalCorrect = 0;
                int correctSelect = 0;
                for (int j = 0; j < options.size(); j++) {
                    if (options.get(j).isCorrect()) {
                        totalCorrect++;
                    }
                    for (String answer : answers) {
                        boolean selected = Integer.parseInt(answer) == options.get(j).getId();
                        options.get(j).setSelected(selected);
                        if (selected) {
                            if (options.get(j).isCorrect()) {
                                correctSelect++;
                            }
                            else {
                                isCorrect = false;
                            }
                            break;
                        }
                    }
                }
                if (correctSelect != totalCorrect) {
                    isCorrect = false;
                }
                if (isCorrect) {
                    correctAnswer++;
                }
            }
            User user = (User)request.getSession().getAttribute("user");
            enrollDAO.addOrUpdateQuizResult(quiz.getId(), user.getId(), correctAnswer, correctAnswer >= data.getPassGrade());
            enrollDAO.checkCourseCompleted(quiz.getCourseId(), user.getId());
            request.setAttribute("quiz", quiz);
            request.setAttribute("question_list", data.getQuestions());
            request.setAttribute("correct_answer", correctAnswer);
            request.setAttribute("total_question", totalQuestion);
            request.setAttribute("pass_grade", data.getPassGrade());
            request.getRequestDispatcher("quiz_result.jsp").forward(request, response);
        }
    }
}
