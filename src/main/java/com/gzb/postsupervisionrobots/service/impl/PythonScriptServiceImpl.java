package com.gzb.postsupervisionrobots.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzb.postsupervisionrobots.entity.Credentials;
import com.gzb.postsupervisionrobots.mapper.PythonScriptMapper;
import com.gzb.postsupervisionrobots.service.PythonScriptService;
import com.gzb.postsupervisionrobots.utils.PatternUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

// 执行python识别脚本，获取脚本返回json文件，对其序列化成为Java对象，并查询数据库进行比对。
@Service
@Log4j2
public class PythonScriptServiceImpl implements PythonScriptService {
    @Autowired
    PythonScriptMapper pythonScriptMapper;
    @Autowired
    PatternUtils patternUtils;

    public String runPythonScript() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:/Users/Administrator/.conda/envs/py36/python.exe", "c://Users//Administrator//Desktop//hello.py");
//            processBuilder.directory(new File("D:\\ComparaFile")); //设置工作路径
            //开始识别
            Process process = processBuilder.start();
            process.waitFor();
            log.info("Python脚本执行完成");
            //  获取socket请求，json反序列化为java对象
            ServerSocket server = new ServerSocket(9001);
            Socket socket = server.accept();
            InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(inputStream);
            String line = br.readLine();
            br.readLine();
            StringBuilder dataBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                // 假设客户端发送完整的JSON数据在一行中
                dataBuilder.append(line);
            }
            String str = dataBuilder.toString();
            str = str.replace(" ", "");
            server.close();
            Map<String, JSONObject> jsonMap = JSONObject.parseObject(str, Map.class);
            // key 文件名 value 对象
            HashMap<String, Credentials> credentialsHashMap = new HashMap<>();
            ArrayList<String> fileNameList = new ArrayList<>();
            //遍历python返回ocr结果，封装对象
            jsonMap.forEach((key, value) -> {
                credentialsHashMap.put(key, PatternUtils.regularMatch(value));
                fileNameList.add(key);
            });

            //通过文件名查询数据库
            List<Credentials> credentialsList = pythonScriptMapper.selectByFileName(fileNameList);
            // ocr结果与数据库内结果进行对比，获取错误对象
            List<Credentials> errorCredentials = new ArrayList<>();
            for (Credentials credentials : credentialsList) {
                if (!credentials.equals(credentialsHashMap.get(credentials.getFileName()))) {
                    errorCredentials.add(credentials);
                }
            }
            return JSON.toJSONString(errorCredentials);
        } catch (Exception e) {
//            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}

