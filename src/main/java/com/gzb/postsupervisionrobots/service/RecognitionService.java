package com.gzb.postsupervisionrobots.service;

import com.gzb.postsupervisionrobots.entity.Result;
import org.springframework.web.multipart.MultipartFile;

public interface RecognitionService {
    Result recognize(MultipartFile[] files);
}
