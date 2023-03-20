package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Options;

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
}
