package com.example.demo.service;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.dto.UserDTO;

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
}
