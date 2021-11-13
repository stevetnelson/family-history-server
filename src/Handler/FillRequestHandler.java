package Handler;

import Result.Result;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;

public class FillRequestHandler extends RequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                String generations = exchange.getRequestURI().toString().substring(exchange.getRequestURI().toString().lastIndexOf('/') + 1);
                String newUrl = exchange.getRequestURI().toString().substring(0, exchange.getRequestURI().toString().lastIndexOf('/'));
                String username = newUrl.substring(newUrl.lastIndexOf('/') + 1);
                int gen;
                if (username.equals("fill")) {
                    gen = 4;
                    username = generations;
                }
                else {
                    gen = Integer.parseInt(generations);
                }
                FillService fillService = new FillService();
                Result fillResult = fillService.fill(username, gen);
                writeJsonResponse(exchange, fillResult);
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
