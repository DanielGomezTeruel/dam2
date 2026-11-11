package org.example;

import config.ThymeleafConstants;
import config.UrlConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.Partida;
import org.example.domain.Torneo;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("/juego")
public class MainGame extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Torneo torneo = (Torneo) req.getSession().getAttribute("torneo");
        if(torneo==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay una partida creada. Ve a /setup");
            return;
        }

        Partida partida = torneo.getLatestGame();
        String coordenada = req.getParameter("coordenada");
        String marca = "";


        if (partida == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay una partida creada. Ve a /setup");
            return;
        }


        if (coordenada == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro requerido");
            return;
        }

        String[] tablero = partida.getTablero();
        String turnoActual = partida.getTurno();


        if (!tablero[Integer.parseInt(coordenada) - 1].equals("-")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ese hueco no está vacío");
            return;
        }

        if(turnoActual.equals(partida.getJugador1())){
            marca = partida.getMarca1();
            partida.setTurno(partida.getJugador2());
        }else{
            marca = partida.getMarca2();
            partida.setTurno(partida.getJugador1());
        }


        tablero[Integer.parseInt(coordenada) - 1] = marca;

        String fila1 = tablero[0] + " " + tablero[1] + " " + tablero[2];
        String fila2 = tablero[3] + " " + tablero[4] + " " + tablero[5];
        String fila3 = tablero[6] + " " + tablero[7] + " " + tablero[8];


        partida.addMovimiento();

        boolean completado = true;

        for (int i = 0; i < 9&&completado; i++) {
            if (tablero[i].equals("-")) {
                completado = false;
            }
        }

        if (completado) {
            partida.setGanador(null);
            partida.setFinalizada(true);
        }




        for (int i = 0; i < 7; i = i + 3) {
            if (tablero[i].equals(tablero[i + 1]) && tablero[i + 1].equals(tablero[i + 2]) && !tablero[i].equals("-")) {
                partida.setGanador(turnoActual);
                partida.setFinalizada(true);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (tablero[i].equals(tablero[i + 3]) && tablero[i + 3].equals(tablero[i + 6]) && !tablero[i].equals("-")) {
                partida.setGanador(turnoActual);
                partida.setFinalizada(true);
            }
        }

        if (tablero[0].equals(tablero[4]) && tablero[4].equals(tablero[8]) && !tablero[0].equals("-")) {
            partida.setGanador(turnoActual);
            partida.setFinalizada(true);
        }

        if (tablero[2].equals(tablero[4]) && tablero[4].equals(tablero[6]) && !tablero[2].equals("-")) {
            partida.setGanador(turnoActual);
            partida.setFinalizada(true);
        }
        String estadisticas ="";
        String ganador ="";
        String perdedor ="";
        String numMovimientos ="";

        if(partida.isFinalizada()){
            estadisticas = "---Estadísticas---";
            if(partida.getGanador()!=null) {
                ganador = "Ha ganado " + partida.getGanador();
                perdedor = "Ha perdido " + partida.getTurno();
            }else{
                ganador = "Empate";
            }
            numMovimientos = "Nº movimientos: "+partida.getNumMovimientos();
        }



        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext ctx = new WebContext(webExchange);
        ctx.setVariable("fila1", fila1);
        ctx.setVariable("fila2", fila2);
        ctx.setVariable("fila3", fila3);
        ctx.setVariable("estadisticas", estadisticas);
        ctx.setVariable("ganador", ganador);
        ctx.setVariable("perdedor", perdedor);
        ctx.setVariable("numMovimientos", numMovimientos);

        resp.setContentType(ThymeleafConstants.CONTENT_TYPE);
        ((TemplateEngine) getServletContext().getAttribute(ThymeleafConstants.TEMPLATE_ENGINE_ATTR))
                .process(UrlConstants.TEMPLATE_TRES_EN_RAYA, ctx, resp.getWriter());

    }
}
