package org.example.newsfeed.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.newsfeed.common.exception.ErrorResponse;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/", "/signup", "/login","/logout"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("로그인 유저") == null) {
                throw new RuntimeException("로그인 해주세요.");
                ObjectMapper mapper = new ObjectMapper();
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(mapper.writeValueAsString(new ErrorResponse("USR-009", "로그인이 필요합니다.", httpRequest.getRequestURI())));
                return;
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 해주세요.");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
