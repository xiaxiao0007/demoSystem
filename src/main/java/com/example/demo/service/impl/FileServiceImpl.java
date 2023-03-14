package com.example.demo.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Files;
import com.example.demo.mapper.FileMapper;
import com.example.demo.service.IFileService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    /*
    * 文件上传具体功能的实现
    *
    * */
    @Override
    public String upload(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename(); // 获取文件的名字
        String type = FileUtil.extName(originalFilename); // 获取文件的类型
        long size = file.getSize(); // 获取文件的大小

        // 本来相同的图片的路径都可以存在数据库当中
        // 定义一个文件表示吗
        // String uuid = IdUtil.fastSimpleUUID();
        System.out.println("============================="+fileUploadPath);
        File uploadFile = new File(fileUploadPath + originalFilename); // 上传的文件绝对路径
        //判断存储文件的路径
        File parentFile = uploadFile.getParentFile();
        System.out.println("============================="+parentFile);
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        Files  dbFile= this.getOne(queryWrapper);
        if(dbFile != null){//文件已存在
            url = dbFile.getUrl();
        }else{
            // 上传到磁盘之上
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "/Data/"+originalFilename;
        }

        // 存储到数据库之中
        Files files = new Files();
        files.setName(originalFilename);
        files.setType(type);
        files.setSize(size);
        files.setUrl(url);
        files.setMd5(md5);
        fileMapper.insert(files);

        return url;
    }
}
