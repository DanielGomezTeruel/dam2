package org.example;

import config.ThymeleafConstants;
import config.UrlConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.Partida;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("/setup")
public class SetupPartida extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String jugador1 = req.getParameter("jugador1");
        String marca1 = req.getParameter("marca1");

        String jugador2 = req.getParameter("jugador2");
        String marca2 = req.getParameter("marca2");

        String texto1 = "Partida creada";


        if (jugador1 == null || marca1 == null || jugador2 == null || marca2 == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro requerido");
            return;
        }

        req.getSession().setAttribute("partida", new Partida(jugador1, jugador2, marca1, marca2));

        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext ctx = new WebContext(webExchange);

        ctx.setVariable("texto1", texto1);

        resp.setContentType(ThymeleafConstants.CONTENT_TYPE);
        ((TemplateEngine) getServletContext().getAttribute(ThymeleafConstants.TEMPLATE_ENGINE_ATTR))
                .process(UrlConstants.TEMPLATE_SETUP, ctx, resp.getWriter());

    }
}
