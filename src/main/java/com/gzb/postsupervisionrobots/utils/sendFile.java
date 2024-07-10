package com.gzb.postsupervisionrobots.utils;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;

@Component
public class sendFile {
//    public static void main(String[] args) throws Exception {
//        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
//        server.createContext("/download", new FileDownloadHandler());
//        server.start();
//        System.out.println("Server is listening on port 8080");
//    }

    static class FileDownloadHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File file = new File("C:/Users/Lenovo/Desktop/GradeSystem.pdf"); // 指定文件路径
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


