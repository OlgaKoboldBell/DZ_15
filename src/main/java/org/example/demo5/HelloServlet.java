package org.example.demo5;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    String[] historyAnswers = {"Ярослав", "Володимир", "Данило", "Данило", "Олег"};
    String[] spaceAnswers = {"Юрій Гагарін", "1961", "1957", "8", "Ніл Армстронг"};
    String[] popCultureAnswers = {"Джамала", "Руслана", "Євробачення", "Майкл Джексон", "Кохання"};
    String[] sportAnswers = {"776 р. до н. е.", "Геркулес", "місто Олімпія", "стрільба", "4"};

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Select Category</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Select Category:</h2>");
        out.println("<form action='hello-servlet' method='post'>");
        out.println("<select name='category'>");
        out.println("<option value='history'>Історія</option>");
        out.println("<option value='pop_culture'>Поп-культура</option>");
        out.println("<option value='space'>Космос</option>");
        out.println("<option value='sport'>Спорт</option>");
        out.println("</select>");
        out.println("<button type='submit'>Показати питання</button>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String url = "jdbc:mysql://localhost:3306/quiz";
        String username = "root";
        String password = "";

        String category = request.getParameter("category");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try {
                Connection con = DriverManager.getConnection(url, username, password);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT question, answer1, answer2, answer3, answer4, true_answer1, true_answer2, true_answer3, true_answer4 FROM " + category);
                ArrayList<String[]> correctAnswersList = new ArrayList<>();
                switch (category) {
                    case "history":
                        correctAnswersList.add(historyAnswers);
                        break;
                    case "space":
                        correctAnswersList.add(spaceAnswers);
                        break;
                    case "pop_culture":
                        correctAnswersList.add(popCultureAnswers);
                        break;
                    case "sport":
                        correctAnswersList.add(sportAnswers);
                        break;
                }
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Quiz</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Питання:</h2>");
                out.println("<form action='hello-servlet' method='post'>");
                int questionNumber = 1;
                int correctAnswers = 0;
                while (rs.next()) {
                    out.println("<p>" + rs.getString("question") + "</p>");
                    out.println("<input type='radio' name='answer" + questionNumber + "' value='1'>" + rs.getString("answer1") + "<br>");
                    out.println("<input type='radio' name='answer" + questionNumber + "' value='2'>" + rs.getString("answer2") + "<br>");
                    out.println("<input type='radio' name='answer" + questionNumber + "' value='3'>" + rs.getString("answer3") + "<br>");
                    out.println("<input type='radio' name='answer" + questionNumber + "' value='4'>" + rs.getString("answer4") + "<br><br>");
                    questionNumber++;
                }
                out.println("<button type='submit' name='submit'>Відповісти</button>");
                out.println("</form>");

                if (request.getParameter("submit") != null) {
                    questionNumber = 1;
                    for (String[] correctAnswersArray : correctAnswersList) {
                        String userAnswer = request.getParameter("answer" + questionNumber);
                        if (userAnswer != null && userAnswer.equals(correctAnswersArray[questionNumber - 1])) {
                            correctAnswers++;
                        }
                        questionNumber++;
                    }
                    out.println("<p>Правильних відповідей: " + correctAnswers + "</p>");
                }
            } catch (SQLException e) {
                out.println("<p>Помилка: " + e.getMessage() + "</p>");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}
