package com.gzb.postsupervisionrobots.controller;

import com.gzb.postsupervisionrobots.service.PythonScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//开始识别
@RestController
@RequestMapping("/api/start")
public class StartRecognize {
    @Autowired
    PythonScriptService pythonScriptService;
    @GetMapping("/")
    public void start(){
        System.out.println(1);
        pythonScriptService.runPythonScript("");

    }
}