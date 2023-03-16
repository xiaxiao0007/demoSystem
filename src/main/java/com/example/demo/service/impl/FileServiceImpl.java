package com.example.demo.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Files;
import com.example.demo.mapper.FileMapper;
import com.example.demo.service.IFileService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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
        // 使用时间戳来表示一个存储的文件,既重新命名
        long timeMillis = System.currentTimeMillis();
        // 文件的新名字
        String fileNewName = timeMillis + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileNewName); // 上传的文件绝对路径
        //判断存储文件的路径
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList= fileMapper.selectList(queryWrapper);
        Files dbFile = filesList.size() == 0 ? null : filesList.get(0);
        if(dbFile != null){//文件已存在
            url = dbFile.getUrl();
        }else{
            // 上传到磁盘之上
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "/data/"+fileNewName;
        }

        // 存储到数据库之中
        Files files = new Files();
        files.setName(fileNewName);
        files.setType(type);
        files.setSize(size);
        files.setUrl(url);
        files.setMd5(md5);
        fileMapper.insert(files);

        return url;
    }

    @Override
    public void download(String id, HttpServletResponse response) throws IOException {
        // 根据文件唯一表示码获取文件
        File downloadFilePath = new File(fileUploadPath + id);
        // 设置输出格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(id, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(downloadFilePath));
        os.flush();
        os.close();
    }

    @Override
    public boolean delOneFile(Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);// 传入的参数，
        return false;
    }
}
