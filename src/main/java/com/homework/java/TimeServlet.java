package com.homework.java;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");

        ZoneId zone = (ZoneId) req.getAttribute("zone");
        String time = ZonedDateTime.now(zone).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));

        PageEngine.getPageEngine().writeFile("time", new Context(req.getLocale(), Map.of("time", time)), resp.getWriter());
    }
}
