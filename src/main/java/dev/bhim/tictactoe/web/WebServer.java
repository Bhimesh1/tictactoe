package dev.bhim.tictactoe.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import dev.bhim.tictactoe.core.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class WebServer {

    // Simple single-game state
    private static Game game;
    private static Player playerX;
    private static Player playerO;

    public static void main(String[] args) throws Exception {
        int port = Integer.getInteger("port",
                Integer.parseInt(System.getenv().getOrDefault("PORT", "8090")));

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Static files under / -> serve index.html and assets from classpath /web
        server.createContext("/", new StaticHandler());
        // API endpoints
        server.createContext("/api/state", exchange -> {
            if (!"GET".equals(exchange.getRequestMethod())) { sendMethodNotAllowed(exchange, "GET"); return; }
            sendJson(exchange, stateJson());
        });
        server.createContext("/api/new", exchange -> {
            if (!"POST".equals(exchange.getRequestMethod())) { sendMethodNotAllowed(exchange, "POST"); return; }
            Map<String, String> q = parseQuery(exchange);
            String x = q.getOrDefault("x", "Player X");
            String o = q.getOrDefault("o", "Player O");
            startNewGame(x, o);
            sendJson(exchange, stateJson());
        });
        server.createContext("/api/move", exchange -> {
            if (!"POST".equals(exchange.getRequestMethod())) { sendMethodNotAllowed(exchange, "POST"); return; }
            if (game == null) { startNewGame("Player X", "Player O"); }
            Map<String, String> q = parseQuery(exchange);
            int row = parseIntOr(q.get("row"), -1);
            int col = parseIntOr(q.get("col"), -1);
            String error = null;
            if (row < 0 || row > 2 || col < 0 || col > 2) {
                error = "row/col must be 0..2";
            } else {
                try {
                    game.playMove(row, col);
                } catch (Exception ex) {
                    error = ex.getMessage();
                }
            }
            sendJson(exchange, stateJson(error));
        });

        server.start();
        System.out.println("Web UI running at http://localhost:" + port);
    }

    private static void startNewGame(String x, String o) {
        playerX = new Player(x, 'X');
        playerO = new Player(o, 'O');
        game = new Game(playerX, playerO);
    }

    private static String stateJson() { return stateJson(null); }

    private static String stateJson(String error) {
        if (game == null) startNewGame("Player X", "Player O");
        Board b = game.board();
        String boardJson = rowsToJson(b);
        String status = game.status().name();
        String currentName = game.status() == GameStatus.IN_PROGRESS ? game.currentPlayer().name() : "";
        char currentSymbol = game.status() == GameStatus.IN_PROGRESS ? game.currentPlayer().symbol() : ' ';
        String xName = playerX != null ? playerX.name() : "Player X";
        String oName = playerO != null ? playerO.name() : "Player O";
        return """
            {
              "board": %s,
              "status": "%s",
              "currentName": "%s",
              "currentSymbol": "%s",
              "xName": "%s",
              "oName": "%s",
              "error": %s
            }
            """.formatted(
                boardJson, status, escapeJson(currentName), currentSymbol,
                escapeJson(xName), escapeJson(oName),
                error == null ? "null" : "\"" + escapeJson(error) + "\""
        );
    }

    private static String rowsToJson(Board b) {
        StringBuilder sb = new StringBuilder("[");
        for (int r = 0; r < 3; r++) {
            if (r > 0) sb.append(",");
            sb.append("[");
            for (int c = 0; c < 3; c++) {
                if (c > 0) sb.append(",");
                char ch = b.get(r, c);
                sb.append("\"").append(ch == ' ' ? "" : ch).append("\"");
            }
            sb.append("]");
        }
        sb.append("]");
        return sb.toString();
    }

    // Very small static file handler for / (serves /web resources from classpath).
    static class StaticHandler implements HttpHandler {
        @Override public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if ("/".equals(path)) path = "/index.html";
            String resourcePath = "/web" + path;
            try (InputStream in = WebServer.class.getResourceAsStream(resourcePath)) {
                if (in == null) {
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }
                byte[] bytes = in.readAllBytes();
                exchange.getResponseHeaders().add("Content-Type", contentType(path));
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
        }

        private String contentType(String path) {
            if (path.endsWith(".html")) return "text/html; charset=utf-8";
            if (path.endsWith(".js")) return "application/javascript; charset=utf-8";
            if (path.endsWith(".css")) return "text/css; charset=utf-8";
            return "text/plain; charset=utf-8";
        }
    }

    private static void sendJson(HttpExchange ex, String body) throws IOException {
        byte[] out = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        ex.sendResponseHeaders(200, out.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(out); }
    }

    private static void sendMethodNotAllowed(HttpExchange ex, String allow) throws IOException {
        ex.getResponseHeaders().add("Allow", allow);
        ex.sendResponseHeaders(405, -1);
        ex.close();
    }

    private static Map<String, String> parseQuery(HttpExchange ex) throws IOException {
        String query = ex.getRequestURI().getRawQuery();
        if (query == null || query.isEmpty()) {
            // if method is POST, also consider body as query-string (simple clients)
            if ("POST".equalsIgnoreCase(ex.getRequestMethod())) {
                try (InputStream is = ex.getRequestBody()) {
                    query = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
        }
        if (query == null || query.isEmpty()) return Collections.emptyMap();
        return Arrays.stream(query.split("&"))
                .map(s -> s.split("=", 2))
                .filter(a -> a.length == 2)
                .collect(Collectors.toMap(
                        a -> urlDecode(a[0]),
                        a -> urlDecode(a[1]),
                        (a, b) -> b,
                        LinkedHashMap::new
                ));
    }

    private static String urlDecode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }

    private static int parseIntOr(String s, int fallback) {
        try { return Integer.parseInt(s); } catch (Exception e) { return fallback; }
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
