package Handler;

import Request.LoginRequest;
import Result.Result;
import Service.LoginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginRequestHandler extends RequestPostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

                LoginRequest loginRequest = getRequest(exchange, LoginRequest.class);
                if (loginRequest != null && loginRequest.getPassword() != null && loginRequest.getUsername() != null) {
                    LoginService loginService = new LoginService();
                    Result loginResult = loginService.login(loginRequest);

                    writeJsonResponse(exchange, loginResult);
                }
                else {
                    writeJsonResponse(exchange, new Result("Error: Request property missing or has invalid value", false));
                }
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
