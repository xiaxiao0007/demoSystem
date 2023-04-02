package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.dto.UserDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxf
 * @since 2023-03-04
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    UserDTO register(UserDTO user);

    Page<User> findPage(Page<User> objectPage, String username, String email, String address);
}
