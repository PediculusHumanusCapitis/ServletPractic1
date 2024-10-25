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
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns ="/add")

public class ServletAdd extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=utf-8");
//
//        request.setCharacterEncoding("UTF-8");
//        PrintWriter pw = response.getWriter();
//
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        double salary = Double.parseDouble(request.getParameter("salary"));
//
//        User user = new User(name, surname, salary);
//        model.add(user,counter.getAndIncrement());
//        pw.print("<html>"+
//                "<h3>Пользователь " + name +" " +surname+" с зарплатой = "+ salary+" создан! </h3>"+
//                "<a href=\"addUser.html\">Создать нового полльзователя</a><br/>"+
//                "<a href=\"index.jsp\">Домой</a>"+
//                "</html");
//    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer jb = new StringBuffer();
        int id = model.getFromList().size()+1;
        PrintWriter pw = response.getWriter();
        String line;

        try{
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine())!=null){
                jb.append(line);
            }
        } catch (Exception e){
            System.out.println("Error");
        }

        JsonObject jobj = gson.fromJson(String.valueOf(jb), JsonObject.class);

        request.setCharacterEncoding("UTF-8");

        String name = jobj.get("name").getAsString();
        String surname = jobj.get("surname").getAsString();
        double salary = jobj.get("salary").getAsDouble();

        User user = new User(name, surname, salary);
        model.add(user,id);

        response.setContentType("application/json;charset=utf-8");
        pw.print(gson.toJson(model.getFromList()));

    }

}
