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
import java.util.List;

@WebServlet("/estadisticas")
public class Estadisticas extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Carrera carrera = (Carrera) req.getSession().getAttribute("carrera");
        if (carrera == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay carrera. Ve a /setup");
            return;
        }

        List<Corredor> corredores = carrera.getCorredores();

        if (corredores.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay corredores apuntados. Ve a /setup");
            return;
        }

        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Estadisticas</h1>");
        out.println("<h4>- - - - - - - -</h4>");

        corredores.forEach(corredor -> {
            out.println("<h2> Corredor: " + corredor.getNombre() + "</h2>");
            corredor.getTiradas().forEach(tirada -> {
                out.println("<p> - - - </p>");
                out.println("<h4>Tirada " + tirada.getId() + "</h4>");
                out.println("<p> Valor: " + tirada.getCantidad() + "</p>");
            });
            out.println("<p> - - - </p>");
        });
        out.println("- - - - - - - -");
        out.println("</body>");
        out.println("</html>");
    }
}
