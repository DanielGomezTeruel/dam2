package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("/index")
public class MiPrimerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String texto = req.getParameter("texto");
        if(texto==null)
        {
            resp.sendError(500, "errorch");
        }
        else
        {
            var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                    .buildExchange(req, resp);
            WebContext ctx = new WebContext(webExchange);
            ctx.setVariable("mensaje", texto);
            resp.setContentType("text/html;charset=UTF-8");
            ((TemplateEngine)getServletContext().getAttribute("com.daw.TemplateEngine"))
                    .process("index", ctx, resp.getWriter());
        }
    }

}
