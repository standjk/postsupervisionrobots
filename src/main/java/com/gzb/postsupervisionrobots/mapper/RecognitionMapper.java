package com.gzb.postsupervisionrobots.mapper;

import com.gzb.postsupervisionrobots.entity.Credentials;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

// 通过文件名作为主键查询数据库
@Mapper
public interface RecognitionMapper {

    //    @ResponseBody
    ArrayList<Credentials> selectByFileName(@Param("fileNameList") ArrayList<String> fileNameList);
}
