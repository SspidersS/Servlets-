package org.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TimeServlet extends HttpServlet {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        String timezoneParam = request.getParameter("timezone");
        ZoneId zoneId;
        String timezoneLabel;

        if (timezoneParam == null || timezoneParam.isEmpty()) {
            zoneId = ZoneId.of("UTC");
            timezoneLabel = "UTC";
        } else {
            zoneId = ZoneId.of(timezoneParam);
            timezoneLabel = timezoneParam;
        }

        ZonedDateTime now = ZonedDateTime.now(zoneId);
        String formattedTime = FORMATTER.format(now);

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Current Time</title></head>");
            out.println("<body>");
            out.println("<h1>Current Time in " + timezoneLabel + ":</h1>");
            out.println("<p>" + formattedTime + " " + timezoneLabel + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}

