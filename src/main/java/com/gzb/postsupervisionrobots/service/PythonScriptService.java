package com.gzb.postsupervisionrobots.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class PythonScriptService {

    public String runPythonScript(String propertyJson) {
        try {

            ProcessBuilder processBuilder = new ProcessBuilder("C:/Users/Administrator/.conda/envs/py36/python.exe","c://Users//Administrator//Desktop//hello.py");
//            processBuilder.directory(new File("D:\\ComparaFile")); //设置工作路径
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Python脚本执行完成，退出码: " + exitCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

//            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // 处理非零退出代码
                return "Error executing script";
            }

            return output.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}

