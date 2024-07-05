package com.gzb.postsupervisionrobots.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//开始识别
@RestController
@RequestMapping("/api/start")
public class StartRecognize {
    @GetMapping("/")
    public void start(){

    }
}