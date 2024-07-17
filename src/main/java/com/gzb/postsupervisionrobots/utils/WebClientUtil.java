package com.gzb.postsupervisionrobots.utils;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Component
public class WebClientUtil {
    private final static WebClient webClient = WebClient.builder().baseUrl("http://127.0.0.1:8090").build();


    public ResponseEntity<String> proxy(MultipartFile[] files){
        Flux<ByteArrayResource> byteArrayResources = Flux.fromArray(files)
                .map(file -> {
                    try {
                        return new ByteArrayResource(file.getBytes()) {
                            @Override
                            public String getFilename() {
                                return file.getOriginalFilename();
                            }
                        };
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        byteArrayResources.toStream()
                .forEach(resource -> {
                    body.add("file", resource);
                    // 如果需要其他元数据，如文件名，也可以这样添加
                    // body.add("filename", resource.getFilename());
                });

        return webClient.method(HttpMethod.POST)
                .uri("/api/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(body))
                .retrieve()
                .toEntity(String.class)
                .block();
    }


}
