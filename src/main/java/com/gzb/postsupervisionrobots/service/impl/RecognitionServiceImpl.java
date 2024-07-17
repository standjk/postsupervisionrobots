package com.gzb.postsupervisionrobots.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gzb.postsupervisionrobots.entity.Credentials;
import com.gzb.postsupervisionrobots.entity.Result;
import com.gzb.postsupervisionrobots.mapper.RecognitionMapper;
import com.gzb.postsupervisionrobots.service.RecognitionService;
import com.gzb.postsupervisionrobots.utils.PatternUtils;
import com.gzb.postsupervisionrobots.utils.WebClientUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

// 转发请求到ocr系统，获取返回json字符串，对其序列化成为Java对象，并查询数据库进行比对。
@Service
@Log4j2
public class RecognitionServiceImpl implements RecognitionService {
    @Autowired
    RecognitionMapper recognitionMapper;
    @Autowired
    PatternUtils patternUtils;
    @Autowired
    WebClientUtil webClient;

    public Result recognize(MultipartFile[] files) {
        ResponseEntity<String> proxy = webClient.proxy(files);
//        log.info(proxy);


        String str = proxy.getBody();
        log.info(str);
        if (str != null) {
            str = str.replace(" ", "");
        } else {
            return Result.error("ocr结果为空");
        }
        Map<String, JSONObject> jsonMap;
        try {
            jsonMap = JSONObject.parseObject(str, Map.class);
        } catch (Exception e) {
            return Result.error("ocr结果无法解析");
        }
        // key 文件名 value 对象
        HashMap<String, Credentials> ocrCredentialsHashMap = new HashMap<>();
        ArrayList<String> fileNameList = new ArrayList<>();
        //遍历返回ocr结果，封装对象
        if (jsonMap != null) {
            jsonMap.forEach((key, value) -> {
                ocrCredentialsHashMap.put(key, PatternUtils.regularMatch(value));
                fileNameList.add(key);
            });
        } else {
            return Result.error("ocr结果为空");
        }
        //通过文件名查询数据库
        List<Credentials> credentialsList = recognitionMapper.selectByFileName(fileNameList);
        Map<String, Credentials> dbCredentialsMap = new HashMap<>();
        if (!credentialsList.isEmpty()) {
            for (Credentials credentials : credentialsList) {
                dbCredentialsMap.put(credentials.getFileName(), credentials);
            }
        }
        // ocr结果与数据库内结果进行对比，获取错误对象   credentialsList数据库结果   credentialsHashMap ocr结果
        List<List<Credentials>> result = new ArrayList<>();
        ocrCredentialsHashMap.forEach((k, v) -> {
            Credentials dbCredential = dbCredentialsMap.get(k);
            if (!v.equals(dbCredential)) {
                List<Credentials> list = new ArrayList<>();
                list.add(dbCredential);
                list.add(v);
                result.add(list);
            }

        });

        return Result.success(result);

    }
}
/**
 *
 */
