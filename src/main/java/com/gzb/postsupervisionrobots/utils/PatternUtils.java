package com.gzb.postsupervisionrobots.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzb.postsupervisionrobots.entity.Credentials;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

// 通过json封装凭条对象
@Component
public class PatternUtils {
    /*
    广州银行业务凭条
    业务类型：个人存款
    交易机构：01328
    交易时间：2024-07-0412：25：03
    交易流水：10339202303030011
    客户名：李蚊蚊
    账号：6214633131141775907
    交易类别：现金
    凭证类型：一卡通（预制卡）
    凭证号码：01417759
    币种：港币
    金额(大写)：港币零元整
    金额：HKDO.00
    产品名称：个人结算账户
    存款子户号：001344
    请对上述银行记录确认签名：
    授权柜员：03936
    经办柜员：10339"\n
     */


    public static Credentials regularMatch(JSONObject ocrResultJSON) {
        Map<String, String> ocrResult = JSONObject.toJavaObject(ocrResultJSON, Map.class);
        String fileName = "文件名";
        String customerName = "客户名";
        String account = "账号";
        String transaction = "产品名称";
        String institution = "交易机构";
        String voucherId = "凭证号码";
        String currency = "币种";
        String amount = "金额";
        String transactionDateTime = "交易时间";
        String subaccountId = "子户号";
        // 转化时间为时间戳
        String date = ocrResult.get(transactionDateTime);
        date = date.replace(" ", "").replace("：", ":");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        long timestamp = instant.toEpochMilli();
        date = String.valueOf(timestamp);
        date = date.substring(0, date.length() - 3);
        // 将货币中文名转化为代码
//        System.out.println("Timestamp: " + timestamp);
        currency = CurrencyCodes.getCodeByCurrencyName(ocrResult.get(currency));
        Credentials credentials = new Credentials(ocrResult.get(fileName), ocrResult.get(customerName), ocrResult.get(account), ocrResult.get(transaction),
                currency, new BigDecimal(ocrResult.get(amount).substring(3)), ocrResult.get(voucherId), ocrResult.get(institution),
                date, ocrResult.get(subaccountId));
        return credentials;
    }
}
