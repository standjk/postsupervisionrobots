import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.*;
import java.nio.file.*;

public class sendFiles {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/download", new FileDownloadHandler());
        server.start();
        System.out.println("Server is listening on port 8080");
    }

    static class FileDownloadHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestedFile = exchange.getRequestURI().getPath();
            // 假设请求的URI是/download/filename.pdf，我们去掉前面的/download/
            if (requestedFile.startsWith("/download/")) {
                requestedFile = requestedFile.substring("/download/".length());
            }

            File file = new File("C:/Users/Lenovo/Desktop/" + requestedFile); // 指定文件存储路径
            if (!file.exists()) {
                exchange.sendResponseHeaders(404, 0);
                return;
            }

            exchange.sendResponseHeaders(200, file.length());
            OutputStream os = exchange.getResponseBody();
            Files.copy(file.toPath(), os);
            os.close();
        }
    }
}