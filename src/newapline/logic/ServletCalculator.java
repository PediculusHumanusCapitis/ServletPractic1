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

import static java.lang.Math.pow;

@WebServlet(urlPatterns ="/calcul")
public class ServletCalculator extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        double a = jobj.get("a").getAsDouble();
        double b = jobj.get("b").getAsDouble();
        String math = jobj.get("math").getAsString();
        double result;

        JsonObject jResponse = new JsonObject();

        switch (math){
            case("+"):
                result=a+b;
                break;
            case("-"):
                result=a-b;
                break;
            case("*"):
                result=a*b;
                break;
            case("/"):
                if(b==0){
                    jResponse.addProperty("message", "b must not be equal to 0. The resulting b=" + b);
                    pw.print(gson.toJson(jResponse));
                    return;
                }else{
                    result = a / b;
                    break;
                }
            case("%"):
                result=a%b;
                break;
            case("^"):
                result=pow(a,b);
                break;

            default:
                jResponse.addProperty("message", "An incorrect arithmetic action has been entered. The resulting arithmetic action=" + math);
                pw.print(gson.toJson(jResponse));
                return;
        }
        jResponse.addProperty("result", result);
        pw.print(gson.toJson(jResponse));
    }
}
