import domain.Torneo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/setup")
public class ContraseñaBiblioteca extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nombreBiblioteca = req.getParameter("biblioteca");
        String password = req.getParameter("contraseña");
        PrintWriter out = resp.getWriter();

        if (nombreBiblioteca == null || password == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Debes introducir el nombre de la biblioteca (biblioteca=) y la contraseña (contraseña=)");
            return;
        }
        if (password.equals("a")) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<h1>" + nombreBiblioteca + "</h1>");
            out.println("</head>");
            out.println("<body>");
            if (req.getSession().getAttribute("torneo") != null) {
                out.println("<h3>Este ordenador ya ha sido desbloqueado</h3>");
            } else {
                Torneo torneo = new Torneo(nombreBiblioteca);
                req.getSession().setAttribute("torneo", torneo);
                out.println("<h3>Ordenador desbloqueado</h3>");
            }
            out.println("</body>");
            out.println("</html>");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Contraseña incorrecta");
        }


    }
}

