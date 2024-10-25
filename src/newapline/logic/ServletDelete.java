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

@WebServlet(urlPatterns ="/delete")
public class ServletDelete extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        JsonObject jResponse = new JsonObject();
        if(id>0){
            if(!model.getFromList().containsKey(id)){
                jResponse.addProperty("message", "User with ID=" +id+" not found");
            }else{
                jResponse.addProperty("message", "User with ID=" +id+" deleted");;
                jResponse.add("user delete", gson.toJsonTree(model.getFromList().get(id)));
                model.delete(id);
                jResponse.add("stayed users", gson.toJsonTree(model.getFromList()));
            }
        }else{
            jResponse.addProperty("message", "ID must be greater than or equal to 0. The resulting value ID="+id);
        }
        pw.print(gson.toJson(jResponse));
    }
}
