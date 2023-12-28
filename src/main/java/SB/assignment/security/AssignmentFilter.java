package SB.assignment.security;

import SB.assignment.exceptions.ApiExceptions;
import SB.assignment.helpers.UsersHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;

import static SB.assignment.constants.AssignmentConstants.ErrorsConstants.PLEASE_LOGIN_IN_FIRST;

@Component
@Slf4j
public class AssignmentFilter implements Filter {

    private final ObjectMapper objectMapper;

    public AssignmentFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private final String WHITE_API = "/api/v1/login";


    @Autowired
    private UsersHelper usersHelper;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getRequestURI().equals(WHITE_API) || usersHelper.isUserLogin()) {
            chain.doFilter(request, response);
        } else {
            handleDoFilterException((HttpServletResponse) response, req.getRequestURI());
            return;
        }
    }

    private void handleDoFilterException(HttpServletResponse response, String url) throws IOException {
        log.error("failed to access url ( " + url + " )");

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiExceptions apiExceptions = new ApiExceptions(PLEASE_LOGIN_IN_FIRST.getMessage(), httpStatus, ZonedDateTime.now());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(apiExceptions));
    }
}
