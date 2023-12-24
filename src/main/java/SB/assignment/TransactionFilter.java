package SB.assignment;

import SB.assignment.exceptions.ApiExceptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;

@Component
//@Order(1)
public class TransactionFilter implements Filter {

    private final ObjectMapper objectMapper;

    public TransactionFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    Logger logger = LoggerFactory.getLogger(TransactionFilter.class);

    private final String WHITE_API = "/api/v1/login"; // TODO : MAKE IT AT CONSTANT


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getRequestURI().equals(WHITE_API)) {
            chain.doFilter(request, response);
        } else if (req.getSession().getAttribute("rule") != null) { // TODO : MAKE IT AT CONSTANT
            chain.doFilter(request, response);
        } else {
            logger.error("failed authentication while attempting to access");
            handleDoFilterException((HttpServletResponse) response);
            return;
        }

//        logger.info(
//                "Starting a transaction for req : {}",
//                req.getRequestURI());

//        chain.doFilter(request, response);

//        logger.info(
//                "Committing a transaction for req : {}",
//                req.getRequestURI());
    }

    // other methods
    private void handleDoFilterException(HttpServletResponse response) throws IOException {

        HttpStatus badRequest = HttpStatus.UNAUTHORIZED;
        ApiExceptions apiExceptions = new ApiExceptions("login in first", badRequest, ZonedDateTime.now());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(apiExceptions));
    }
}
