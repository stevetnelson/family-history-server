package Handler;

import Request.LoadRequest;
import Result.Result;
import Service.LoadService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class LoadRequestHandler extends RequestPostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

                LoadRequest loadRequest = getRequest(exchange, LoadRequest.class);
                if (loadRequest != null && loadRequest.getMyEvents() != null && loadRequest.getMyUsers() != null && loadRequest.getMyPersons() != null) {
                    LoadService loadService = new LoadService();
                    Result loadResult = loadService.load(loadRequest);

                    writeJsonResponse(exchange, loadResult);
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
