package com.gzb.postsupervisionrobots.mapper;

import com.gzb.postsupervisionrobots.entity.Credentials;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PythonScriptMapper {

    Credentials selectByFileName(@Param("credentials") Credentials credentials);
}
