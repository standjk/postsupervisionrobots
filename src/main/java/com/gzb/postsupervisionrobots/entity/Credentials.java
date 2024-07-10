package com.gzb.postsupervisionrobots.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

// 凭条实体类
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    // 文件名
    @Nullable
    public String fileName;
    // 客户名称
    @Nullable
    public String customerName;
    // 账号
    @Nullable
    public String account;

    //交易名称
    @Nullable
    public String transactionName;

    //币种
    @Nullable
    public String currencyType;

    //金额
    @Nullable
    public BigDecimal Amount;


    //凭证号码
    @Nullable
    public String voucherId;

    // 交易机构
    @Nullable
    public String institution;

    // 交易时间
    @Nullable
    public String transactionDateTime;
    // 子户号
    @Nullable
    public String subaccountId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return (fileName == null || Objects.equals(fileName, that.fileName)) && (customerName == null || Objects.equals(customerName, that.customerName))
                && (account == null || Objects.equals(account, that.account)) && (transactionName == null || Objects.equals(transactionName, that.transactionName))
                && (currencyType == null || Objects.equals(currencyType, that.currencyType)) && (Amount == null || Amount.compareTo(that.Amount) == 0)
                && (voucherId == null || Objects.equals(voucherId, that.voucherId))
                && (institution == null || Objects.equals(institution, that.institution))
                && (transactionDateTime == null || Objects.equals(transactionDateTime, that.transactionDateTime))
                && (subaccountId == null || Objects.equals(subaccountId, that.subaccountId));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, customerName, account, transactionName, currencyType, Amount, voucherId, institution, transactionDateTime, subaccountId);
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "fileName='" + fileName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", account='" + account + '\'' +
                ", transactionName='" + transactionName + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", Amount=" + Amount +
                ", voucherId='" + voucherId + '\'' +
                ", institution='" + institution + '\'' +
                ", transactionDateTime='" + transactionDateTime + '\'' +
                ", subaccountId='" + subaccountId + '\'' +
                '}';
    }
}
