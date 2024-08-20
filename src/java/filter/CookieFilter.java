package filter;

import data_access.UserDataAccess;
import model.User;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

public class CookieFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("user_login")) {
                        String email = cookie.getValue();
                        User user = UserDataAccess.getInstance().getUserByEmail(email);
                        if (user != null) {
                            session = req.getSession(true);
                            session.setAttribute("user", user);
                        }
                        break;
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
