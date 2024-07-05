package com.gzb.postsupervisionrobots.controller;// 导入需要的包和类
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


// 创建文件上传控制器
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {


    // 处理文件上传请求的POST方法
    @PostMapping("/")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile[] files) {
        try {
            //获取当前日期
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateSt = date.format(formatter).replace("-","//");
            String path = "D://ComparaFile//" + dateSt;
            if (!new File(path).exists()){
                new File(path).mkdirs();
            }
            // 获取上传文件的文件名
            for(MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                path = path+"/";

                // 将文件保存到磁盘或执行其他操作，这里只是简单地将文件保存到静态资源目录下
                file.transferTo(new File(path+fileName));
            }

            return new ResponseEntity<>("文件上传成功！", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("文件上传失败：" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
