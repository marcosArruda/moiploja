/**
 *
 */
package com.marcos.moiploja.admin.security;

import com.marcos.moiploja.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Marcos
 */
@Component
public class PostAuthorizationFilter extends OncePerRequestFilter {
    protected String[] IGNORE_URIS = {
            "/assets/"
    };
    @Autowired
    SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (!isIgnorableURI(uri)) {
            String menu = MenuConfiguration.getMatchingMenu(uri);
            request.setAttribute("CURRENT_MENU", menu);
        }
        chain.doFilter(request, response);
    }

    private boolean isIgnorableURI(String uri) {
        for (String u : IGNORE_URIS) {
            if (uri.startsWith(u)) {
                return true;
            }
        }
        return false;
    }
}
