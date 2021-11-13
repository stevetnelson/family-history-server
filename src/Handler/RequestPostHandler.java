package Handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestPostHandler extends RequestHandler {
    public <T> T getRequest(HttpExchange exchange, Class<T> classType) throws IOException {
        InputStream reqBody = exchange.getRequestBody();
        String reqData = readString(reqBody);
        return JsonSerializer.deserialize(reqData, classType);
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
