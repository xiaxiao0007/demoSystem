package com.example.demo.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Code;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDTO;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxf
 * @since 2023-03-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private  static final Log LOG = Log.get();
    @Override
    public Result login(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if("".equals(username) || "".equals(password)){
            return Result.error(Code.CODE_400.getCode(),"参数错误");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User user;
        try{
            user = this.getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
           throw new  ServiceException(Code.CODE_500.getCode(),"系统错误");
        }
        if(user != null){
            // 这段代码不具有通用性，既耦合度过高
            userDTO.setNickname(user.getNickname());
            userDTO.setAvatarUrl(user.getAvatarUrl());
            return Result.success(userDTO);
        }else{
            throw new ServiceException(Code.CODE_600.getCode(), "用户名或密码错误");
        }
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User user;
        try{
            user = this.getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Code.CODE_500.getCode(),"系统错误");
        }
        if(user == null){
            user = new User();
            /*user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setNickname(userDTO.getNickname());
            user.setAvatarUrl(user.getAvatarUrl());*/
            BeanUtils.copyProperties(userDTO,user);
            save(user);
        }else {
            throw new ServiceException(Code.CODE_600.getCode(),"用户已存在");
        }
        return userDTO;
    }
}
