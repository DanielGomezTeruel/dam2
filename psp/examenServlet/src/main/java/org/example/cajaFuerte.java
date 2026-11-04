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

@WebServlet("/cajaFuerte")
public class cajaFuerte extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codigo;
        String mensaje="";
        if(req.getSession().getAttribute("codigo")==null){
            if(req.getParameter("codigo")!=null) {
                codigo = req.getParameter("codigo");
            }else{
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro requerido");
                return;
            }
            if(codigo.length()==3) {
                req.getSession().setAttribute("codigo", codigo);
                mensaje = "codigo introducido con éxito";
            }
        }else{
            mensaje = "codigo ya existente";
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
