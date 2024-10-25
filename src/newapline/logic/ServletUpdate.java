package newapline.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns ="/update")
public class ServletUpdate extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer jb = new StringBuffer();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
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
        String name = jobj.get("name").getAsString();
        String surname = jobj.get("surname").getAsString();
        double salary = jobj.get("salary").getAsDouble();
        JsonObject jResponse = new JsonObject();
        if(id>0){
            if(!model.getFromList().containsKey(id)){
                jResponse.addProperty("message", "User with ID=" +id+" not found");
            }else{
                jResponse.addProperty("message", "User with ID=" +id+" update");;
                jResponse.add("old user information", gson.toJsonTree(model.getFromList().get(id)));
                model.update(id, name, surname, salary);
                jResponse.add("new user information", gson.toJsonTree(model.getFromList().get(id)));
            }
        }else{
            jResponse.addProperty("message", "ID must be greater than or equal to 0. The resulting value ID="+id);
        }

        pw.print(gson.toJson(jResponse));
    }


}
