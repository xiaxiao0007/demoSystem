package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Files;
import com.example.demo.service.IFileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
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
    * */
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        return Result.success(fileService.upload(file));
    }

    /*
    * 在前端下载文件 文件的下载
    * */
    @GetMapping("/{fileUUID}")//  通过response将文件上传出去
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException{
        fileService.download(fileUUID,response);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!"".equals(name),"name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(fileService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }
}
