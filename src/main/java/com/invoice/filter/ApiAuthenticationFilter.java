package com.invoice.filter;

import com.invoice.rest.exception.RestNotAuthorizedException;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.StringTokenizer;

public class ApiAuthenticationFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String AUTHENTICATION_BASIC = "Basic";
    private static final String ERROR_MSG = "You are not authorized to access this resource";
    private static final String ERROR_CODE = "INV-401";


    @Autowired
    private Environment environment;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        //Retrieve authorization header
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        final String apiPath = requestContext.getUriInfo().getPath();
        final List<String> authorization = headers.get(AUTHORIZATION_KEY);

        //Block access if missing authorization header
        if (!apiPath.contains("health")) {
            if (authorization == null || authorization.isEmpty()) {
                throw new RestNotAuthorizedException(ERROR_CODE, ERROR_MSG);
            }

            // Validate the request.
            String authorizationHeaderValue = authorization.get(0);
            final String base64EncodedUserPassword = authorizationHeaderValue.replaceFirst(AUTHENTICATION_BASIC + " ", "");

            //Block access if missing user creds header
            if (StringUtils.isEmpty(base64EncodedUserPassword)) {
                throw new RestNotAuthorizedException(ERROR_CODE, ERROR_MSG);
            }

            //Base64 Decode username and password
            String usernameAndPassword = new String(Base64.decode(base64EncodedUserPassword.getBytes()));


            //Obtain username and password
            final StringTokenizer credTokenizer = new StringTokenizer(usernameAndPassword, ":");
            if (credTokenizer.countTokens() < 2) {
                throw new RestNotAuthorizedException(ERROR_CODE, ERROR_MSG);
            }
            final String username = credTokenizer.nextToken();
            final String password = credTokenizer.nextToken();


            //Block access if missing user creds header
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                throw new RestNotAuthorizedException(ERROR_CODE, ERROR_MSG);
            }

            if (!username.equals(environment.getProperty("app.api.username")) || !password.equals(environment.getProperty("app.api.password"))) {
                throw new RestNotAuthorizedException(ERROR_CODE, ERROR_MSG);
            }
        }
    }

}
