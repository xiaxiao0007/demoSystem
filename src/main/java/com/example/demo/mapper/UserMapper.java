package com.example.demo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xxf
 * @since 2023-03-04
 */
public interface UserMapper extends BaseMapper<User> {

   /*
   @Select("select * from sys_user where username like #{username} limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize, String username);

    @Select("select count(*) from sys_user where username like concat('%', #{username}, '%') ")
    Integer selectTotal(String username);
    **/

    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User entity);


    Page<User> findPage(Page<User> page, @Param("username")String username, @Param("email")String email, @Param("address") String address);
}
