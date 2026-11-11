package org.example;

import config.ThymeleafConstants;
import config.UrlConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.Torneo;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

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

        String peticion =(String) req.getSession().getAttribute("peticion");

        if(peticion==null){
            switch (torneo.getJugadores().size()){
                case 0:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay jugadores. Ve a /setup");
                    return;
                case 1:
                    peticion="tercera";
                    break;
                case 2:
                    peticion="segunda";
                    break;
                default:
                    peticion="primera";
                    break;
            }

        }
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Ganadores</h1>");
        out.println("- - - - - - - -");
        switch (peticion){
            case "primera":
                out.println("<h2>Tercer puesto: "+torneo.getJugadoresByWins().get(2)+"</h2>");
                out.println("<h3>Numero de victorias: "+torneo.getWins(torneo.getJugadoresByWins().get(2))+"</h3>");
                req.getSession().setAttribute("peticion", "segunda");
                break;
            case "segunda":
                out.println("<h2>Segundo puesto: "+torneo.getJugadoresByWins().get(1)+"</h2>");
                out.println("<h3>Numero de victorias: "+torneo.getWins(torneo.getJugadoresByWins().get(1))+"</h3>");
                req.getSession().setAttribute("peticion", "tercera");
                break;
            case "tercera":
                out.println("<h1>Primer puesto: "+torneo.getJugadoresByWins().getFirst()+"</h1>");
                out.println("<h3>Numero de victorias: "+torneo.getWins(torneo.getJugadoresByWins().getFirst())+"</h3>");
                req.getSession().setAttribute("peticion", "tercera");
                break;
        }
        out.println("</body>");
        out.println("</html>");
    }
}
