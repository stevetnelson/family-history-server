package Handler;

import Request.RegisterRequest;
import Result.Result;
import Service.RegisterService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class RegisterRequestHandler extends RequestPostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

                RegisterRequest registerRequest = getRequest(exchange, RegisterRequest.class);
                if (registerRequest != null && registerRequest.getPassword() != null &&
                        registerRequest.getUsername() != null && registerRequest.getEmail() != null &&
                        registerRequest.getFirstName() != null && registerRequest.getLastName() != null && registerRequest.getGender() != null &&
                        (registerRequest.getGender().toLowerCase().equals("m") || registerRequest.getGender().toLowerCase().equals("f"))) {
                    registerRequest.setGenderLowercase();
                    RegisterService registerService = new RegisterService();
                    Result registerResult = registerService.register(registerRequest);
                    writeJsonResponse(exchange, registerResult);
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
