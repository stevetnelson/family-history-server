package Main;

import Handler.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class FamilyMapServer {
    public static void main(String[] args) throws IOException {
        FamilyMapServer myServer = new FamilyMapServer();
        myServer.startServer(Integer.parseInt(args[0]));

    }

    private void startServer(int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(serverAddress, 10);
        registerHandlers(server);
        server.start();
        System.out.println("FamilyMapServer listening on port " + port);
    }

    private void registerHandlers (HttpServer server) {
        server.createContext("/", new FileHandler());
        server.createContext("/user/register", new RegisterRequestHandler());
        server.createContext("/user/login", new LoginRequestHandler());
        server.createContext("/clear", new ClearRequestHandler());
        server.createContext("/fill/", new FillRequestHandler());
        server.createContext("/load", new LoadRequestHandler());
        server.createContext("/person/", new SinglePersonRequestHandler());
        server.createContext("/person", new PersonRequestHandler());
        server.createContext("/event/", new SingleEventRequestHandler());
        server.createContext("/event", new EventRequestHandler());
    }
}
