package Handler;

import Result.Result;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class RequestHandler {
    public void writeJsonResponse(HttpExchange exchange, Result result) throws IOException {
        if (result.isSuccess()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }
        OutputStream respBody = exchange.getResponseBody();
        String jsonString = JsonSerializer.serialize(result);
        System.out.println(jsonString);
        writeString(jsonString, respBody);
        respBody.close();
    }


    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }
}
