package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.IFileService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private IFileService fileService;

    /*
    * 文件上传
    *
    * */
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {

        return Result.success(fileService.upload(file));
    }
}
