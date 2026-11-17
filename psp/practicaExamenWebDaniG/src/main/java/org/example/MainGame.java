package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.Carrera;
import org.example.domain.Corredor;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/carrera")
public class MainGame extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Carrera carrera = (Carrera) req.getSession().getAttribute("carrera");
        if (carrera == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay una carrera creada. Ve a /setup");
            return;
        }
        if (!(boolean) req.getSession().getAttribute("carreraEmpezada")) {
            String password = req.getParameter("contraseña");
            if (password == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Un administrador tiene que introducir la contrasenha para empezar");
                return;
            }
            if (!password.equals("contraseña")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Contrasenha incorrecta");
                return;
            } else {
                req.getSession().setAttribute("carreraEmpezada", true);
            }
        }

        String nombreCorredor = req.getParameter("corredor");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Carrera</h1>");
        out.println("- - - - - - - -");

        if (!carrera.isCompletada()) {

            if (nombreCorredor == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de corredor requerido");
                return;
            }

            if (!carrera.corredorExists(nombreCorredor)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Corredor inexistente");
                return;
            }

            carrera.checkTurno();

            Corredor corredor = carrera.getCorredorByName(nombreCorredor);

            if (!corredor.isMovedThisTurn()) {
                out.println("<h2>" + carrera.moveCorredor(corredor) + "</h2>");
            } else {
                out.println("<h2>" + corredor.getNombre() + " ya ha movido en este turno</h2>");
            }

            if (corredor.getPosicion() >= carrera.getLongitud()) {
                carrera.setCompletada(true);
            }
        }

        if (carrera.isCompletada()) {
            out.println("<h1> " + carrera.getGanador() + " ha ganado la carrera</h1>");
        }

        carrera.getCorredores().forEach(j -> {
            out.println("<h3>" + j.getNombre() + ": " + j.getPosicion() + "/" + carrera.getLongitud() + "</h3>");
        });

        out.println("</body>");
        out.println("</html>");
    }

}
