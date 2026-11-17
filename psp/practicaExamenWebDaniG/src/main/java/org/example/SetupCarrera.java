package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.Carrera;

import java.io.IOException;

@WebServlet("/setup")
public class SetupCarrera extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String password = req.getParameter("contraseña");

        if (req.getParameter("longitud") == null || password == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro requerido");
            return;
        }

        if (password.equals("contraseña")) {

            int longitud = Integer.parseInt(req.getParameter("longitud"));

            req.getSession().setAttribute("carrera", new Carrera(longitud));
            req.getSession().setAttribute("carreraEmpezada", false);

            resp.getWriter().println("Carrera creada con longitud " + longitud);

        } else {
            resp.getWriter().println("Contrasenha erronea");
        }

    }
}
