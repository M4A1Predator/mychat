package com.nckpop.mychat.filter;

import com.nckpop.mychat.service.JwtUtil;
import com.nckpop.mychat.util.UserRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    @Qualifier(value = "jwtUtilWithoutDbCheckImpl")
    private JwtUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${jwt.refresh.header}")
    private String refreshTokenHeader;

    @Resource(name = "initUserRequest")
    private UserRequest userRequest;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Headers",
                "Access-Control-Allow-Origin, Origin, Accept, X-Requested-With, Authorization, refreshauthorization, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Allow-Credentials");
        if (response.getHeader("Access-Control-Allow-Origin") == null)
            // response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin") != null ? request.getHeader("origin"): "http://localhost:4200");
        if(response.getHeader("Access-Control-Allow-Credentials") == null)
            response.addHeader("Access-Control-Allow-Credentials", "true");
        if(response.getHeader("Access-Control-Allow-Methods") == null)
            response.addHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");

        String token = request.getHeader(tokenHeader);

        if (token != null && !token.equals("")) {

            if(!request.getMethod().equals("OPTIONS")){
                token = request.getHeader(this.tokenHeader).substring(6);
            }else{
                token = request.getHeader(this.tokenHeader);
            }

            if(jwtTokenUtil.isTokenExpired(token)){
                response.setStatus(490);
                return;
            }

            if (jwtTokenUtil.validateToken(token)) {

                String username = jwtTokenUtil.getUsernameFromToken(token);
                List autorities = jwtTokenUtil.getRolesFromToken(token);
                logger.info("checking Validity of JWT for user ");

                logger.info("checking authentication for user " + username);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, autorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

                userRequest.setUserId(jwtTokenUtil.getUserIdFromToken(token));
            }
        }
        chain.doFilter(request, response);

    }
}
