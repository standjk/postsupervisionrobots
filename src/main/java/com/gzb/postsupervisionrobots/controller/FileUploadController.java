package com.gzb.postsupervisionrobots.controller;// 导入需要的包和类


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzb.postsupervisionrobots.entity.Result;
import com.gzb.postsupervisionrobots.service.impl.PythonScriptServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


// 创建文件上传控制器
@RestController
@Log4j2
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    PythonScriptServiceImpl pythonScriptService;

    // 处理文件上传请求的POST方法
    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        try {
            //获取当前日期
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateSt = date.format(formatter).replace("-", "//");
            String path = "D://ComparaFile//" + dateSt;
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            // 获取上传文件的文件名
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                path = path + "/";
                // 将文件保存到磁盘或执行其他操作，这里只是简单地将文件保存到静态资源目录下
                file.transferTo(new File(path + fileName));
            }
//            return new ResponseEntity<>("文件上传成功！", HttpStatus.OK);
        } catch (Exception e) {
            return Result.error("文件上传失败");
        }
//        String json = "";
        try {
            String json = start();
            return Result.success(json);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("文件上传成功，但是后续识别失败");
        }
    }


    // 执行python脚本
    private String start() {
        return pythonScriptService.runPythonScript();
    }

}
