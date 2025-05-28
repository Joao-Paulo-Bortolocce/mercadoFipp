package unoeste.fipp.mercadofipp.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import unoeste.fipp.mercadofipp.security.JWTTokenProvider;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;


public class AccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String token = req.getHeader("Authorization");

        boolean isAutenticacaoEndpoint = req.getRequestURI().contains("/logar")|| req.getRequestURI().contains("/cadastro");

        if ((token != null && JWTTokenProvider.verifyToken(token)) || isAutenticacaoEndpoint) {
            chain.doFilter(request, response);
        } else {
            System.out.println(token);
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            res.setContentType("application/json");
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("mensagem", "Não autorizado. Faça login para prosseguir.");
            ObjectMapper mapper = new ObjectMapper();
            res.getWriter().write(mapper.writeValueAsString(responseBody));
        }
    }
}


