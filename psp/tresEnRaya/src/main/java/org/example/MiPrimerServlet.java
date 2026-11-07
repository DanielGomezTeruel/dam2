package org.example;

import config.ThymeleafConstants;
import config.UrlConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("/tres")
public class MiPrimerServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String marca = req.getParameter("marca");
        String coordenada = req.getParameter("coordenada");
        String ganador = "";
        String mensajeCompletado = "";

        if (marca == null || coordenada == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro requerido");
            return;
        }

        if(req.getSession().getAttribute("marca") == null){
            req.getSession().setAttribute("marca", "X");
        }

        String turno = req.getSession().getAttribute("marca").toString();

        if(!(marca.equalsIgnoreCase("X")||marca.equalsIgnoreCase("O"))){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Marca errónea, debe ser X u O");
            return;
        }

        if(!marca.equalsIgnoreCase(turno)){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No es tu turno");
            return;
        }

        String[] Tablero = (String[]) req.getSession().getAttribute("Tablero");
        if (Tablero == null) {
            Tablero = new String[]{"-", "-", "-", "-", "-", "-", "-", "-", "-"};
            req.getSession().setAttribute("Tablero", Tablero);
        }

        if(!Tablero[Integer.parseInt(coordenada) - 1].equals("-")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ese hueco no está vacío");
            return;
        }

        Tablero[Integer.parseInt(coordenada) - 1] = turno;

        String fila1 = Tablero[0] + " " + Tablero[1] + " " + Tablero[2];
        String fila2 = Tablero[3] + " " + Tablero[4] + " " + Tablero[5];
        String fila3 = Tablero[6] + " " + Tablero[7] + " " + Tablero[8];


        boolean completado=true;

        for (int i = 0; i < 9; i++) {
            if(Tablero[i].equals("-")){
                completado=false;
            }
        }

        if(completado){
            mensajeCompletado="Ya no quedan huecos";
        }


        if(!marca.equalsIgnoreCase("O")){
            req.getSession().setAttribute("marca", "O");
        }else{
            req.getSession().setAttribute("marca", "X");
        }

        for (int i = 0; i < 7; i=i+3) {
            if(Tablero[i].equals(Tablero[i+1])&&Tablero[i+1].equals(Tablero[i+2])&&!Tablero[i].equals("-")){
                ganador = "Ha ganado "+Tablero[i];
            }
        }

        for (int i = 0; i < 3; i++) {
            if(Tablero[i].equals(Tablero[i+3])&&Tablero[i+3].equals(Tablero[i+6])&&!Tablero[i].equals("-")){
                ganador = "Ha ganado "+Tablero[i];
            }
        }

        if(Tablero[0].equals(Tablero[4])&&Tablero[4].equals(Tablero[8])&&!Tablero[0].equals("-")){
            ganador = "Ha ganado "+Tablero[0];
        }

        if(Tablero[2].equals(Tablero[4])&&Tablero[4].equals(Tablero[6])&&!Tablero[2].equals("-")){
            ganador = "Ha ganado "+Tablero[2];
        }

        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext ctx = new WebContext(webExchange);
        ctx.setVariable("fila1", fila1);
        ctx.setVariable("fila2", fila2);
        ctx.setVariable("fila3", fila3);
        ctx.setVariable("ganador", ganador);
        ctx.setVariable("mensaje", mensajeCompletado);

        resp.setContentType(ThymeleafConstants.CONTENT_TYPE);
        ((TemplateEngine) getServletContext().getAttribute(ThymeleafConstants.TEMPLATE_ENGINE_ATTR))
                .process(UrlConstants.TEMPLATE_TRES_EN_RAYA, ctx, resp.getWriter());

    }
}
