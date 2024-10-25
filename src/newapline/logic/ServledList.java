package newapline.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns ="/get")
public class ServledList extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=utf-8");
//
//        request.setCharacterEncoding("UTF-8");
//
//        PrintWriter pw = response.getWriter();
//        int id = Integer.parseInt(request.getParameter("id"));
//
//        if (id == 0) {
//            pw.print("<html>" +
//                    "<h3>Доступные пользователи</h3><br/>" +
//                    "ID пользователя: " +
//                    "<ul>");
//            for (Map.Entry<Integer, User> entry : model.getFromList().entrySet()) {
//                pw.print("<li>" + entry.getKey() + "</li>" +
//                        "<ul>" +
//                        "<li> Имя: " + entry.getValue().getName() + "</li>" +
//                        "<li> Фамилия: " + entry.getValue().getSurname() + "</li>" +
//                        "<li> Зарплата: " + entry.getValue().getSalari() + "</li>" +
//                        "</ul>");
//            }
//            pw.print("</ul>" +
//                    "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
//        } else if (id > 0) {
//            if (id > model.getFromList().size()) {
//                pw.print("<html>" +
//                        "<h3>Такого пользователя нет!</h3>" +
//                        "<a href=\"index.jsp\">Домой</a>" +
//                        "</html>");
//            } else {
//                pw.print("<html>" +
//                        "<h3>Пользователь с id" + id + ":</h3>" +
//                        "<br/>" +
//                        "<ul>" +
//                        "<li> Имя: " + model.getFromList().get(id).getName() + "</li>" +
//                        "<li> Фамилия: " + model.getFromList().get(id).getSurname() + "</li>" +
//                        "<li> Зарплата: " + model.getFromList().get(id).getSalari() + "</li>" +
//                        "</ul>" +
//                        "<a href=\"index.jsp\">Домой</a>" +
//                        "</html>");
//            }
//
//        } else {
//            pw.print("<html>" +
//                    "<h3>ID должен быть больше 0!</h3>" +
//                    "<a href=\"index.jsp\">Домой</a>" +
//                    "</html>");
//        }
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer jb = new StringBuffer();
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        String line;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        JsonObject jobj = gson.fromJson(String.valueOf(jb), JsonObject.class);

        int id = jobj.get("id").getAsInt();
        JsonObject jResponse = new JsonObject();

        if(id==0){
            jResponse.add("users", gson.toJsonTree(model.getFromList()));
        }else if(id>0){
            if(!model.getFromList().containsKey(id)){
                jResponse.addProperty("message", "Пользователь с ID=" +id+" не найден");
            }else{
                jResponse.add("user", gson.toJsonTree(model.getFromList().get(id)));
            }
        }else{
            jResponse.addProperty("message", "ID должно быть больше или равно 0. Получение значение ID="+id);
        }
        pw.print(gson.toJson(jResponse));

    }


}

