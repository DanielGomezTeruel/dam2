import domain.Torneo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/finTorneo")
public class FinTorneo extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Torneo torneo = (Torneo) req.getSession().getAttribute("torneo");

        if (torneo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No existe torneo. Ve a /setup");
            return;
        }

        String peticion = (String) req.getSession().getAttribute("peticion");

        if (peticion == null) {
            peticion = "primera";
        }

        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<h1>" + torneo.getNombreBiblioteca() + "</h1>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Fin de torneo</h2>");
        out.println("- - - - - - - -");
        switch (peticion) {
            case "primera":

                torneo.setFinalizado(true);

                out.println("<h1>Torneo finalizado. No se pueden jugar mas partidas</h1>");
                req.getSession().setAttribute("peticion", "segunda");
                break;
            case "segunda":
                if (torneo.getJugadoresByWins().get(0) != null) {
                    out.println("<h1>Primer puesto: " + torneo.getJugadoresByWins().getFirst() + "</h1>");
                    out.println("<h3>Numero de victorias: " + torneo.getWins(torneo.getJugadoresByWins().getFirst()) + "</h3>");
                } else {
                    out.println("<h1>No existen jugadores</h1>");

                }
                if (torneo.getJugadoresByWins().get(1) != null) {
                    out.println("<h2>Segundo puesto: " + torneo.getJugadoresByWins().get(1) + "</h2>");
                    out.println("<h3>Numero de victorias: " + torneo.getWins(torneo.getJugadoresByWins().get(1)) + "</h3>");
                }
                if (torneo.getJugadoresByWins().get(2) != null) {
                    out.println("<h2>Tercer puesto: " + torneo.getJugadoresByWins().get(2) + "</h2>");
                    out.println("<h3>Numero de victorias: " + torneo.getWins(torneo.getJugadoresByWins().get(2)) + "</h3>");
                }
                req.getSession().setAttribute("peticion", "tercera");
                break;
        }
        out.println("</body>");
        out.println("</html>");
    }
}