package Handler;

import Dao.AuthTokenDao;
import Dao.DataAccessException;
import Dao.Database;
import Model.AuthToken;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.Connection;

public class RequestAuthTokenHandler extends RequestHandler {

    public boolean isAuthorized(HttpExchange exchange) throws IOException {
        Headers reqHeaders = exchange.getRequestHeaders();
        if (reqHeaders.containsKey("Authorization")) {
            String authToken = reqHeaders.getFirst("Authorization");
            return authTokenIsValid(authToken);
        } else {
            return false;
        }
    }

    public boolean authTokenIsValid(String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken userAuth = authTokenDao.findWithAuthToken(authToken);
            db.closeConnection(false);
            if (userAuth == null) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (DataAccessException e) {
            try {db.closeConnection(false);}
            catch (DataAccessException d) {
                System.out.println(d.getMessage());
            }
            return false;
        }
    }
}
