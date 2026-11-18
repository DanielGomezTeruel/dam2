import domain.Partida;
import domain.Torneo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/estadisticas")
public class Estadisticas extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Torneo torneo = (Torneo) req.getSession().getAttribute("torneo");
        if (torneo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay torneo. Ve a /setup");
            return;
        }

        List<Partida> partidas = torneo.getPartidas();
        List<String> jugadores = torneo.getJugadores();

        String nombre = req.getParameter("nombre");
        String numPartida = req.getParameter("numPartida");


        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<h1>" + torneo.getNombreBiblioteca() + "</h1>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Estadisticas</h1>");
        out.println("- - - - - - - -");
        if (nombre == null && numPartida == null) {

            jugadores.forEach(jugador -> {
                out.println("<h1>Jugador: " + jugador + "</h1>");
                out.println("<h3> Numero de victorias: " + torneo.getWins(jugador) + "</h3>");
                out.println("- - - - - - -");
            });
        } else if (nombre == null) {
            int id = Integer.parseInt(numPartida);
            Partida partida = partidas.stream().filter(p -> p.getId() == id).toList().getFirst();
            if (partida != null) {
                out.println("<h1>Historial de la partida " + id + "</h1>");
                partida.getHistorialMovimientos().forEach(out::println);
            } else {
                out.println("<h1>Partida inexistente</h1>");
            }

        } else if (numPartida == null) {
            List<Partida> partidasJugador = torneo.getPartidasByName(nombre);
            if (!partidasJugador.isEmpty()) {
                out.println("<h1> Estadisticas de " + nombre + "</h1>");

                partidasJugador.forEach(partida -> {
                    String contrincante = nombre.equals(partida.getJugador1()) ? partida.getJugador2() : partida.getJugador1();

                    out.println("<p> ------Partida " + partida.getId() + "------- </p>");

                    if (partida.getGanador() != null) {
                        if (partida.getGanador().equals(nombre)) {
                            out.println("<p> Ganador contra " + contrincante + " </p>");
                        } else {
                            out.println("<p> Perdedor contra " + contrincante + "</p>");
                        }
                    } else {
                        out.println("<p> Empate con " + contrincante + "</p>");
                    }
                });

            } else {
                out.println("<h1>No se encuentran partidas asociadas a este jugador</h1>");
            }

        }

        out.println("</body>");
        out.println("</html>");
    }

}
