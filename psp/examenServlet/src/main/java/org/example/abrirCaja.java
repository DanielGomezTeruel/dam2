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


@WebServlet("/abrirCaja")
public class abrirCaja extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String introducido = "";
        String codigo = "";
        if(req.getSession().getAttribute("codigo") != null){
            codigo = (String) req.getSession().getAttribute("codigo");
        }
        if(req.getSession().getAttribute("introducido")!=null){
            introducido = (String) req.getSession().getAttribute("introducido");
        }

        String digito = req.getParameter("digito");
        String mensaje = "";

        if(digito==null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro requerido");
            req.getSession().setAttribute("introducido", null);
            req.getSession().setAttribute("correcto", false);
            return;
        }
        if(digito.length()>1){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Un dígito cada vez");
            req.getSession().setAttribute("introducido", null);
            req.getSession().setAttribute("correcto", false);
            return;
        }

        System.out.println(codigo);

        if(introducido.length()<3){

            if(!(codigo.charAt(introducido.length()) == digito.charAt(0))){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Número incorrecto");
                req.getSession().setAttribute("introducido", null);
                req.getSession().setAttribute("correcto", false);
                return;
            }

        }

        introducido = introducido+digito;
        req.getSession().setAttribute("introducido", introducido);

        if(introducido.length()==3){
            if(introducido.equals(codigo)){
                mensaje = "Código correcto";
                req.getSession().setAttribute("correcto", true);
            }
        }

        var webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext ctx = new WebContext(webExchange);
        ctx.setVariable("mensaje", mensaje);

        resp.setContentType(ThymeleafConstants.CONTENT_TYPE);
        ((TemplateEngine) getServletContext().getAttribute(ThymeleafConstants.TEMPLATE_ENGINE_ATTR))
                .process(UrlConstants.TEMPLATE_ABRIR, ctx, resp.getWriter());



    }
}
