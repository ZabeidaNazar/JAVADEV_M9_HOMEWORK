package com.homework.java;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(value = "/*")
public class DebugFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String debug = req.getParameter("debug");

        if (debug != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<p style=\"font-size: 30px; color: #c5c5d2;\">")
                    .append("ContextPath: ")
                    .append(req.getContextPath())
                    .append("<br><br>PathInfo: ")
                    .append(req.getPathInfo())
                    .append("<br><br>QueryString: ")
                    .append(req.getQueryString())
                    .append("<br><br>RequestURI: ")
                    .append(req.getRequestURI())
                    .append("<br><br>ServletPath: ")
                    .append(req.getServletPath())
                    .append("<br><br>Cookies: ");
            for (Cookie cookie : req.getCookies()) {
                stringBuilder
                        .append(String.format("%s=%s", cookie.getName(), cookie.getValue()))
                        .append("<br>");
            }
            stringBuilder
                    .append("</p>");

            resp.getWriter().write(stringBuilder.toString());
        }

        chain.doFilter(req, resp);
    }
}
