package Handler;

import Result.Result;
import Service.PersonSingleService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SinglePersonRequestHandler extends RequestAuthTokenHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                if (isAuthorized(exchange)) {
                    String personID = exchange.getRequestURI().toString().substring(exchange.getRequestURI().toString().lastIndexOf('/') + 1);
                    PersonSingleService personSingleService = new PersonSingleService();
                    Result personSingleResult = personSingleService.personSingle(personID, exchange.getRequestHeaders().getFirst("Authorization"));
                    writeJsonResponse(exchange, personSingleResult);
                }
                else {
                    writeJsonResponse(exchange, new Result("Error: Invalid auth token", false));
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

