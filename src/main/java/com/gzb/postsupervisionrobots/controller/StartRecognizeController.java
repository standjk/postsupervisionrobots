package com.gzb.postsupervisionrobots.controller;

import com.gzb.postsupervisionrobots.service.impl.PythonScriptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//开始识别
@RestController
@RequestMapping("/api/start")
public class StartRecognizeController {

    @Autowired
    PythonScriptServiceImpl pythonScriptService;

    @GetMapping("/")
    public void start(){
        pythonScriptService.runPythonScript();

    }
}