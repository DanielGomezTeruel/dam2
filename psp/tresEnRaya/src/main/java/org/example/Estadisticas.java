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
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/estadisticas")
public class Estadisticas extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Torneo torneo = (Torneo) req.getSession().getAttribute("torneo");
        if(torneo==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay torneo. Ve a /setup");
            return;
        }

        List<Partida> partidas = torneo.getPartidas();

        String nombre = req.getParameter("nombre");
        String numPartida = req.getParameter("numPartida");


        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Estadisticas</h1>");
        out.println("- - - - - - - -");
        if(nombre==null&&numPartida==null) {

            partidas.forEach(partida -> {
                out.println("<h1>Partida " + partida.getId() + "</h1>");
                if (partida.getGanador() != null) {
                    out.println("<p> Ganador: " + partida.getGanador() + "</p>");
                    out.println("<p> Perdedor: " + partida.getTurno() + " </p>");
                } else {
                    out.println("<p> Empate entre " + partida.getJugador1() + " y " + partida.getJugador2() + " </p>");
                }
                out.println("<p> Marca usada por " + partida.getJugador1() + ": " + partida.getMarca1());
                out.println("<p> Marca usada por " + partida.getJugador2() + ": " + partida.getMarca2());
                out.println("<p> Numero movimientos: " + partida.getNumMovimientos() + "</p>");
                out.println("- - - - - - -");
            });
        }else if(nombre==null){
            int id = Integer.parseInt(numPartida);
            Partida partida = partidas.stream().filter(p->p.getId()==id).toList().getFirst();
            if(partida!=null) {
                String[] tablero = partida.getTablero();
                String fila1 = tablero[0] + " " + tablero[1] + " " + tablero[2];
                String fila2 = tablero[3] + " " + tablero[4] + " " + tablero[5];
                String fila3 = tablero[6] + " " + tablero[7] + " " + tablero[8];


                out.println("<h1>Tablero de la partida " + id + "</h1>");
                out.println("<p>" + fila1 + "</p>");
                out.println("<p>" + fila2 + "</p>");
                out.println("<p>" + fila3 + "</p>");
            }else{
                out.println("<h1>Partida inexistente</h1>");
            }

        } else if(numPartida==null){
            List<Partida> partidasJugador = torneo.getPartidasByName(nombre);
            if(!partidasJugador.isEmpty()){
                out.println("<h1> Estadisticas de "+nombre+"</h1>");
                out.println("<h2> Victorias totales: "+torneo.getWins(nombre)+"</h2>");
                out.println("<h2> Derrotas totales: "+torneo.getLoses(nombre)+"</h2>");


                partidasJugador.forEach(partida -> {
                        String contrincante = partida.getJugador1();
                        String marca = partida.getMarca2();

                        if(partida.getJugador1().equals(nombre)){
                            contrincante = partida.getJugador2();
                            marca = partida.getMarca1();
                        }

                        out.println("<p> ------Partida "+partida.getId()+"------- </p>");

                        if(partida.getGanador()!=null) {
                            if (partida.getGanador().equals(nombre)) {
                                out.println("<p> Ganador contra "+contrincante+" </p>");
                            } else {
                                out.println("<p> Perdedor contra "+contrincante+"</p>");
                            }
                        }else{
                            out.println("<p> Empate con "+contrincante+"</p>");
                        }
                        out.println("<p> Marca utilizada: "+marca+"</p>");
                        out.println("<p> Numero de movimientos: "+partida.getNumMovimientos()+"</p>");
                });

            }else{
                out.println("<h1>No se encuentran partidas asociadas a este jugador</h1>");
            }

        }

        out.println("</body>");
        out.println("</html>");
    }
}
