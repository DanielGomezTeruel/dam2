import domain.Partida;
import domain.Torneo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet("/partida")
public class PartidaServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Torneo torneo = (Torneo) req.getSession().getAttribute("torneo");
        if (torneo == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Este ordenador no se ha desbloqueado con la contraseña de la bibliioteca");
            return;
        }

        if (torneo.getLatestGame() == null || torneo.getLatestGame().isFinalizada()) {
            //Nueva partida
            String jugador1 = req.getParameter("jugador1");
            String jugador2 = req.getParameter("jugador2");
            String palabra = req.getParameter("palabra");


            if (jugador1 == null || jugador2 == null || palabra == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros requeridos: jugador1, jugador2, palabra");
                return;
            }

            if (torneo.isFinalizado()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Torneo finalizado. No se pueden jugar más partidas");
                return;
            }

//Lo comento porque no funciona pero que veas que ha habido un intento de filtrado de formato
//            if(!Pattern.matches("[a-zA-Z]", palabra)){
//                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "La palabra no puede contener números ni caracteres especiales");
//                return;
//            }

            torneo.addPartida(new Partida(jugador1, jugador2, palabra.toLowerCase()));
            torneo.addJugador(jugador1);
            torneo.addJugador(jugador2);


            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<h1>" + torneo.getNombreBiblioteca() + "</h1>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Partida creada</h1>");
            out.println("- - - - - - - -");
            out.println("</body>");
            out.println("</html>");
        } else {
            String jugador = req.getParameter("jugador");
            String guess = req.getParameter("proposicion");

            Partida partida = torneo.getLatestGame();

            if (jugador == null || guess == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros requeridos: jugador, proposicion");
                return;
            }

            if (!jugador.equals(partida.getTurno())) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le toca a " + partida.getTurno());
                return;
            }

            guess = guess.toLowerCase();

            boolean adivinaPalabraEntera = !(guess.length() == 1);


            if (adivinaPalabraEntera) {
                if (guess.equals(partida.getPalabra())) {
                    partida.setGanador(jugador);
                    partida.setIncognita(partida.getPalabra());
                }
            } else {
                if (partida.letraProbada(guess.charAt(0))) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Has intentado adivinar una letra que ya se ha dicho");
                    return;
                }
                if (partida.getPalabra().contains(guess)) {
                    partida.letraAdivinada(guess.charAt(0), partida.getPalabra().indexOf(guess));
                }
            }
            partida.registrarIntento(jugador, guess);
            partida.switchTurno();

            if (partida.getIntentosJ1() > 5 || partida.getIntentosJ2() > 5 || partida.getGanador() != null) {
                partida.setFinalizada(true);
            }

            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<h1>" + torneo.getNombreBiblioteca() + "</h1>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Partida</h2>");
            out.println("<p>" + partida.getPalabra().length() + " letras</p>");
            out.println("<h3>" + partida.getIncognita() + "</h3>");
            out.print("<h4>Letras probadas: ");
            partida.getLetrasProbadas().forEach(out::print);
            out.println("</h4>");
            out.println("<h4>" + partida.getJugador1() + " lleva " + partida.getIntentosJ1() + " intentos</h4>");
            out.println("<h4>" + partida.getJugador2() + " lleva " + partida.getIntentosJ2() + " intentos</h4>");
            if (partida.isFinalizada()) {
                out.print("<h2> Estadisticas finales </h2>");
                if (partida.getGanador() != null) {
                    out.println("<h4> Ha ganado " + partida.getGanador() + "</h4>");
                } else {
                    out.println("<h4> Empate </h4>");
                }
            }
            out.println("<h4>" + partida.getHistorialMovimientos() + "</h4>");
            out.println("</body>");
            out.println("</html>");


        }
    }
}
