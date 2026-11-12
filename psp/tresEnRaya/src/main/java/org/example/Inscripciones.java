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

@WebServlet("/inscripciones")
public class Inscripciones extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Carrera carrera = (Carrera) req.getSession().getAttribute("carrera");

        if (carrera == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No existe carrera. Ve a /setup");
            return;
        }

        String corredor = req.getParameter("corredor");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Inscripcion de corredor</h1>");
        out.println("- - - - - - - -");
        if (corredor == null) {
            out.println("<h4>Introduce en la url el nombre del corredor que desea inscribirse en la carrera (nombre del atributo: corredor).</h4>");
        } else {
            if (carrera.addCorredor(new Corredor(corredor))) {
                out.println("<h4>Corredor apuntado con exito</h4>");
            } else {
                out.println("<h4>Este corredor ya estaba apuntado</h4>");
            }
        }
        out.println("</body>");
        out.println("</html>");

    }
}
