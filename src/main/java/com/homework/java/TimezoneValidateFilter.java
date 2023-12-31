package com.homework.java;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Map;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");

        if (timezone == null) {
            timezone = "UTC";
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastTimezone".equals(cookie.getName())) {
                        timezone = cookie.getValue().toUpperCase();
                        break;
                    }
                }
            }
            try {
                req.setAttribute("zone", ZoneId.of(timezone));
            } catch (Exception ignored) {
                req.setAttribute("zone", ZoneId.of("UTC"));
                resp.addCookie(new Cookie("lastTimezone", "UTC"));
            }
        } else {
            try {
                req.setAttribute("zone", ZoneId.of(timezone.toUpperCase()));
            } catch (Exception ignored) {
                resp.setStatus(400);
                resp.setContentType("text/html; charset=utf-8");
                Context context = new Context(
                        req.getLocale(),
                        Map.of("time", "Invalid timezone")
                );
                PageEngine.getPageEngine().writeFile("time", context, resp.getWriter());
                return;
            }
            resp.addCookie(new Cookie("lastTimezone", timezone));
        }

        chain.doFilter(req, resp);
    }
}
