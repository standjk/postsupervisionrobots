package com.gzb.postsupervisionrobots.service.impl;
import com.gzb.postsupervisionrobots.entity.Credentials;
import com.gzb.postsupervisionrobots.mapper.PythonScriptMapper;
import com.gzb.postsupervisionrobots.service.PythonScriptService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Service
@Log4j2
public class PythonScriptServiceImpl implements PythonScriptService {
    @Autowired
    PythonScriptMapper pythonScriptMapper;

    public String runPythonScript() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:/Users/Administrator/.conda/envs/py36/python.exe","c://Users//Administrator//Desktop//hello.py");
//            processBuilder.directory(new File("D:\\ComparaFile")); //设置工作路径
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            log.info("Python脚本执行完成");
            pythonScriptMapper.selectByFileName(new Credentials("a"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
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

