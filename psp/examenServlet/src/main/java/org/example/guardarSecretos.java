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

@WebServlet("/guardarSecretos")
public class guardarSecretos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clave = "";
        String valor = "";
        String mensaje = "";
        if(req.getParameter("clave") == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro requerido");
        }
        if(req.getParameter("valor")==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro requerido");
        }

        if((boolean) (req.getSession().getAttribute("corrector"))){
            req.getSession().setAttribute(clave, valor);
            mensaje = "Introducido con exito";
        }else{
            mensaje = "No has abierto la caja";
        }

        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext ctx = new WebContext(webExchange);
        ctx.setVariable("mensaje", mensaje);

        resp.setContentType(ThymeleafConstants.CONTENT_TYPE);
        ((TemplateEngine) getServletContext().getAttribute(ThymeleafConstants.TEMPLATE_ENGINE_ATTR))
                .process(UrlConstants.TEMPLATE_CAJAFUERTE, ctx, resp.getWriter());


    }
}
