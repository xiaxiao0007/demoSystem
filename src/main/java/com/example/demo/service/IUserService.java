package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.UserDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xxf
 * @since 2023-03-04
 */
public interface IUserService extends IService<User> {

    Result login(UserDTO userDTO);

    UserDTO register(UserDTO user);
}
