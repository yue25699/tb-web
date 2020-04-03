package com.tb.service;

import org.springframework.web.multipart.MultipartFile;

import com.tb.vo.EasyUIFile;

public interface FileService {

	EasyUIFile fileUpload(MultipartFile uploadFile);

}
