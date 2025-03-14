package org.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.util.Set;
import java.util.TimeZone;

public class TimezoneValidateFilter implements Filter {
    private static final Set<String> VALID_TIMEZONES = ZoneId.getAvailableZoneIds();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String timezone = req.getParameter("timezone");
        if (timezone != null && !timezone.isEmpty() && !isValidTimezone(timezone)) {
            res.setContentType("text/html; charset=UTF-8");
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter out = res.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head><title>Error</title></head>");
                out.println("<body>");
                out.println("<h1>Invalid timezone</h1>");
                out.println("</body>");
                out.println("</html>");
            }
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private boolean isValidTimezone(String timezone) {
        return VALID_TIMEZONES.contains(timezone);
    }
}