package Handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = exchange.getRequestHeaders();

                String urlPath = exchange.getRequestURI().toString();
                if (urlPath == null || urlPath.equals("/")) {
                    urlPath = "/index.html";
                }

                String filePath = "web" + urlPath;
                OutputStream respBody;

                File file = new File(filePath);
                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                }
                else {
                    exchange.sendResponseHeaders(404, 0);
                    respBody = exchange.getResponseBody();
                    file = new File("web/HTML/404.html");
                    Files.copy(file.toPath(), respBody);
                }
                respBody.flush();
                respBody.close();

            }
            else {
                exchange.sendResponseHeaders(405, 0);
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }

}
