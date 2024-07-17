package com.gzb.postsupervisionrobots.controller;// 导入需要的包和类

import com.gzb.postsupervisionrobots.entity.Result;
import com.gzb.postsupervisionrobots.service.impl.RecognitionServiceImpl;
import com.gzb.postsupervisionrobots.utils.WebClientUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.FutureTask;


// 创建文件上传控制器
@RestController
@Log4j2
@RequestMapping("/api")
public class RecognitionController {
    @Autowired
    RecognitionServiceImpl recognitionService;

    @Autowired
    WebClientUtil webClient;
    @Value("${web.upload-path}")
    final String basePath = "D://ComparaFile//";

    // 处理文件上传请求的POST方法
    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        try {
            //获取当前日期
            if (files.length==0){
                return Result.error("未上传文件");
            }
            FutureTask<Result> ocrTask = new FutureTask<>(() -> recognitionService.recognize(files));
            FutureTask<Result> saveTask= new FutureTask<>(() -> saveFile(files));
            Thread ocrThread = new Thread(ocrTask);
            Thread saveThread = new Thread(saveTask);
            ocrThread.start();
            saveThread.start();
            Result result = ocrTask.get();
            log.info("返回结果:"+result);
            return result;
        } catch (Exception e) {
            return Result.error("识别失败");
        }

    }

    // 保存MultipartFile files的文件
    private Result saveFile(MultipartFile[] files) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateSt = date.format(formatter).replace("-", "//");
        String path = basePath + dateSt;
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        path = path + '/';
        try {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                // 将文件保存到磁盘或执行其他操作，这里只是简单地将文件保存到静态资源目录下
                file.transferTo(new File(path + fileName));
            }
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}
