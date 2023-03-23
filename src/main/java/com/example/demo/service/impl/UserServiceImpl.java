package com.example.demo.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Code;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.UserDTO;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IMenuService;
import com.example.demo.service.IRoleService;
import com.example.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private IMenuService menuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User user = getUserInfo(userDTO);
        if(user != null){
            // 这段代码不具有通用性，既耦合度过高
            BeanUtils.copyProperties(user,userDTO);
            // 设置Token
            String token = JwtUtil.createToken(user);
            userDTO.setToken(token);

            String flag = userDTO.getRole(); // 获取角色ROLE_ADMIN 进行表的查询
            // 通过唯一标识符获取角色roleId
            Integer role_id = roleMapper.findIdByFlag(flag);
            // 通过role_id获取菜单menu_id,也可以进行多表连查
            List<Integer> menus = roleMenuMapper.selectByRoleId(role_id);
            // 通过menu_id获取对应的路径

            return userDTO;
        }else{
            throw new ServiceException(Code.CODE_600.getCode(), "用户名或密码错误");
        }
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        User user = getUserInfo(userDTO);
        if(user == null){
            user = new User();
            BeanUtils.copyProperties(userDTO,user);
            /*
             * 再从数据库中取出id,前面并不知道相应的id是多少
             * */
            Integer id = userMapper.insert(user);
            userDTO.setId(id);
        }else {
            throw new ServiceException(Code.CODE_600.getCode(),"用户已存在");
        }
        return userDTO;
    }

    public User getUserInfo(UserDTO userDTO){
        if("".equals(userDTO.getUsername()) || "".equals(userDTO.getPassword())){
            // 使用异常来处理返回值问题
            throw new ServiceException(Code.CODE_400.getCode(),"密码或用户名输入为空");
        }
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
        return user;
    }
}
