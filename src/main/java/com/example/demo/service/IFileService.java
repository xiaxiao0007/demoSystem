package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Files;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService extends IService<Files> {

    String upload(MultipartFile file) throws IOException;

    Boolean download(String uuid, HttpServletResponse response) throws IOException;
}
