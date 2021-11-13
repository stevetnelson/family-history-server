package Handler;

import Result.Result;
import Service.PersonFamilyService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class PersonRequestHandler extends RequestAuthTokenHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                if (isAuthorized(exchange)) {
                    PersonFamilyService personFamilyService = new PersonFamilyService();
                    Result personFamilyResult = personFamilyService.personFamily(exchange.getRequestHeaders().getFirst("Authorization"));
                    writeJsonResponse(exchange, personFamilyResult);
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
