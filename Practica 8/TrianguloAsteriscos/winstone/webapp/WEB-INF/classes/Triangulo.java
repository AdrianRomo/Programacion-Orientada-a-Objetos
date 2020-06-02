import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Triangulo extends HttpServlet {

  int reng, i, j, k;

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    String name = req.getParameter("renglones");
    reng = Integer.parseInt(name) + 1;
    out.println("<HTML>");
    out.println("<HEAD><TITLE>Triangulo</TITLE></HEAD>");
    out.println("<BODY>");
    out.println("<H1>Triangulo</H1>");
    for (i = 1; i < reng; i++) {
      for (j = reng - i; j > 0; j--) {
        out.println("&nbsp;&nbsp;");
      }
      for (k = i; k > 0; k--) {
        out.println("*");
      }
      out.println("<br>");
    }
    out.println("</BODY></HTML>");
  
  }

}
