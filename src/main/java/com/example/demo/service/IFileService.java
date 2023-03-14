package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService extends IService<Files> {
    String upload(MultipartFile file) throws IOException;
}
