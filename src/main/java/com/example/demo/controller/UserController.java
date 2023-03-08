package com.example.demo.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.UserDTO;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.service.IUserService;
import com.example.demo.entity.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xxf
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

   /*
   * 将数据统一封装到结果集中
   * */

    /*
    * 保存数据或更新数据内容
    * */
    @PostMapping
    public Result save(@RequestBody User user) {
        return Result.success(userService.saveOrUpdate(user));
    }

    /*
    * 根据id删除数据库中相应的用户
    * */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    /*
    * 批量删除数据库中的用户
    * */
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeBatchByIds(ids));
    }

    /*
    * 查询用户的全部数据
    * */
    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    /*
    * 根据用户id查询用户
    * */
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.list());
    }

    /*
    * mybatisplus插件的自动的生成的分页器
    * */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            // 第一个参数：该参数是一个布尔类型，只有该参数是true时，才将like条件拼接到sql中；本例中，如果name字段不为空，则拼接name字段的like查询条件；
            // 第二个参数：该参数是数据库中的字段名；
            // 第三个参数：该参数值字段值；
            queryWrapper.like(!"".equals(username),"username", username);
            queryWrapper.like(!"".equals(email),"email", email);
            queryWrapper.like(!"".equals(address),"address",address);
            queryWrapper.orderByDesc("id");
        return Result.success(userService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    /*
    * 导出数据
    * */
    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws Exception{
        // 从数据库中查询数据
        List<User> list = userService.list();
        // 在内存操作，写出浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);
        // 自定义标题名,如果在实体类中使用hutool注解的别名，可以直接导出，不需要写下面的代码
        // writer.addHeaderAlias("username","用户名");
        // writer.addHeaderAlias("password","密码");
        // writer.addHeaderAlias("nickname","昵称");
        // writer.addHeaderAlias("phone","电话");
        // writer.addHeaderAlias("email","邮箱");
        // writer.addHeaderAlias("address","地址");
        // writer.addHeaderAlias("createTime","创建时间");

        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        // writer.setOnlyAlias(true);

        // 一次性写到excel中
        writer.write(list,true);

        // 设置浏览器的响应格式
        response.setContentType("application/vnd.openxmlformats-officdocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息","utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

        return Result.success();
    }

    /*
    * 数据的导入
    * */
    @PostMapping("/import")
    public boolean imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        return userService.saveBatch(list);
    }

    /*
    * 登录验证
    * */
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        return userService.login(userDTO);
    }

    /*
    * 之前没有统一封装结果的后台代码
    * */
    /*@PostMapping
    public Boolean save(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
            return userService.removeBatchByIds(ids);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public List<User> findOne(@PathVariable Integer id) {
        return userService.list();
    }*/

}

