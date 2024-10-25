<%--
  Created by IntelliJ IDEA.
  User: ArtZay
  Date: 24.10.2024
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="newapline.logic.Model" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <h1>Домашняя страница по работе с пользователями</h1>
  Введите ID Пользователя (0 - для вывода всего списка пользователей)
  <br/>
  Доступно:<%
    Model model = Model.getInstance();
    out.print(model.getFromList().size());
  %>
  <form method="get" action="get">
    <label>ID:
      <input type="text" name="id">
    </label>
    <button type="submit">Поиск</button>
    <br/>
    <a href="addUser.html">Создать нового пользователя</a>
  </form>
  </body>
</html>
