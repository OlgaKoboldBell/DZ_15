<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Вітаємо у веб-додаток Квіз" %></h1>
<br/>
<h2>Реєстрація</h2>
<form action="RegisterServlet" method="post">
    Ім'я користувача: <input type="text" name="username" required><br>
    Пароль: <input type="password" name="password" required><br>
    <input type="submit" value="Зареєструватися">
</form>
<%
    String registrationStatus = (String) request.getAttribute("registrationStatus");
    if(registrationStatus != null) {
%>
<p><%= registrationStatus %></p>
<a href="hello-servlet">Вперед - до питань!</a>
<% } %>
</body>
</html>
