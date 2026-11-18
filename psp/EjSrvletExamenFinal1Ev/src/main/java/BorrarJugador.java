import domain.Torneo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/borrarJugador")
public class BorrarJugador extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Torneo torneo = (Torneo) req.getSession().getAttribute("torneo");

        if (torneo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No existe torneo. Ve a /setup");
        } else {
            String jugadorABorrar = req.getParameter("jugador");
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<h1>" + torneo.getNombreBiblioteca() + "</h1>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Borrar jugador</h1>");
            out.println("- - - - - - - -");
            if (jugadorABorrar == null) {
                out.println("<h4>Introduce en la url el jugador que deseas borrar del torneo (nombre del atributo: jugador). Ten en cuenta que esto eliminara todas las partidas asociadas</h4>");
            } else {
                if (torneo.getJugadores().contains(jugadorABorrar)) {
                    torneo.deletePlayer(jugadorABorrar);
                    out.println("<h4>Jugador eliminado del torneo</h4>");
                } else {
                    out.println("<h4>Ese jugador no existe</h4>");
                }
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
}
